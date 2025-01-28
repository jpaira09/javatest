package org.dtna.dto.product_inventory.subdtos;

import lombok.Data;

import java.util.List;

@Data
public class Inventory {
    private String  name;
    private  String productSerialNumber;
    private  String model;
    private  String orderDealerCode;

    private  String unitOfMeasure;
    private String usageValue;
    private  String buildDate;
    private  String productYear;

    private  String inServiceDate;


    private List<MajorComponent> majorComponent;




}
