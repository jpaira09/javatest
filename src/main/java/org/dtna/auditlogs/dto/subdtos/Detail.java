package org.dtna.auditlogs.dto.subdtos;

import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
public class Detail {
    private  String salesforceId;
    private  String status;
    private  String error;
}
