package App;

import App.Entity.Trainer;
import App.Entity.TrainerClientList;
import App.Repository.ClientRecords;
import App.Repository.TrainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity updateClientSummary
			(@RequestParam(name = "username") String username, @RequestParam(name = "trainerUsername") String trainerUsername,
			 @RequestParam(name = "healthGoal") String healthGoal, @RequestParam(name = "dietaryPreferences") String dietaryPreferences,
			 @RequestParam(name = "weight") Double weight, @RequestParam(name = "height") Double height, @RequestParam(name = "age") Integer age)
	{

		return ResponseEntity.ok().body("ok");
	}

	@GetMapping("/get-client-list")
	public  TrainerClientList getTrainerClientList
			(@RequestBody Trainer trainer)
	{
		TrainerClientList trainerClientList = new TrainerClientList(trainer);
		log.info("trainer service getClientList request received");
		trainerClientList.setClientList(clientRecords.getClientsByTrainer_username(trainer.getUsername()));
		return trainerClientList;
	}

	public static void main(String[] args) {
		SpringApplication.run(TrainerService.class, args);
	}


}
