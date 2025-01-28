package org.dtna.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dtna.dto.technician_interface.NextGenPayloadTechnician;
import org.dtna.dto.technician_interface.subdtos.Technicians;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
public interface TechnicianService {

   public List<NextGenPayloadTechnician>convertFormatForTechnician() ;
    public String sendDataInBatches() throws FileNotFoundException, JsonProcessingException;


}
