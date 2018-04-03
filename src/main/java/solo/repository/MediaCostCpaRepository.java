package solo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import solo.domain.MediaCostCpa;

import java.util.Optional;

/**
 * @author Matthew Williams
 */
public interface MediaCostCpaRepository extends JpaRepository<MediaCostCpa, Long> {

    Page<MediaCostCpa> findByIdGreaterThan(Long id, Pageable pageable);
}
