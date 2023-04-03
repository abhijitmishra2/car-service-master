package com.carbazaar.carservice.repository;

import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface VariantRepository extends JpaRepository<Variant,Long>,CarSearchRepository {
    List<Variant> findByFuelTypeAndBodyType(FuelType fuelType, BodyType bodyType);

    // List<Variant> findVariantsByAirbagsGreaterThanEqualAndMileageGreaterThanEqual(int airBags, double mileage);

  // List<Variant> findVariantsByExShowroomPriceBetween(BigDecimal valueOf, BigDecimal valueOf1);
}
