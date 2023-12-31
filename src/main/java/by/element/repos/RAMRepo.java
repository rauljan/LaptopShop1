package by.element.repos;

import by.element.model.RAM;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RAMRepo extends CrudRepository<RAM, Integer>, JpaSpecificationExecutor<RAM> {
    @Query(value = "SELECT ram.model FROM RAM ram")
    List<String> getAllModels();

    RAM findByModel(String model);
}