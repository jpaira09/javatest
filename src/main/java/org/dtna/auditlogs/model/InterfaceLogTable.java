package org.dtna.auditlogs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "interface_log_table")

public class InterfaceLogTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;
    private String salesForceTransactionId;
    private String status;
    private String  responseCode;
    private String errorMessageFromSalesforce;
    private String retryNumber;
    private String interfaceName;
    private String data;
    private Timestamp timestamp;

}