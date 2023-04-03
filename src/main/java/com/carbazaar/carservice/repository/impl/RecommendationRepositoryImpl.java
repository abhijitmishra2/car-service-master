package com.carbazaar.carservice.repository.impl;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import com.carbazaar.carservice.pojo.RecommendationQueryObj;
import com.carbazaar.carservice.repository.RecommendationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecommendationRepositoryImpl implements RecommendationRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Variant> getRecommendedCars(RecommendationQueryObj recommendationQueryObj) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Variant> criteriaQuery = criteriaBuilder.createQuery(Variant.class);

        Root<Variant> variantRoot = criteriaQuery.from(Variant.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantRoot.get("exShowroomPrice"), recommendationQueryObj.getMinPrice()));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(variantRoot.get("exShowroomPrice"), recommendationQueryObj.getMaxPrice()));

        Expression<String> expression= variantRoot.get("car").get("brand").get("name");
        predicates.add(expression.in(recommendationQueryObj.getBrandNames()));

        Expression<TransmissionType> expression1= variantRoot.get("transmissionType");
        predicates.add(expression1.in(recommendationQueryObj.getTransmissionTypes()));

        Expression<FuelType> expression2= variantRoot.get("fuelType");
        predicates.add(expression2.in(recommendationQueryObj.getFuelTypes()));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
