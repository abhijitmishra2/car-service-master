package com.carbazaar.carservice.service.impl;

import com.carbazaar.carservice.adapter.DtoAdapter;
import com.carbazaar.carservice.entity.Brand;
import com.carbazaar.carservice.exception.ApiValidationException;
import com.carbazaar.carservice.exception.AssetNotFoundException;
import com.carbazaar.carservice.pojo.BrandDto;
import com.carbazaar.carservice.repository.BrandRepository;
import com.carbazaar.carservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandDto> getAllBrandsList() {
        return brandRepository.findAllByStatus(true)
                .stream()
                .map(DtoAdapter::convertBrandEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> saveBrand(List<BrandDto> brandDtoList) {

        List<BrandDto> response = new ArrayList<>();

        brandDtoList.forEach(brandDto -> {
            Optional<Brand> brandOptional = brandRepository.findByNameIgnoreCase(brandDto.getName());
            if(brandOptional.isPresent()) {
                Brand brand = brandOptional.get();
                log.info("Brand with name {} and id {} already present", brand.getName(), brand.getId());
                if(brandOptional.get().isStatus()) { //Checking if the record was present, but deleted
                    log.warn("Record already present, skipping");
                    throw new ApiValidationException("Brand with id " + brand.getId() + " and name " + brand.getName() + " already exists");
                }
                log.info("{} was previously deleted, making it available again", brand.getName());
                brand.setLogoUrl(brandDto.getLogoUrl());
                response.add(DtoAdapter.convertBrandEntityToDto(brandRepository.save(brand)));
            }

            Brand brand = Brand.builder()
                    .name(brandDto.getName())
                    .logoUrl(brandDto.getLogoUrl())
                    .build();

            response.add(DtoAdapter.convertBrandEntityToDto(brandRepository.save(brand)));
        });

        return response;
    }

    @Override
    public String updateBrandImage(Long id, String filePath) {
        Brand brand = Optional.ofNullable(brandRepository.findByIdAndStatus(id, true))
                .orElseThrow(() -> new AssetNotFoundException("No Brand found for id " + id));

        brand.setLogoUrl(filePath);
        brandRepository.save(brand);
        return filePath;
    }

}
