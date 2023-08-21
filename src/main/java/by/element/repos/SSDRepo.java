package by.element.repos;

import by.element.model.SSD;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SSDRepo extends CrudRepository<SSD, Integer>, JpaSpecificationExecutor<SSD> {
    @Query(value = "SELECT ssd.model FROM SSD ssd")
    List<String> getAllModels();

    SSD findByModel(String model);
}