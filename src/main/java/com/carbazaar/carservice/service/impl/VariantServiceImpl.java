package com.carbazaar.carservice.service.impl;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.repository.CarRepository;
import com.carbazaar.carservice.repository.VariantRepository;
import com.carbazaar.carservice.service.StorageService;
import com.carbazaar.carservice.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final CarRepository carRepository;
    private final StorageService storageService;

    @Override
    public List<Variant> saveVariants(List<Variant> variants,Long carId) {
        Optional<Car> byId = carRepository.findById(carId);
        Car car = byId.get();

        variants.forEach(
                (variant) -> {
                 variant.setCar(car);
                 variantRepository.save(variant);
                }
        );
        return variants;
    }

    @Override
    public Variant getVariant(Long variantId) {
        Variant variant = variantRepository.findById(variantId).get();
        variant.setImageUrlList(storageService.getFileData(variant.getImageUrlList()));
        return variant;
    }

    @Override
    public List<Variant> compareVariants(List<Long> variantIds) {
        List<Variant> resultSet = new ArrayList<>();

        variantIds.stream().forEach((variantId)->{
            Variant variant = variantRepository.findById(variantId).get();
            variant.setImageUrlList(storageService.getFileData(variant.getImageUrlList()));
            resultSet.add(variant);
        });
        return resultSet;
    }

    @Override
    public Variant deleteVariant(long variantId) {
        Variant variant = variantRepository.findById(variantId).get();
        variantRepository.delete(variant);
        return variant;
    }

    public List<Variant> findAllVariants(){
        return variantRepository.findAll();
    }

    @Override
    public List<Variant> findAllVariantsByCarId(long carId) {
        return variantRepository.findAllVariantsByCarId(carId);
    }

    @Override
    public long findVariantID(String carName, String variantName) {
        Variant variant = variantRepository.findVariant(carName, variantName);
        return variant.getId();
    }
}
