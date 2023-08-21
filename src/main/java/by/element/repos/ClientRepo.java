package by.element.repos;

import by.element.model.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ClientRepo extends CrudRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
    @Query(value = "SELECT c.id FROM Client c")
    List<Integer> getAllIds();
}