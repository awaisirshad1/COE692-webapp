package App;

import App.Entity.Trainer;
import App.Repository.ClientRecords;
import App.Repository.TrainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/insert-trainer")
	public @ResponseBody ResponseEntity insertTrainer
			(@RequestParam(name = "username") String username)
	{
		log.info("request received");
		ResponseEntity response;
		Long trainerExists = trainerRepository.trainerExists(username);
		if(trainerExists == 1){
			log.info("trainer exists");
			response = ResponseEntity.badRequest().body("trainer already exists");
			return response;
		}
		Trainer trainer = new Trainer();
		trainer.setUsername(username);
		trainerRepository.save(trainer);
		log.info("inserted trainer");
		response = ResponseEntity.ok().body("inserted trainer");
		return response;
	}

	@PostMapping("/insert-client")
	public @ResponseBody ResponseEntity insertClient
			(@RequestParam(name = "username") String username)
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
