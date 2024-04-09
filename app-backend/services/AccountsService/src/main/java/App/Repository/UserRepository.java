package App.Repository;

import App.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {
    @Query(value = "select exists(select username from User where username=:#{#username})", nativeQuery = true)
    public Long usernameExists(@Param("username") String username);

    public User searchUserByUsername(String username);

    @Query(value = "select password from User where username=:#{#username}", nativeQuery = true)
    public String loginCheck(@Param("username") String username);
}
