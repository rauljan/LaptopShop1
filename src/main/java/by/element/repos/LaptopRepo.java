package by.element.repos;

import by.element.model.Laptop;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LaptopRepo extends CrudRepository<Laptop, Integer>, JpaSpecificationExecutor<Laptop> {
    @Query(value = "SELECT l.label.model FROM Laptop l")
    List<String> getAllModels();

    Laptop findByLabelModel(String model);
}