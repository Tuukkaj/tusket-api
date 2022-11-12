package fi.tuukka.tusketapi.api.time;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface TimeRepository extends CrudRepository<Time, Long> {
    Page<Time> findAll(Pageable pageable);
}
