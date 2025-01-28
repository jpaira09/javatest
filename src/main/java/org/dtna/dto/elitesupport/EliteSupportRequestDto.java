package org.dtna.dto.elitesupport;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EliteSupportRequestDto {
    private String locationCode;
    private String eliteSupport;
    private String startDate;
    private String endDate;
    private String isActive;
    private String name;
}
