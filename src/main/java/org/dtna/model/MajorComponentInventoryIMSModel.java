package org.dtna.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "major_component_inventory_ims")
@Entity
public class MajorComponentInventoryIMSModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String productSerialNumber;
    private String insert;
    private String component;
    private String ftlPartNumber;
    private String inServiceDate;
    private String manufacturer;
    private String modelNumber;
    private String seqNo;
    private String serialNumber;
    private String synthLubeInd;
    private String synthLubeindecator;
    private String typeCode;
}
