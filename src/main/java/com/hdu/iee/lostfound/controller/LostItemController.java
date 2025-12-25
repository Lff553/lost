package com.hdu.iee.lostfound.controller;

import com.hdu.iee.lostfound.dto.ClaimRequest;
import com.hdu.iee.lostfound.dto.ClaimResponse;
import com.hdu.iee.lostfound.dto.LostItemRequest;
import com.hdu.iee.lostfound.dto.LostItemResponse;
import com.hdu.iee.lostfound.service.ClaimService;
import com.hdu.iee.lostfound.service.LostItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class LostItemController {

    private final LostItemService lostItemService;
    private final ClaimService claimService;

    public LostItemController(LostItemService lostItemService, ClaimService claimService) {
        this.lostItemService = lostItemService;
        this.claimService = claimService;
    }

    @GetMapping
    public List<LostItemResponse> listItems(@RequestParam(required = false) String keyword) {
        return lostItemService.list(keyword);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LostItemResponse createItem(
            @Valid @RequestBody LostItemRequest request,
            @RequestHeader("X-Student-Id") String studentId) {
        return lostItemService.create(request, studentId);
    }

    @PutMapping("/{id}")
    public LostItemResponse updateItem(
            @PathVariable Long id,
            @Valid @RequestBody LostItemRequest request,
            @RequestHeader("X-Student-Id") String studentId) {
        return lostItemService.update(id, request, studentId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @PathVariable Long id,
            @RequestHeader("X-Student-Id") String studentId) {
        lostItemService.delete(id, studentId);
    }

    @GetMapping("/my")
    public List<LostItemResponse> getMyItems(@RequestHeader("X-Student-Id") String studentId) {
        return lostItemService.getMyItems(studentId);
    }

    @PostMapping("/{id}/claims")
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimResponse submitClaim(@PathVariable Long id, @Valid @RequestBody ClaimRequest request) {
        return claimService.submitClaim(id, request);
    }
}

