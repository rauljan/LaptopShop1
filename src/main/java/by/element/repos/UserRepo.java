package by.element.repos;

import by.element.security.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}