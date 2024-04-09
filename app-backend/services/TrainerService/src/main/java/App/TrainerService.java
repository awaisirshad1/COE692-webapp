package App;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/Trainer")
@Slf4j
public class TrainerService {


	public static void main(String[] args) {
		SpringApplication.run(TrainerService.class, args);
	}

}
