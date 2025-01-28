package org.dtna.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

import org.dtna.authoriser.service.AuthService;

import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.model.ProductInventoryImsModel;
import org.dtna.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dtna/middleware")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    AuthService authService;

    @PostMapping("/inventoryCreation")
    public List<Inventory> createInventory(@RequestParam("file") MultipartFile file) throws ParserConfigurationException, IOException, SAXException {
        return inventoryService.createInventory(file.getInputStream());
    }
    @PostMapping("/sendDataIms")
    public HashMap<String,String> sendDataToIms(@RequestBody List<ProductInventoryImsModel> productInventoryImsModel, @RequestHeader Map<String, String> headers, HttpServletRequest request)
    {
        HashMap<String, String> responseMap = new HashMap<>();

        try {
            // Retrieve the Authorization header
            String token = request.getHeader("Authorization");
            if (token == null || token.trim().isEmpty()) {
                responseMap.put("status", "Failed");
                responseMap.put("message", "Header Missing");
                return responseMap;
            }

            // Remove "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7).trim();
            }

            // Validate the token
            Claims claims = authService.validateToken(token);

            // If validation is successful, process inventory
            inventoryService.sendProductInventoryToIms(productInventoryImsModel);
            responseMap.put("status", "Success");
            responseMap.put("message", "Successfully submitted");
        } catch (ExpiredJwtException e) {
            responseMap.put("status", "Failed");
            responseMap.put("message", "Token has expired");
        } catch (RuntimeException e) {
            responseMap.put("status", "Failed");
            responseMap.put("message", e.getMessage());
        } catch (Exception e) {
            responseMap.put("status", "Failed");
            responseMap.put("message", "Unauthorized");
        }

        return responseMap;
    }

}
