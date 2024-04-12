package App.Repository;

import App.Entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRecords extends CrudRepository<Client, String> {
    @Query(value = "select * from client_records where username = :#{#username}", nativeQuery = true)
    public Client getClientByUsername(String username);

    @Query(value = "select exists(select username from client_records where username=:#{#username})", nativeQuery = true)
    public Long clientExists(@Param("username") String username);

    @Query(value ="select * from client_records where trainer_username = :#{#trainer_username}" ,nativeQuery = true)
    public List<Client> getClientsByTrainer_username(String trainer_username);
}
