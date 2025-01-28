package org.dtna.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dtna.service.TechnicianService;
import org.dtna.utils.TruckAPIHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/dtna/middleware")
public class TechnicianCertificationController {
    @Autowired
    TechnicianService technicianService;


//    @PostMapping(value = "/techniciansCertifications")
//    public String convertTechnician(@RequestParam("file") MultipartFile file,@RequestParam("filetwo") MultipartFile fileTwo) throws IOException {
//        return technicianService.sendDataInBatches(file.getInputStream(),fileTwo.getInputStream());
//    }

    @PostMapping(value = "/getTechnicianData")
    public String getData() throws FileNotFoundException, JsonProcessingException {
        return  technicianService.sendDataInBatches();
    }
}
