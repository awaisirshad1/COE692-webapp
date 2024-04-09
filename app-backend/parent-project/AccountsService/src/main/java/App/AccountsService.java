package App;

import App.Entity.User;
import App.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/Accounts")
@Slf4j
public class AccountsService {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccountsService.class, args);
	}

	@GetMapping("/GET")
	public ResponseEntity getRequest(@RequestParam(name="username") String username, @RequestParam(name="password") String password){
		ResponseEntity response;
		log.info("get request received");
		User testOp = userRepository.searchUserByUsername(username);
		log.info("testOp: "+testOp);
		String internal = userRepository.loginCheck(username);
		if(password.equals(internal)){
			log.info("success, respond");
			response = ResponseEntity.ok().body("success");
		}else{
			log.info("failure, respond");
			response = ResponseEntity.badRequest().body("invalid login");
		}
		return response;
	}

	@PostMapping("/POST")
	public @ResponseBody ResponseEntity postRequest
			(@RequestParam(name="username") String username,@RequestParam(name="password") String password,
		  	 @RequestParam(name="firstName") String firstName, @RequestParam(name="lastName") String lastName,
		     @RequestParam(name="isTrainer") Boolean isTrainer)
	{
		ResponseEntity response;
		// Regex checks
		boolean validUsernameRegex = validateUsername(username);
		boolean validPasswordRegex = validatePassword(password);
		if(!(validPasswordRegex || validUsernameRegex)){
			log.info("regex checks failed");
			response = ResponseEntity.ok().body("invalid username or password");
			return response;
		}

		// Duplicate username checks
		Long resultLong = userRepository.usernameExists(username);
		log.info("resultLong: "+resultLong);
		if(resultLong==1){
			log.info("account exists");
			response = ResponseEntity.ok().body("username already in use");
			return response;
		}

		Boolean setIsTrainer = Boolean.valueOf(isTrainer);
		log.info("isTrainer conversion: " +setIsTrainer);

		// username and regex checks passed, create user
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirst_name(firstName);
		user.setLast_name(lastName);
		user.setIs_trainer(setIsTrainer);

		userRepository.save(user);
		log.info("use saved");
		response = ResponseEntity.ok().body("Created account");
		return response;
	}
	//Regex methods
	public boolean validateUsername (String username)
	{
		String regex = "^[a-zA-Z0-9_]+$";
		return username != null && username.matches(regex);
	}
	public boolean validatePassword (String password )
	{
		String regex = "^[a-zA-Z0-9_]+$";
		return password != null && password.matches(regex);
	}


}
