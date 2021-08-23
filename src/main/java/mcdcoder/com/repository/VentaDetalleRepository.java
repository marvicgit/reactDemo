package mcdcoder.com.repository;

import mcdcoder.com.domain.VentaDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VentaDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {}
