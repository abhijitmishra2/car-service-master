package com.carbazaar.carservice.repository;

import com.carbazaar.carservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository< Brand, Long> {
    Brand findByIdAndStatus(Long id, boolean status);

    List<Brand> findAllByStatus(boolean b);

    Optional<Brand> findByNameIgnoreCase(String name);
}
