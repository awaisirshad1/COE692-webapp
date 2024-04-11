package App.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class TrainerClientList {
    Trainer trainer;
    List<Client> clientList;


}
