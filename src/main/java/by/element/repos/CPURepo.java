package by.element.repos;

import by.element.model.CPU;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CPURepo extends CrudRepository<CPU, Integer>, JpaSpecificationExecutor<CPU> {
    @Query(value = "SELECT cpu.model FROM CPU cpu")
    List<String> getAllModels();

    CPU findByModel(String model);
}