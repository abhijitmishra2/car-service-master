package com.carbazaar.carservice.service;

import com.carbazaar.carservice.entity.Variant;

import java.util.List;

public interface VariantService {
    public List<Variant> saveVariants(List<Variant> variants,Long carId);
    public Variant getVariant(Long variantId);
    public List<Variant> compareVariants(List<Long> variantIds);
    public Variant deleteVariant(long variantId);
    public List<Variant> findAllVariants();
    public List<Variant> findAllVariantsByCarId(long carId);
    public long findVariantID(String carName,String variantName);
}
