package App.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
public class User {

    @Id
    @Getter @Setter
    String username;
    @Setter
    String password;
    @Getter @Setter
    String first_name;
    @Getter @Setter
    String last_name;
    @Getter @Setter
    Boolean is_trainer;

     public User(String username, String password, String firstName, String lastName, Boolean isTrainer){
        this.setUsername(username);
        this.setPassword(password);
        this.setFirst_name(firstName);
        this.setLast_name(lastName);
        this.setIs_trainer(isTrainer);
    }

    public User() {}

    @Override
    public String toString(){
         return "\nUSER:\n\t[username]:"+username+"\n\t[first_name]:"+first_name+"\n\t[last_name]:"+last_name
                 +"\n\t[is_trainer]:"+is_trainer;
    }
}
