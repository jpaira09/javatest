package org.dtna.dto.elitesupport;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextGenPayloadEliteSupport {
    private String locationCode;
    private String eliteSupport;
    private String startdate;
    private String enddate;
    private String isActive;
    private String name;
}
