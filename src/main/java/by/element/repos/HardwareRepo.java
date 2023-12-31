package by.element.repos;

import by.element.model.Hardware;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface HardwareRepo extends CrudRepository<Hardware, Integer>, JpaSpecificationExecutor<Hardware> {
    @Query(value = "SELECT h.assemblyName FROM Hardware h")
    List<String> getAllAssemblyNames();

    Hardware findByAssemblyName(String assemblyName);
}