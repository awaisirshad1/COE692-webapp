package App.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
public class Client {
    @Id
    @Getter @Setter
    String username;
    @Getter @Setter
    String trainer_username;
    @Getter @Setter
    String health_goal;
    @Getter @Setter
    String dietaryPreferences;
    @Getter @Setter
    Integer weight;
    @Getter @Setter
    Integer height;
    @Getter @Setter
    Integer age;


    public Client() {}
}
