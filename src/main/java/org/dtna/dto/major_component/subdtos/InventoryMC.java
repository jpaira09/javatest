package org.dtna.dto.major_component.subdtos;

import lombok.Data;

import java.util.List;

@Data
public class InventoryMC {
    private String name;
    private String axleRation;
    private String model;
    private String productSerialNumber;
    private String businessUnits;
    private String orderDate;
    private String orderingVocation;
    private String registeredVocation;
    private String inServiceDate;
    private String orderDealerCode;
    private String buildDate;
    private String productYear;
    private String unitOfMeasure;
    private String unitNumber;
    private String unitUsage;
    private List<MajorComponent> majorComponent;
}
