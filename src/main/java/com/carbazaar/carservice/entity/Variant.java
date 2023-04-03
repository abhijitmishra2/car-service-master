package com.carbazaar.carservice.entity;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import com.carbazaar.carservice.entity.helper.StringListConverter;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "variantdetails")
public class Variant extends AbstractJpaEntity {


    @Column(name = "name", nullable = false)
    private String variantName;
    private BigDecimal exShowroomPrice;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private String fuelCapacity;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> color;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    private Boolean powerSteering;
    private int engine;
    private Double safetyRating;
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    private Double mileage;
    private Double length;
    private Double width;
    private Double height;
    private Double groundClearance;
    private String maxPower;
    private String maxTorque;
    private String powerWindow;
    private Boolean powerBoot;
    private Boolean abs;
    private Boolean breakAssist;
    private Integer airbags;
    private Boolean centralLocking;
    private Boolean powerDoorLocks;
    @Transient
    private long rtoCharge;
    @Transient
    private long fastTag;
    @Transient
    private long onRoadPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Car car;



    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }
    public BigDecimal getExShowroomPrice() {
        return exShowroomPrice;
    }

    public void setExShowroomPrice(BigDecimal exShowroomPrice) {
        this.exShowroomPrice = exShowroomPrice;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(String fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Boolean getPowerSteering() {
        return powerSteering;
    }

    public void setPowerSteering(Boolean powerSteering) {
        this.powerSteering = powerSteering;
    }

    public Double getSafetyRating() {
        return safetyRating;
    }

    public void setSafetyRating(Double safetyRating) {
        this.safetyRating = safetyRating;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getGroundClearance() {
        return groundClearance;
    }

    public void setGroundClearance(Double groundClearance) {
        this.groundClearance = groundClearance;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }

    public String getMaxTorque() {
        return maxTorque;
    }

    public void setMaxTorque(String maxTorque) {
        this.maxTorque = maxTorque;
    }

    public String getPowerWindow() {
        return powerWindow;
    }

    public void setPowerWindow(String powerWindow) {
        this.powerWindow = powerWindow;
    }

    public Boolean getPowerBoot() {
        return powerBoot;
    }

    public void setPowerBoot(Boolean powerBoot) {
        this.powerBoot = powerBoot;
    }

    public Boolean getAbs() {
        return abs;
    }

    public void setAbs(Boolean abs) {
        this.abs = abs;
    }

    public Boolean getBreakAssist() {
        return breakAssist;
    }

    public void setBreakAssist(Boolean breakAssist) {
        this.breakAssist = breakAssist;
    }

    public Integer getAirbags() {
        return airbags;
    }

    public void setAirbags(Integer airbags) {
        this.airbags = airbags;
    }

    public Boolean getCentralLocking() {
        return centralLocking;
    }

    public void setCentralLocking(Boolean centralLocking) {
        this.centralLocking = centralLocking;
    }

    public Boolean getPowerDoorLocks() {
        return powerDoorLocks;
    }

    public void setPowerDoorLocks(Boolean powerDoorLocks) {
        this.powerDoorLocks = powerDoorLocks;
    }

    public String getCarName()
    {
        return car.getName();
    }

    public Brand getCarBrand()
    {
        return car.getBrand();
    }

    public int getManufacturingYear()
    {
        return car.getManufacturingYear();
    }

    public int getSeatCapacity()
    {
        return car.getSeatCapacity();
    }

    public List<String> getImageUrlList()
    {
        return car.getImageUrlList();
    }

    public void setImageUrlList(List<String> imageUrlList) {
        car.setImageUrlList(imageUrlList);
    }

    public Long getCarId()
    {
        return car.getId();
    }
    @JsonIgnore
    public Car getCar()
    {
        return car;
    }

    @JsonIgnore
    public void setCar(Car car)
    {
        this.car=car;
    }

    public long getRtoCharge() {
        return rtoCharge;
    }

    public void setRtoCharge(long rtoCharge) {
        this.rtoCharge = rtoCharge;
    }

    public long getFastTag() {
        return fastTag;
    }

    public void setFastTag(long fastTag) {
        this.fastTag = fastTag;
    }

    public long getOnRoadPrice() {
        return onRoadPrice;
    }

    public void setOnRoadPrice(long onRoadPrice) {
        this.onRoadPrice = onRoadPrice;
    }


}
