package by.element.repos;

import by.element.model.Availability;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AvailabilityRepo extends CrudRepository<Availability, Integer>, JpaSpecificationExecutor<Availability> { }