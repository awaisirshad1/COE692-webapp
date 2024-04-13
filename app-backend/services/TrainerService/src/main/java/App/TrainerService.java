package App;

import App.Entity.Client;
import App.Entity.Trainer;
import App.Entity.TrainerClientList;
import App.Handler.ResponseHandler;
import App.Repository.ClientRecords;
import App.Repository.TrainerRepository;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost://3000")
@RestController
@RequestMapping("/trainer")
@Slf4j
public class TrainerService {

	@Autowired
	ClientRecords clientRecords;
	@Autowired
	TrainerRepository trainerRepository;

	// INTER SERVICE COMMUNICATION METHOD
	@PostMapping("/create-trainer")
	public ResponseEntity<String> insertTrainer
			(@RequestBody LinkedMultiValueMap<String,String> params)
	{
		ResponseEntity<String> response;
		log.info("trainer service create-trainer request received");
		String jsonParam = params.getFirst("username");
		log.info(jsonParam);
		Long trainerExists = trainerRepository.trainerExists(jsonParam);
		if(trainerExists == 1){
			log.info("trainer exists");
			response = ResponseEntity.badRequest().body("failure");
			return response;
		}
		Trainer trainer = new Trainer(jsonParam);
		trainerRepository.save(trainer);
		TrainerClientList clientList = new TrainerClientList(trainer);
		log.info("inserted-trainer");
		response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"success\"}");
		return response;
	}

	// INTER SERVICE COMMUNICATION METHOD
	@PostMapping("/create-client")
	public ResponseEntity insertClient
			(@RequestBody LinkedMultiValueMap<String,String> params)
	{
		log.info("trainer service create-client request received");
		String jsonParam = params.getFirst("username");
		log.info("jsonParam:"+jsonParam);
		Long clientExists = clientRecords.clientExists(params.getFirst(jsonParam));
		if(clientExists==1){
			log.info("client already exists, returning failure status...");
			return ResponseEntity.badRequest().body("failure");
		}
		else{
			log.info("client does not exist, inserting...");

		}
		return ResponseEntity.ok().body("ok");
	}

	@PostMapping("/insert-client-summary")
	public ResponseEntity<Object> updateClientSummary
//			(@RequestParam(name = "username") String username, @RequestParam(name = "trainerUsername") String trainerUsername,
//			 @RequestParam(name = "healthGoal") String healthGoal, @RequestParam(name = "dietaryPreferences") String dietaryPreferences,
//			 @RequestParam(name = "weight") Double weight, @RequestParam(name = "height") Double height, @RequestParam(name = "age") Integer age)
		(@RequestBody Map<String,String> payload)
	{
		log.info("Trainer service insert-client-summary post request received with payload: "+payload.toString());
		String responseMsg;

		String username = payload.get("username");
		String trainerUsername;
		String healthGoal = payload.get("healthGoal");
		String dietaryPreferences = payload.get("dietaryPreferences");
		Double weight = Double.valueOf(payload.get("weight"));
		Double height = Double.valueOf(payload.get("height"));
		Integer age = Integer.valueOf(payload.get("age"));

		Client client = clientRecords.getClientByUsername("username");
		if(client == null){
			responseMsg = "client not found";
			return ResponseHandler.generateResponse(responseMsg, HttpStatus.NOT_FOUND, "username:"+username);
		}
		else{
			//check for trainer username in this request
			try{
				trainerUsername = payload.get("trainerUsername");
				client.setTrainer_username(trainerUsername);
			}
			catch (Exception e){
				log.info("no trainer username provided");
				trainerUsername = null;
			}

			client.setHealth_goal(healthGoal);
			client.setDietaryPreferences(dietaryPreferences);
			client.setWeight(weight);
			client.setHeight(height);
			client.setAge(age);

			clientRecords.save(client);
			responseMsg = "saved client" + client.getUsername();
			return ResponseHandler.generateResponse(responseMsg, HttpStatus.OK, client);
		}
	}

	@GetMapping("/get-client-list")
	public ResponseEntity<Object> getTrainerClientList
			(@PathParam(value = "trainerUsername") String trainerUsername)
	{
		log.info("trainer service getClientList request received");
		String responseMsg;

		Trainer trainer = trainerRepository.getTrainerByUsername(trainerUsername);
		if(trainer==null){
			responseMsg = "trainer not found";
			return ResponseHandler.generateResponse(responseMsg, HttpStatus.BAD_REQUEST, "");
		}
		TrainerClientList trainerClientList = new TrainerClientList(trainer);
		trainerClientList.setClientList(clientRecords.getClientsByTrainer_username(trainer.getUsername()));
		log.info("client list:"+trainerClientList.getClientList());
		return ResponseHandler.generateResponse("success", HttpStatus.OK, trainerClientList.getClientList());
	}

	public static void main(String[] args) {
		SpringApplication.run(TrainerService.class, args);
	}


}
