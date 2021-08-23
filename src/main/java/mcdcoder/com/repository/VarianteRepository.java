package mcdcoder.com.repository;

import mcdcoder.com.domain.Variante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Variante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long> {}
