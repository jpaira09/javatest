package org.dtna.auditlogs.dto;

import lombok.Data;
import org.dtna.auditlogs.dto.subdtos.Detail;


import java.util.List;

@Data
public class ResponseDto {
    private  String status;
    private  String interfaceName;
    private  List<Detail> detail;

}
