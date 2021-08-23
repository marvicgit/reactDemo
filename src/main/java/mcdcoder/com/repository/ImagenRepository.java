package mcdcoder.com.repository;

import mcdcoder.com.domain.Imagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Imagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {}
