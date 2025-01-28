package org.dtna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dtna.authoriser.service.AuthService;
import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.model.ProductInventoryImsModel;
import org.dtna.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InventoryService inventoryService;
    @InjectMocks
    private InventoryController inventoryController;


    @Mock
    private AuthService authService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }
    @Test
    void testCreateInventory() throws Exception {
        // Mock the service response
        when(inventoryService.createInventory(any(InputStream.class)))
                .thenReturn(List.of(new Inventory())); // Replace with actual Inventory object

        // Mock file upload
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-file.xml",
                "text/xml",
                "<xml></xml>".getBytes()
        );


        // Perform the request and verify the response
        mockMvc.perform(multipart("/dtna/middleware/inventoryCreation").file(file))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.length()").value(1));
    }

    @Test
    void testSendDataToImsSuccess() throws Exception {
        // Mock the service responses
        when(authService.validateToken(anyString()));
        Mockito.doNothing().when(inventoryService).sendProductInventoryToIms(anyList());

        // Mock request data
        Map<String, String> headers = Map.of("Authorization", "Bearer valid-token");
        List<ProductInventoryImsModel> requestBody = List.of(new ProductInventoryImsModel()); // Replace with actual object

        // Perform the request and verify the response
        mockMvc.perform(post("/dtna/middleware/sendDataIms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Successfully sumbited"));
    }

    @Test
    void testSendDataToImsUnauthorized() throws Exception {
        // Mock the service response
        when(authService.validateToken(anyString()));

        // Mock request data
        Map<String, String> headers = Map.of("Authorization", "Bearer invalid-token");
        List<ProductInventoryImsModel> requestBody = List.of(new ProductInventoryImsModel()); // Replace with actual object

        // Perform the request and verify the response
        mockMvc.perform(post("/dtna/middleware/sendDataIms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalid-token")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failed"))
                .andExpect(jsonPath("$.message").value("Invalid Token"));
    }

    @Test
    void testSendDataToImsHeaderMissing() throws Exception {
        // Mock the service response
        when(authService.validateToken(anyString()));

        // Mock request data
        List<ProductInventoryImsModel> requestBody = List.of(new ProductInventoryImsModel()); // Replace with actual object

        // Perform the request and verify the response
        mockMvc.perform(post("/dtna/middleware/sendDataIms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failed"))
                .andExpect(jsonPath("$.message").value("Bad Request"));
    }
}