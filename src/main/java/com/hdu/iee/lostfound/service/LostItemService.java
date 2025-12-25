package com.hdu.iee.lostfound.service;

import com.hdu.iee.lostfound.dto.ClaimResponse;
import com.hdu.iee.lostfound.dto.LostItemRequest;
import com.hdu.iee.lostfound.dto.LostItemResponse;
import com.hdu.iee.lostfound.entity.Claim;
import com.hdu.iee.lostfound.entity.LostItem;
import com.hdu.iee.lostfound.entity.LostItemStatus;
import com.hdu.iee.lostfound.entity.User;
import com.hdu.iee.lostfound.exception.NotFoundException;
import com.hdu.iee.lostfound.repository.LostItemRepository;
import com.hdu.iee.lostfound.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LostItemService {

    private final LostItemRepository lostItemRepository;
    private final AuthUtil authUtil;

    public LostItemService(LostItemRepository lostItemRepository, AuthUtil authUtil) {
        this.lostItemRepository = lostItemRepository;
        this.authUtil = authUtil;
    }

    public List<LostItemResponse> list(String keyword) {
        List<LostItem> items;
        if (!StringUtils.hasText(keyword)) {
            items = lostItemRepository.findAll();
        } else {
            items = lostItemRepository.searchByKeyword(keyword.trim());
        }
        return items.stream().map(this::toResponse).toList();
    }

    public LostItemResponse create(LostItemRequest request, String studentId) {
        User currentUser = authUtil.getCurrentUser(studentId);

        LostItem item = new LostItem();
        updateEntityFromRequest(request, item);
        item.setPublisher(currentUser);

        LostItem saved = lostItemRepository.save(item);
        return toResponse(saved);
    }

    public LostItemResponse update(Long id, LostItemRequest request, String studentId) {
        User currentUser = authUtil.getCurrentUser(studentId);

        LostItem item = lostItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到ID为" + id + "的物品"));

        // 检查权限：只有发布者可以修改
        if (!item.getPublisher().getId().equals(currentUser.getId())) {
            throw new RuntimeException("无权限修改此物品");
        }

        updateEntityFromRequest(request, item);
        return toResponse(item);
    }

    public void delete(Long id, String studentId) {
        User currentUser = authUtil.getCurrentUser(studentId);

        LostItem item = lostItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到ID为" + id + "的物品"));

        // 检查权限：只有发布者可以删除
        if (!item.getPublisher().getId().equals(currentUser.getId())) {
            throw new RuntimeException("无权限删除此物品");
        }

        lostItemRepository.delete(item);
    }

    public List<LostItemResponse> getMyItems(String studentId) {
        User currentUser = authUtil.getCurrentUser(studentId);
        List<LostItem> items = lostItemRepository.findByPublisherOrderByCreatedAtDesc(currentUser);
        return items.stream().map(this::toResponse).toList();
    }

    public LostItem findOne(Long id) {
        return lostItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到ID为" + id + "的物品"));
    }

    private void updateEntityFromRequest(LostItemRequest request, LostItem item) {
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setLocation(request.getLocation());
        if (request.getCreatedAt() != null) {
            item.setCreatedAt(request.getCreatedAt());
        } else if (item.getCreatedAt() == null) {
            item.setCreatedAt(LocalDateTime.now());
        }
        item.setFinderName(request.getFinderName());
        item.setFinderContact(request.getFinderContact());
        item.setImageUrl(request.getImageUrl());
        if (request.getStatus() != null) {
            item.setStatus(request.getStatus());
        } else if (item.getStatus() == null) {
            item.setStatus(LostItemStatus.AVAILABLE);
        }
    }

    public LostItemResponse toResponse(LostItem item) {
        LostItemResponse response = new LostItemResponse();
        response.setId(item.getId());
        response.setTitle(item.getTitle());
        response.setDescription(item.getDescription());
        response.setLocation(item.getLocation());
        response.setCreatedAt(item.getCreatedAt());
        response.setFinderName(item.getFinderName());
        response.setFinderContact(item.getFinderContact());
        response.setImageUrl(item.getImageUrl());
        response.setStatus(item.getStatus());

        // 添加发布者信息
        if (item.getPublisher() != null) {
            response.setPublisherId(item.getPublisher().getId());
            response.setPublisherName(item.getPublisher().getName());
        }

        List<ClaimResponse> claimResponses = item.getClaims().stream()
                .map(this::toClaimResponse)
                .toList();
        response.setClaims(claimResponses);
        return response;
    }

    private ClaimResponse toClaimResponse(Claim claim) {
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

