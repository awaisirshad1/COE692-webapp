package App.Repository;

import App.Entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRecords extends CrudRepository<Client, String> {

}
