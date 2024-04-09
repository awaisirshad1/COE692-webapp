package App.Repository;

import App.Entity.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends CrudRepository<Trainer, String> {
    @Query(value = "select username from Trainer where username = :#{#username}", nativeQuery = true)
    public String getTrainerByUsername(@Param("username") String username);
}
