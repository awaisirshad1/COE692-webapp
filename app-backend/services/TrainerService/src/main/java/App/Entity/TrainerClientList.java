package App.Entity;


import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder
public class TrainerClientList {
    @Getter @Setter
    Trainer trainer;
    @Getter
    Map<String,Client> clientList;

    public TrainerClientList(Trainer trainer){
        this.trainer = trainer;
        this.clientList = new HashMap<>();
    }

    public boolean addClient(Client client){
        if(this.clientList.get(client.getUsername())==null){
            clientList.put(client.getUsername(), client);
            return true;
        }
        return false;
    }

    public void insertClient(Client client){
        if(this.clientList.get(client.getUsername())==null){
            clientList.put(client.getUsername(), client);
        }
    }

    public void setClientList(List<Client> clientList){
        clientList.forEach(this::insertClient);
    }

}
