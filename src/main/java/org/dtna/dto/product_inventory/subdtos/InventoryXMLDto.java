package org.dtna.dto.product_inventory.subdtos;

import lombok.Data;

import java.util.List;


@Data
public class InventoryXMLDto {
    private String providerCode;
    private  String make;
    private  String model;
    private  String base;
    private  String productSerialNumber;
    private String productType;
    private String vocationCode;
    private String specialWarrantyCode;
    private String  buildDate;
    private List<ProductAttribute> productAttributes;
    private  String unitOfMeasure;
    private  String saleType;
    private  String policyCode;
    private  String installDate;
    private String productYear;
    private String name;
    private String axleRation;
    private String businessUnits;
    private String orderDate;
    private String orderingVocation;
    private String registeredVocation;
    private String inServiceDate;
    private String orderDealerCode;
}
