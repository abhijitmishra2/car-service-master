package com.carbazaar.carservice.service.impl;

import com.carbazaar.carservice.adapter.DtoAdapter;
import com.carbazaar.carservice.entity.*;
import com.carbazaar.carservice.exception.ApiValidationException;
import com.carbazaar.carservice.exception.AssetNotFoundException;
import com.carbazaar.carservice.pojo.*;
import com.carbazaar.carservice.repository.BrandRepository;
import com.carbazaar.carservice.repository.CarRepository;
import com.carbazaar.carservice.repository.CarsTrackRepository;
import com.carbazaar.carservice.repository.VariantRepository;
import com.carbazaar.carservice.service.CarService;
import com.carbazaar.carservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final VariantRepository variantRepository;
    private final CarsTrackRepository carsTrackRepository;
    private final StorageService storageService;

    @Override
    public CarDto getCarById(Long id, String city) {
        AtomicBoolean found = new AtomicBoolean(false);
        Car car = Optional.ofNullable(carRepository.findByIdAndStatus(id, true))
                .orElseThrow(() -> new AssetNotFoundException("No car found with id " + id));
        //TODO: add onRoad Price here

        CarsTrack carsTrack = carsTrackRepository.findByCityName(city);
        System.out.println("Cars track DB Fetch... " + carsTrack);
        if (carsTrack != null) {
            carsTrack.getCars().stream().forEach(
                    carData -> {
                        if (carData.getKey().equalsIgnoreCase(car.getName())) {
                            found.set(true);
                            carData.setCount(carData.getCount() + 1);
                        }
                    }
            );

            if (!found.get()) {
                RecommendationEntity recommendationEntity = new RecommendationEntity(car.getName(), 1);
                carsTrack.getCars().add(recommendationEntity);
            }
        } else {
            RecommendationEntity recommendationEntity = new RecommendationEntity(car.getName(), 1);
            List<RecommendationEntity> list = new ArrayList<>();
            list.add(recommendationEntity);

            carsTrack = new CarsTrack();
            carsTrack.setCityName(city);
            carsTrack.setCars(list);
            System.out.println("Cars Track... " + carsTrack);
        }


        CarsTrack carsTrackSavedResponse = carsTrackRepository.save(carsTrack);
        System.out.println("cars track saved response" + carsTrackSavedResponse);

        CarDto carDto = DtoAdapter.convertCarEntityToDto(car);
        carDto.setImageUrlList(storageService.getFileData(carDto.getImageUrlList()));
        return carDto;
    }

    @Override
    public List<CarDto> saveCarDetails(List<CarDto> carDtoList) {
        List<CarDto> response = new ArrayList<>();
        carDtoList.forEach(carDto -> {
            Brand brand = Optional.ofNullable(brandRepository.findByIdAndStatus(carDto.getBrand().getId(), true))
                    .orElseThrow(() -> new AssetNotFoundException("No brand found with ID " + carDto.getBrand().getId()));
            Car car = DtoAdapter.convertCarDtoToEntity(carDto, brand);
            car = carRepository.save(car);
            CarDto res = DtoAdapter.convertCarEntityToDto(car);
            res.setImageUrlList(storageService.getFileData(res.getImageUrlList()));
            response.add(res);
        });

        return response;
    }

    @Override
    public List<CarDto> getCars(Long brandId) {
        if (Objects.nonNull(brandId)) {
            Brand brand = Optional.ofNullable(brandRepository.findByIdAndStatus(brandId, true))
                    .orElseThrow(() -> new AssetNotFoundException(("No brand found with ID " + brandId)));
            List<Car> carList = carRepository.findAllByBrandAndStatus(brand, true);
            return carList.stream().map(car -> {
                CarDto carDto = DtoAdapter.convertCarEntityToDto(car);
                carDto.setImageUrlList(storageService.getFileData(carDto.getImageUrlList()));
                return carDto;
            }).collect(Collectors.toList());
        }

        return carRepository.findAllByStatus(true).stream().map(DtoAdapter::convertCarEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByIds(List<Long> carIdList) {
        List<Car> carList = carRepository.findAllByIdInAndStatus(carIdList, true);
        return carList.stream().map(car -> {
            CarDto carDto = DtoAdapter.convertCarEntityToDto(car);
            carDto.setImageUrlList(storageService.getFileData(carDto.getImageUrlList()));
            return carDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CarListingDto> fetchLatestCars() {
        Integer year = Year.now(ZoneId.of("Asia/Kolkata")).getValue();
        log.info("Current Year = {}", year);
        List<Car> carList = carRepository.findAllByManufacturingYearAndStatus(year, true);
        return carList.stream().map(car -> {
            CarListingDto carListingDto = DtoAdapter.convertCarEntityToListingDto(car);
            carListingDto.setImageUrlList(storageService.getFileData(carListingDto.getImageUrlList()));
            return carListingDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SearchResultsDto> searchCarDetails(SearchDto searchDto) {

        System.out.println(searchDto.toString());
        final List<Variant> searchResults = variantRepository.findVariants(searchDto);
        return searchResults.stream().map(search -> {
            SearchResultsDto searchResultsDto = DtoAdapter.convertVariantEntityToDto(search);
            searchResultsDto.setImageUrlList(storageService.getFileData(searchResultsDto.getImageUrlList()));
            return  searchResultsDto;
        }).collect(Collectors.toList());
    }

    public Variant getVariantDetailsByCarNameAndVariantName(String carName, String variantName) {
        Variant variant = carRepository.findVariant(carName, variantName);
        variant.setImageUrlList(storageService.getFileData(variant.getImageUrlList()));
        return variant;
    }

    @Override
    public List<Car> findAllNewCarsInCity(List<String> brandNames) {
        System.out.println("New Cars IN City");
        System.out.println("BrandNames... " + brandNames);
        List<Car> newCarsInCity = carRepository.findAllCarsInCity(brandNames);
        newCarsInCity.forEach(car -> car.setImageUrlList(storageService.getFileData(car.getImageUrlList())));
        return newCarsInCity;
    }

    @Override
    public List<Car> findAllLatestCars(List<String> brandNames) {
        List<Car> carList = carRepository.findAllLatestCarsInCity(brandNames);
        carList.forEach(car -> car.setImageUrlList(storageService.getFileData(car.getImageUrlList())));
        return carList;
    }

    @Override
    public List<CarDto> findAllSimilarCars(long variantId) {
        Variant variant = variantRepository.findById(variantId).get();
        String carNameResponse = variant.getCarName();
        HashSet<String> carSet = new HashSet<>();
        List<Car> similarCarResponse = new ArrayList<>();

        List<Variant> variants = variantRepository.findByFuelTypeAndBodyType(variant.getFuelType(), variant.getBodyType());
        variants.stream().forEach(variant1 -> {
            carSet.add(variant1.getCar().getName());
        });
        System.out.println(carSet);

        carSet.stream().forEach(carName -> {
            if (!carName.equalsIgnoreCase(carNameResponse)) {
                Car car = carRepository.findByName(carName);
                System.out.println(car);
                similarCarResponse.add(car);
            }
        });
        System.out.println("Similar Car Response:: " + similarCarResponse);
        return similarCarResponse.stream().map(car -> {
            CarDto carDto = DtoAdapter.convertCarEntityToDto(car);
            carDto.setImageUrlList(storageService.getFileData(carDto.getImageUrlList()));
            return carDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getCarImages(String carName) {
        Car car = carRepository.findByName(carName);
        car.setImageUrlList(storageService.getFileData(car.getImageUrlList()));
        return car.getImageUrlList();
    }

    @Override
    public List<String> updateCarImages(Long id, List<String> imagePathList) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setImageUrlList(imagePathList);
            carRepository.save(car);
            return imagePathList;
        }

        throw new ApiValidationException("No Car found with id : " + id);
    }
}
