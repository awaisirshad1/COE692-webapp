package App.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainer")
@Builder
public class Trainer {
    @Id
    @Getter @Setter
    String username;
    @Transient @Getter @Setter
    List<Client> clientList;

    public Trainer(String username){
        this.username = username;
        this.clientList = new ArrayList<>();
    }

    public Trainer(){}
}
