package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.controller.dto.PurchasingRequestDTO;
import com.emlakjet.purchasing.controller.dto.PurchasingResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PurchasingController {

    @PostMapping("/v1/purchases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PurchasingResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Limit overpassed."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public PurchasingResponseDTO addPurchase(PurchasingRequestDTO purchasingRequestDTO) {
        return null;
    }
}
