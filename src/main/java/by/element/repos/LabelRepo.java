package by.element.repos;

import by.element.model.Label;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LabelRepo extends CrudRepository<Label, Integer>, JpaSpecificationExecutor<Label> {
    @Query(value = "SELECT l.model FROM Label l")
    List<String> getAllModels();

    Label findByModel(String model);
}