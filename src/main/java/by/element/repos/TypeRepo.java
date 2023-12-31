package by.element.repos;

import by.element.model.Type;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TypeRepo extends CrudRepository<Type, Integer>, JpaSpecificationExecutor<Type> {
    @Query(value = "SELECT t.name FROM Type t")
    List<String> getAllNames();

    List<Type> findByName(String name);
}