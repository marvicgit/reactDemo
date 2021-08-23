package mcdcoder.com.repository;

import mcdcoder.com.domain.CompraDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompraDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {}
