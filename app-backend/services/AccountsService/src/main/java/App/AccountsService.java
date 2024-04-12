package App;

import App.Entity.User;
import App.Handler.ResponseHandler;
import App.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountsService {

	@Autowired
	UserRepository userRepository;

	@Value("${endpoints.TrainerServiceBaseUri}")
	private String trainerServiceBaseUri;


	@PostMapping("/login")
	public  ResponseEntity<Object> login(@RequestParam(name="username") String username, @RequestParam(name="password") String password){
		ResponseEntity response;
		log.info("get request received");
		User user = userRepository.searchUserByUsername(username);
		log.info("testOp: "+user);
		String internal = userRepository.loginCheck(username);
		String message;
		if(password.equals(internal)){
			log.info("success, respond");
			message = "successful login";

			return ResponseHandler.generateResponse(message, HttpStatus.OK, user.getIs_trainer());
//			response = ResponseEntity.ok().body("success");
		}else{
			log.info("failure, respond");
			message = "invalid login";
			response = ResponseEntity.badRequest().body("invalid login");
			return ResponseHandler.generateResponse(message, HttpStatus.UNAUTHORIZED, "");
		}
	}

	@PostMapping("/create")
	public @ResponseBody ResponseEntity createUser
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

		MultiValueMap<String, String> trainerServiceResponse = null;
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("username", user.getUsername());
		WebClient client = WebClient.create();
		if(user.getIs_trainer()){
			log.info("user is trainer, send req to trainer service");
			trainerServiceResponse = insertTrainerEntity(user, client, requestBody);
		}
		else{
			trainerServiceResponse = insertNonTrainerEntity(user, client, requestBody);
		}
		log.info("response: "+trainerServiceResponse.toString());
		// After all other services are called, save to repository
		userRepository.save(user);
		log.info("user saved to userRepository");
		//TODO
		// If trainer, insert to trainer repository, if not, insert to client repository

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

	/* When the account created is of type trainer,
	*  make a POST request to the trainer service
	*  insert entity into trainer service DB
	*/
	private MultiValueMap<String, String> insertTrainerEntity(User user, WebClient client, MultiValueMap<String, String> requestBody){
		//TODO
		final String targetUri = trainerServiceBaseUri.concat("/create-trainer");
		log.info("insertTrainerEntity targetUri: \'"+targetUri+"\'");
		LinkedMultiValueMap<String,String> responseMap = new LinkedMultiValueMap<>();

		ResponseEntity<String> response = client
				.post()
				.uri(targetUri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromFormData(requestBody))
				.retrieve()
				.toEntity(String.class)
				.block();

		log.info("String response: "+response.getBody());
		JSONObject responseObject = new JSONObject(response.getBody());
//		log.info("JSON object for response: "+responseObject);
//		log.info("JSON object get status: "+responseObject.get("status"));
		String statusString = (responseObject.getString("status")).toString().contains("success") ? "success": "failure";
		responseMap.add("status",statusString);
		return responseMap;
	}

	/* When the account created is not of type trainer,
	 *  make a POST request to the trainer service
	 *  insert entity into trainer service DB
	 */
	private MultiValueMap<String, String> insertNonTrainerEntity(User user, WebClient client, MultiValueMap<String,String> requestBody){
		//TODO
		final String targetUri = trainerServiceBaseUri.concat("/create-client");
		log.info("insertNonTrainerEntity targetUri: \'"+targetUri+"\'");
		return null;
	}


	public static void main(String[] args) {
		SpringApplication.run(AccountsService.class, args);
	}

}
