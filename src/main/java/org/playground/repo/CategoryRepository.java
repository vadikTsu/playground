package org.playground.repo;

import org.playground.domain.Category;
import org.playground.domain.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    List<Lot> findByCategoryId(@Param("id")Long id);
//
//    Page<Lot> findByNameContaining(@Param("name") String name, Pageable pageable);
}
