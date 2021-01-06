package tacos.starter_jpa.data;

import org.springframework.data.repository.CrudRepository;

import tacos.starter_jpa.User;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
