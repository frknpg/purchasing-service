package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.emlakjet.purchasing.entity.InvoiceStatus;
import com.emlakjet.purchasing.entity.Invoice;
import com.emlakjet.purchasing.service.InvoiceService;
import com.emlakjet.purchasing.shared.GenericResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Bearer Authentication")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/v1/invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response."),
            @ApiResponse(responseCode = "400", description = "Limit overpassed."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<GenericResponse<Invoice>> addPurchase(@RequestBody @Valid InvoiceRequestDTO invoiceRequestDTO) {
        var purchase = invoiceService.addPurchase(invoiceRequestDTO);
        var status = purchase.getStatus();
        var statusCode = status == InvoiceStatus.APPROVED ? 200 : 400;
        return ResponseEntity.status(statusCode).body(GenericResponse.<Invoice>builder()
                .code(statusCode)
                .data(purchase)
                .message(status == InvoiceStatus.APPROVED ? "Invoice approved." : "Invoice declined.")
                .success(status == InvoiceStatus.APPROVED)
                .build());
    }

    @GetMapping("/v1/invoices")
    public Page<Invoice> getPurchases(Pageable pageable) {
        return invoiceService.getPurchases(pageable);
    }
}
