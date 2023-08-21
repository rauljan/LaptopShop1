package by.element.repos;

import by.element.model.Buying;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BuyingRepo extends CrudRepository<Buying, Integer>, JpaSpecificationExecutor<Buying> { }