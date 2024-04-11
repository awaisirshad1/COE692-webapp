package App;

import App.Entity.Client;
import App.Entity.Trainer;
import App.Repository.ClientRecords;
import App.Repository.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/trainer")
@Slf4j
public class TrainerService {

	@Autowired
	ClientRecords clientRecords;
	@Autowired
	TrainerRepository trainerRepository;

	@PostMapping("/create-trainer")
	public @ResponseBody ResponseEntity<String> insertTrainer
			(@RequestBody LinkedMultiValueMap<String,String> param)
	{
		ResponseEntity<String> response;
		log.info("trainer service create-trainer request received");
		String jsonParam = param.getFirst("username");
		log.info(jsonParam);
		Long trainerExists = trainerRepository.trainerExists(jsonParam);
		if(trainerExists == 1){
			log.info("trainer exists");
//			response = ResponseEntity.badRequest().body("trainer already exists");
			response = ResponseEntity.badRequest().body("failure");
			//			return "failure";
		}
		Trainer trainer = new Trainer(jsonParam);
		trainerRepository.save(trainer);
		log.info("inserted-trainer");
		response = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"success\"}");
//		response = "success";
		return response;
	}

	@PostMapping("/create-client")
	public @ResponseBody ResponseEntity insertClient
			(@RequestBody Client client)
	{
		return ResponseEntity.ok().body("ok");
	}

	@PostMapping("/insert-client-summary")
	public @ResponseBody ResponseEntity updateClientSummary
			(@RequestParam(name = "username") String username, @RequestParam(name = "trainerUsername") String trainerUsername,
			 @RequestParam(name = "healthGoal") String healthGoal, @RequestParam(name = "dietaryPreferences") String dietaryPreferences,
			 @RequestParam(name = "weight") Double weight, @RequestParam(name = "height") Double height, @RequestParam(name = "age") Integer age)
	{
		return ResponseEntity.ok().body("ok");
	}





	public static void main(String[] args) {
		SpringApplication.run(TrainerService.class, args);
	}


}
