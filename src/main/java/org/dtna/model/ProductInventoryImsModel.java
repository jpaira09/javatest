package org.dtna.model;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Table(name = "product_inventory_ims")
@Entity
public class ProductInventoryImsModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private  int id;
    private  String productSerialNumber;
    private String make;
    private String ownerCode;
    private String vocationCode;
    private String deliveryDealerCode;
    private String deliveryComments;
    private String policyCode;
    private String unitOfMeasure;
    private String usageValue;
    private String saleType;
    private String orderNumber;
    private String fleetUnit;
    private String regBluePrint;
    private String autoRegister;
    private String firstUsedDate;
    private String registeredDate;


}
