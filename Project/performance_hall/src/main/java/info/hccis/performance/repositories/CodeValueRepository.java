package info.hccis.performance.repositories;

import info.hccis.performance.jpa.entity.CodeValue;
import info.hccis.performance.jpa.entity.TicketOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeValueRepository extends CrudRepository<CodeValue, Integer> {
        List<CodeValue> findByCodeTypeId(Integer codeTypeId);
}