package org.dtna.dto.product_inventory.subdtos;

import lombok.Data;

import java.security.SecureRandom;

@Data
public class HVBattery {
    private String regVocation;
    private String  regBluePrint;
    private  String series;
    private String  salesOrderNo;
    private String salesOrderLine;
    private  String salesOrderPlant;
    private String axleFinPartNo;
    private String remanInd;
    private String subProductType;
    private String ddcCAN;
    private String ddcCustomerName;
    private String shipDate;

}
