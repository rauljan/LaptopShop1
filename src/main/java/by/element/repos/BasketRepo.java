package by.element.repos;

import by.element.model.Basket;
import by.element.model.Client;
import by.element.model.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BasketRepo extends CrudRepository<Basket, Integer>, JpaSpecificationExecutor<Basket> {
    @Query(value = "SELECT b.id FROM Basket b")
    List<Integer> getAllIds();

    @Query(value = "SELECT b.employee FROM Basket b")
    List<Employee> getEmployees();

    @Query(value = "SELECT b.client FROM Basket b")
    List<Client> getClients();
}