package com.carbazaar.carservice.repository.impl;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.TransmissionType;
import com.carbazaar.carservice.pojo.SearchDto;
import com.carbazaar.carservice.repository.CarSearchRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CarSearchRepositoryImpl implements CarSearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Variant> findVariants(SearchDto searchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Variant> criteriaQuery = criteriaBuilder.createQuery(Variant.class);

        Root<Variant> variantRoot = criteriaQuery.from(Variant.class);
        List<Predicate> predicates = new ArrayList<>();

        //min price filter
        if(searchDto.getMinPrice()>=0)
        {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("exShowroomPrice"), BigDecimal.valueOf(searchDto.getMinPrice())));
        }

        //max price filter
        if(searchDto.getMaxPrice()!=0)
        {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(variantRoot.get("exShowroomPrice"), BigDecimal.valueOf(searchDto.getMaxPrice())));
        }else{
            predicates.add(criteriaBuilder.lessThanOrEqualTo(variantRoot.get("exShowroomPrice"), BigDecimal.valueOf(2000000)));
        }

        //transmission type filter
        if(searchDto.getTransmissionType().size()==1)
        {
            //Expression<String> expression= variantRoot.get("transmissionType");
            //predicates.add(expression.in(searchDto.getTransmissionType()));
            List<TransmissionType> transmissionType = searchDto.getTransmissionType();
            predicates.add(criteriaBuilder.equal(variantRoot.get("transmissionType"), transmissionType.get(0)));
            //predicates.add(criteriaBuilder.equal(variantRoot.get("transmissionType"), TransmissionType.valueOf(searchDto.getTransmissionType())));
        }

        //FuelType filter
        if(searchDto.getFuelType().size()>0)
        {
            Expression<String> expression= variantRoot.get("fuelType");
            predicates.add(expression.in(searchDto.getFuelType()));
            //predicates.add(criteriaBuilder.equal(variantRoot.get("fuelType"), FuelType.valueOf(searchDto.getFuelType())));
        }

        //BodyType filter
        if(searchDto.getBodyType().size()>0)
        {
            Expression<String> expression= variantRoot.get("bodyType");
            predicates.add(expression.in(searchDto.getBodyType()));
           // predicates.add(criteriaBuilder.equal(variantRoot.get("bodyType"), BodyType.valueOf(searchDto.getBodyType())));
        }

        //safety rating
        if(searchDto.getSafetyRatings()>0)
        {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("safetyRating"),searchDto.getSafetyRatings()));
        }

        //mileage
        if(searchDto.getMileage()>0)
        {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("mileage"),searchDto.getMileage()));
        }

        //airbag
        if(searchDto.getAirBags()>0)
        {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("airbags"),searchDto.getAirBags()));
        }

        //seatCapacity
        if(searchDto.getSeatCapacity().size()>0)
        {
            List<Integer> seatCapacity = searchDto.getSeatCapacity();
            int size = searchDto.getSeatCapacity().size();
            //Expression<String> expression= variantRoot.get("bodyType");
            predicates.add(criteriaBuilder.between(variantRoot.get("car").get("seatCapacity"),seatCapacity.get(0),seatCapacity.get(size-1)));
            //predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("car").get("seatCapacity"),searchDto.getSeatCapacity()));
        }

        if(searchDto.getBrandNames().size()>0)
        {
            Expression<String> expression= variantRoot.get("car").get("brand").get("name");
            predicates.add(expression.in(searchDto.getBrandNames()));
        }

        if(!StringUtils.isEmpty(searchDto.getBrandName()))
        {
            predicates.add(criteriaBuilder.equal(variantRoot.get("car").get("brand").get("name"),searchDto.getBrandName()));
        }

        if(!StringUtils.isEmpty(searchDto.getCarName()))
        {
            predicates.add(criteriaBuilder.equal(variantRoot.get("car").get("name"),searchDto.getCarName()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Variant findVariant(String carName, String variantName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Variant> criteriaQuery = criteriaBuilder.createQuery(Variant.class);

        Root<Variant> variantRoot = criteriaQuery.from(Variant.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(variantRoot.get("variantName"),variantName));
        predicates.add(criteriaBuilder.equal(variantRoot.get("car").get("name"),carName));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Car> findAllCarsInCity(List<String> brandNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);

        Root<Car> variantRoot = criteriaQuery.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();

        Expression<String> expression= variantRoot.get("brand").get("name");
        predicates.add(expression.in(brandNames));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> findAllLatestCarsInCity(List<String> brandNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);

        Root<Car> variantRoot = criteriaQuery.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();

        Expression<String> expression= variantRoot.get("brand").get("name");
        predicates.add(expression.in(brandNames));

        predicates.add(criteriaBuilder.equal(variantRoot.get("manufacturingYear"),2021));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Variant> findAllVariantsByCarId(long carId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Variant> criteriaQuery = criteriaBuilder.createQuery(Variant.class);

        Root<Variant> variantRoot = criteriaQuery.from(Variant.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(variantRoot.get("car").get("id"),carId));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
