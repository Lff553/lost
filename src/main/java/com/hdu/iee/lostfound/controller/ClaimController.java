package com.hdu.iee.lostfound.controller;

import com.hdu.iee.lostfound.dto.ClaimResponse;
import com.hdu.iee.lostfound.dto.ClaimStatusUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hdu.iee.lostfound.service.ClaimService;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public List<ClaimResponse> listClaims() {
        return claimService.listAll();
    }

    @PutMapping("/{id}/status")
    public ClaimResponse updateStatus(@PathVariable Long id, @Valid @RequestBody ClaimStatusUpdateRequest request) {
        return claimService.updateStatus(id, request);
    }
}

