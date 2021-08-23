package mcdcoder.com.repository;

import mcdcoder.com.domain.SubCategoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {}
