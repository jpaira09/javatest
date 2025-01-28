package org.dtna.auditlogs.dto;

import lombok.Data;
import org.dtna.auditlogs.dto.subdtos.Results;

import java.util.List;

@Data
public class CustomResponseDto {
    private String hasErrors;
    private List<Results> results;
}
