package App.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Builder
@AllArgsConstructor
public class Trainer {
    @Getter @Setter
    String username;

    public Trainer(){}

    @Override
    public String toString(){
        return "[Trainer]: {username="+this.getUsername()+"}";
    }
}
