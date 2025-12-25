package com.hdu.iee.lostfound.service;

import com.hdu.iee.lostfound.dto.ClaimRequest;
import com.hdu.iee.lostfound.dto.ClaimResponse;
import com.hdu.iee.lostfound.dto.ClaimStatusUpdateRequest;
import com.hdu.iee.lostfound.entity.Claim;
import com.hdu.iee.lostfound.entity.ClaimStatus;
import com.hdu.iee.lostfound.entity.LostItem;
import com.hdu.iee.lostfound.entity.LostItemStatus;
import com.hdu.iee.lostfound.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.hdu.iee.lostfound.repository.ClaimRepository;

import java.util.List;

@Service
@Transactional
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final LostItemService lostItemService;

    public ClaimService(ClaimRepository claimRepository, LostItemService lostItemService) {
        this.claimRepository = claimRepository;
        this.lostItemService = lostItemService;
    }

    public ClaimResponse submitClaim(Long itemId, ClaimRequest request) {
        LostItem item = lostItemService.findOne(itemId);
        Claim claim = new Claim();
        claim.setLostItem(item);
        claim.setClaimantName(request.getClaimerName());
        claim.setContactInfo(request.getClaimerContact());
        claim.setDescription(request.getReason());
        Claim saved = claimRepository.save(claim);
        item.getClaims().add(saved);
        return toResponse(saved);
    }

    public List<ClaimResponse> listAll() {
        return claimRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClaimResponse updateStatus(Long claimId, ClaimStatusUpdateRequest request) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new NotFoundException("未找到ID为" + claimId + "的认领记录"));
        ClaimStatus status = request.getStatus();
        claim.setStatus(status);

        // 同步更新物品状态
        if (status == ClaimStatus.APPROVED) {
            claim.getLostItem().setStatus(LostItemStatus.CLAIMED);
        } else if (status == ClaimStatus.COMPLETED) {
            claim.getLostItem().setStatus(LostItemStatus.RETURNED);
        }

        return toResponse(claim);
    }

    private ClaimResponse toResponse(Claim claim) {
        ClaimResponse response = new ClaimResponse();
        response.setId(claim.getId());
        response.setClaimerName(claim.getClaimantName());
        response.setClaimerContact(claim.getContactInfo());
        response.setReason(claim.getDescription());
        response.setStatus(claim.getStatus());
        if (claim.getLostItem() != null) {
            response.setLostItemId(claim.getLostItem().getId());
            response.setLostItemTitle(claim.getLostItem().getTitle());
        }
        return response;
    }
}

