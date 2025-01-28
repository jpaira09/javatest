package org.dtna.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dtna.authoriser.service.AuthService;
import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.dtna.service.MajorComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dtna/middleware")
public class MajorComponentController {

    @Autowired
    private MajorComponentService majorComponentService;

    @Autowired
    private AuthService authService;

    @PostMapping("/inventory")
    public ResponseEntity<List<InventoryMC>> syncInventory(@RequestParam("file") MultipartFile file) {
        try {
            List<InventoryMC> inventoryMCList = majorComponentService.processProductInventory(file.getInputStream());
            return new ResponseEntity<>(inventoryMCList, HttpStatus.OK); }
        catch (ParserConfigurationException | IOException | SAXException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/majorComponentDataToIMS")
    public HashMap<String, String> sendDataToIms(@RequestBody List<MajorComponentInventoryIMSModel> majorComponentInventoryImsModel, HttpServletRequest request) {

        HashMap<String, String> map = new HashMap<>();
        String token = request.getHeader("Authorization");
        String responseToken = String.valueOf(authService.validateToken(token));

        if (responseToken == null) {
            map.put("status", "Failed");
            map.put("message", "Invalid Token");
            return map;
        }

        switch (responseToken) {
            case "Success":
                majorComponentService.sendMajorComponentInventoryDataToIMS(majorComponentInventoryImsModel);
                map.put("status", "Success");
                map.put("message", "Successfully submitted");
                break;
            case "Unauthorized":
                map.put("status", "Failed");
                map.put("message", "Invalid Token");
                break;
            case "Header Missing":
                map.put("status", "Failed");
                map.put("message", "Bad Request");
                break;
            case "Invalid Token":
                map.put("status", "Failed");
                map.put("message", "Unauthorized");
                break;
            default:
                map.put("status", "Failed");
                map.put("message", "Unknown error");
                break;
        }
        return map;
    }

}
