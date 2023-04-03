package com.carbazaar.carservice.repository;

import com.carbazaar.carservice.entity.Brand;
import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.pojo.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> ,CarSearchRepository,RecommendationRepository{
    Car findByIdAndStatus(Long id, boolean status);

    List<Car> findAllByBrandAndStatus(Brand brand, boolean status);

    List<Car> findAllByStatus(boolean status);

    List<Car> findAllByIdInAndStatus(Collection<Long> carIdList, boolean status);

    List<Car> findAllByManufacturingYearAndStatus(Integer year, boolean status);

    List<Car> findByVariantsAirbags(Integer airbags);

    Car findByName(String carName);
}
