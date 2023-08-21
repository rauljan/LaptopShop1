package by.element.repos;

import by.element.model.Display;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DisplayRepo extends CrudRepository<Display, Integer>, JpaSpecificationExecutor<Display> {
    @Query(value = "SELECT d.model FROM Display d")
    List<String> getAllModels();

    Display findByModel(String model);
}