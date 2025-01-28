package org.dtna.dto.elitesupport.subdtos;

import lombok.Data;
import org.dtna.dto.elitesupport.NextGenPayloadEliteSupport;

import java.util.List;

@Data
public class EliteSupportPayload {
   private List<NextGenPayloadEliteSupport> payload;
}
