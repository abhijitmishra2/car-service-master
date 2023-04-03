package com.carbazaar.carservice.service.impl;

import com.carbazaar.carservice.adapter.DtoAdapter;
import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.CarsTrack;
import com.carbazaar.carservice.entity.RecommendationEntity;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import com.carbazaar.carservice.pojo.CarDto;
import com.carbazaar.carservice.pojo.RecommendationQueryDto;
import com.carbazaar.carservice.pojo.RecommendationQueryObj;
import com.carbazaar.carservice.repository.CarRepository;
import com.carbazaar.carservice.repository.CarsTrackRepository;
import com.carbazaar.carservice.service.CarService;
import com.carbazaar.carservice.service.RecommendationService;
import com.carbazaar.carservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecommendationServiceImpl implements RecommendationService {

    private final CarRepository carRepository;

    private final CarsTrackRepository carsTrackRepository;

    private final CarService carService;

    private final StorageService storageService;

    @Override
    public List<CarDto> getRecommendedCars(List<RecommendationQueryDto> recommendationQueryDto,String city) {

        Set<String> carSet = new HashSet<>();
        List<Car> recommendedCarResponse = new ArrayList<>();

        List<RecommendationQueryDto> dtos=recommendationQueryDto;

        //If User visits first time then read city data
        if(recommendationQueryDto.isEmpty())
        {
            CarsTrack carsTrack = carsTrackRepository.findByCityName(city);
            List<RecommendationEntity> cars = carsTrack.getCars();
            for (RecommendationEntity car:cars) {
                RecommendationQueryDto dto = DtoAdapter.convertToRecommendationQueryDto(car);
                dtos.add(dto);
            }
        }


        //Regular Flow
        if(recommendationQueryDto.size()>1) {
             dtos = recommendationQueryDto.stream().sorted(
                    Comparator.comparingInt(RecommendationQueryDto::getCount).reversed()
            ).collect(Collectors.toList());
        }
        List<String> cars = new ArrayList<>();

        System.out.println("DTO Size... "+dtos.size());



        if(dtos.size()>=3)
        {
            cars.add(dtos.get(0).getKey());
            cars.add(dtos.get(1).getKey());
            cars.add(dtos.get(2).getKey());
        }else if(dtos.size()==1)
        {
            //ToDo: Single one to be implemented
            Car car = carRepository.findByName(dtos.get(0).getKey());
            System.out.println("Car... "+car);
            List<CarDto> allSimilarCars = carService.findAllSimilarCars(car.getVariants().get(0).getId());
            System.out.println("Recommendation Service Find Similar Cars :: "+allSimilarCars);
            return allSimilarCars;
        }
        else{
            dtos.stream().forEach(
                    dto -> {
                        System.out.println("DTO... "+dto);
                        cars.add(dto.getKey());
                    }
            );
        }

        System.out.println("Cars... "+cars);
        RecommendationQueryObj recommendationQueryObj = compareCars(cars);
        System.out.println("Recommendation Query Params"+recommendationQueryObj);

        List<Variant> recommendedVariants = carRepository.getRecommendedCars(recommendationQueryObj);
        System.out.println("Recommended Cars");
        recommendedVariants.stream().forEach(variant -> {
            carSet.add(variant.getCar().getName());
        });
        System.out.println(carSet);

        carSet.stream().forEach(carName -> {
            Car car = carRepository.findByName(carName);
            System.out.println(car);
            recommendedCarResponse.add(car);
        });


        return recommendedCarResponse.stream().map(car -> {
            CarDto carDto = DtoAdapter.convertCarEntityToDto(car);
            carDto.setImageUrlList(storageService.getFileData(carDto.getImageUrlList()));
            return carDto;
        }).collect(Collectors.toList());
    }

    public RecommendationQueryObj compareCars(List<String> carNames) {
        RecommendationQueryObj recommendationQueryObj = new RecommendationQueryObj();

        BigDecimal minPrice,maxPrice;
        long min=100000000;
        long max=0;

        List<Car> cars=new ArrayList<>();

        Set<Double> mileage = new TreeSet<>();
        Set<FuelType> fuelTypes = new HashSet<>();
        Set<TransmissionType> transmissionTypes = new HashSet<>();
        Set<String> brandNames = new HashSet<>();

        cars=carNames.stream().map(carName->{
            return carRepository.findByName(carName);
        }).collect(Collectors.toList());


        for (Car car:cars) {
            if(car.getVariants().get(0).getExShowroomPrice().longValue()<min)
            {
                min=car.getVariants().get(0).getExShowroomPrice().longValue();
            }
        }

        for (Car car:cars) {
            if(car.getVariants().get(car.getVariants().size()-1).getExShowroomPrice().longValue()>max)
            {
                max=car.getVariants().get(car.getVariants().size()-1).getExShowroomPrice().longValue();
            }
        }

        minPrice=BigDecimal.valueOf(min);
        maxPrice=BigDecimal.valueOf(max);

       /* Car car1 = carRepository.findByName(carNames.get(0));
        Car car2 = carRepository.findByName(carNames.get(1));
        Car car3 = carRepository.findByName(carNames.get(2));

        BigDecimal car1MinPrice = car1.getVariants().get(0).getExShowroomPrice();
        minPrice=car1MinPrice;

        BigDecimal car2MinPrice = car2.getVariants().get(0).getExShowroomPrice();
        BigDecimal car3MinPrice = car3.getVariants().get(0).getExShowroomPrice();

        BigDecimal car1MaxPrice = car1.getVariants().get(car1.getVariants().size() - 1).getExShowroomPrice();
        maxPrice=car1MaxPrice;

        BigDecimal car2MaxPrice = car2.getVariants().get(car2.getVariants().size() - 1).getExShowroomPrice();
        BigDecimal car3MaxPrice = car3.getVariants().get(car3.getVariants().size()-1).getExShowroomPrice();

        if(car1MinPrice.compareTo(car2MinPrice)==1)
        {
          minPrice=car2MinPrice;
        }

        if(minPrice.compareTo(car3MinPrice)==1)
        {
            minPrice=car3MinPrice;
        }

        if(car2MaxPrice.compareTo(maxPrice)==1)
        {
            maxPrice=car2MaxPrice;
        }

        if(car3MaxPrice.compareTo(maxPrice)==1)
        {
            maxPrice=car3MaxPrice;
        }

*/
        cars.forEach(car -> {
            car.getVariants().forEach(
                    variant -> {
                        mileage.add(variant.getMileage());
                    }
            );
        });

        cars.forEach(car -> {
            brandNames.add(car.getBrand().getName());
        });

        cars.forEach(car -> {
            car.getVariants().forEach(
                    variant -> {
                        fuelTypes.add(variant.getFuelType());
                    }
            );
        });

        cars.forEach(car -> {
            car.getVariants().forEach(
                    variant -> {
                        transmissionTypes.add(variant.getTransmissionType());
                    }
            );
        });
        //set variants
       /* car1.getVariants().stream().forEach(variant -> {
            mileage.add(variant.getMileage());
        });

        car2.getVariants().stream().forEach(variant -> {
            mileage.add(variant.getMileage());
        });

        car3.getVariants().stream().forEach(variant -> {
            mileage.add(variant.getMileage());
        });

        //set brand names
        brandNames.add(car1.getBrand().getName());
        brandNames.add(car2.getBrand().getName());
        brandNames.add(car3.getBrand().getName());
*/

        //set fuel type
       /* car1.getVariants().stream().forEach(variant -> {
            fuelTypes.add(variant.getFuelType());
        });

        car2.getVariants().stream().forEach(variant -> {
            fuelTypes.add(variant.getFuelType());
        });

        car3.getVariants().stream().forEach(variant -> {
            fuelTypes.add(variant.getFuelType());
        });

        //set Transmission type
        car1.getVariants().stream().forEach(variant -> {
            transmissionTypes.add(variant.getTransmissionType());
        });

        car2.getVariants().stream().forEach(variant -> {
            transmissionTypes.add(variant.getTransmissionType());
        });

        car3.getVariants().stream().forEach(variant -> {

            transmissionTypes.add(variant.getTransmissionType());
        });
*/
        /*fuelTypes.add("Both");
        fuelTypes.add("Petrol");
        fuelTypes.add("Diesel");

        transmissionTypes.add("Automatic");
        transmissionTypes.add("Manual");*/

        recommendationQueryObj.setBrandNames(brandNames);
        recommendationQueryObj.setMinPrice(minPrice.subtract(new BigDecimal(100000)));
        recommendationQueryObj.setMaxPrice(maxPrice.add(new BigDecimal(100000)));
        recommendationQueryObj.setMileage(mileage);
        recommendationQueryObj.setFuelTypes(fuelTypes);
        recommendationQueryObj.setTransmissionTypes(transmissionTypes);

        return recommendationQueryObj;
    }
}
