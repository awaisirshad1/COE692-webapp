package App.Repository;

import App.Entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClientRecords extends CrudRepository<Client, String> {
    @Query(value = "select * from client_records where username = :#{#username}", nativeQuery = true)
    public Client getClientByUsername(String username);

}
