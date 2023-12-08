package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.dao.purchasing.PurchasingRequestDTO;
import com.emlakjet.purchasing.dao.purchasing.PurchasingResponseDTO;
import com.emlakjet.purchasing.service.PurchasingService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Bearer Authentication")
public class PurchasingController {

    private final PurchasingService purchasingService;

    public PurchasingController(PurchasingService purchasingService) {
        this.purchasingService = purchasingService;
    }

    @PostMapping("/v1/purchases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PurchasingResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Limit overpassed."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public PurchasingResponseDTO addPurchase(@RequestBody @Valid PurchasingRequestDTO purchasingRequestDTO) {
        return new PurchasingResponseDTO(purchasingService.addPurchase(purchasingRequestDTO));
    }
}
