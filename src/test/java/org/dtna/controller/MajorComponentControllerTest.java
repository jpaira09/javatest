package org.dtna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dtna.authoriser.service.AuthService;
import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.dtna.service.MajorComponentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


class MajorComponentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MajorComponentController majorComponentController;

    @Mock
    private MajorComponentService majorComponentService;


    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(majorComponentController).build();
    }

    @Test
    void testSyncInventoryPositiveCase() throws Exception {
        List<InventoryMC> inventoryMCList = new ArrayList<>();
        InventoryMC inventoryMC = new InventoryMC();
        inventoryMC.setName("Test Component");
        inventoryMCList.add(inventoryMC);

        when(majorComponentService.processProductInventory(any())).thenReturn(inventoryMCList);

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", "<xml></xml>".getBytes());

        mockMvc.perform(multipart("/dtna/middleware/inventory")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Component")));
    }

    @Test
    void testSyncInventoryNegativeCase() throws Exception {
        when(majorComponentService.processProductInventory(any())).thenThrow(new IOException("Failed to process inventory"));

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", "<xml></xml>".getBytes());

        mockMvc.perform(multipart("/dtna/middleware/inventory")
                        .file(file))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void testSyncInventoryEmptyCase() throws Exception {
        List<InventoryMC> inventoryMCList = new ArrayList<>();

        when(majorComponentService.processProductInventory(any())).thenReturn(inventoryMCList);

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", "".getBytes());

        mockMvc.perform(multipart("/dtna/middleware/inventory")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void sendDataToImsPositiveCase() throws Exception {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();
        MajorComponentInventoryIMSModel model = new MajorComponentInventoryIMSModel();
        // Set the required fields for model
        models.add(model);

        when(authService.validateToken(anyString()));

        mockMvc.perform(post("/dtna/middleware/majorComponentDataToIMS")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(models)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")))
                .andExpect(jsonPath("$.message", is("Successfully submitted")));

        // Verify interaction with the service
        verify(majorComponentService, times(1)).sendMajorComponentInventoryDataToIMS(models);
    }

    @Test
    void sendDataToImsInvalidTokenCase() throws Exception {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();
        MajorComponentInventoryIMSModel model = new MajorComponentInventoryIMSModel();
        models.add(model);

        when(authService.validateToken(anyString()));

        mockMvc.perform(post("/dtna/middleware/majorComponentDataToIMS")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(models)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Invalid Token")));

        // Verify no interaction with the service
        verify(majorComponentService, times(0)).sendMajorComponentInventoryDataToIMS(any());
    }

    @Test
    void sendDataToImsHeaderMissingCase() throws Exception {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();
        MajorComponentInventoryIMSModel model = new MajorComponentInventoryIMSModel();
        models.add(model);

        when(authService.validateToken(anyString())).thenReturn(null);

        mockMvc.perform(post("/dtna/middleware/majorComponentDataToIMS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(models)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Invalid Token")));

        // Verify no interaction with the service
        verify(majorComponentService, times(0)).sendMajorComponentInventoryDataToIMS(any());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
