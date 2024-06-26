package App.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "trainer")
@Builder
@AllArgsConstructor
public class Trainer {
    @Id
    @Getter @Setter
    String username;

    public Trainer(){}

    @Override
    public String toString(){
        return "[Trainer]: {username="+this.getUsername()+"}";
    }
}
