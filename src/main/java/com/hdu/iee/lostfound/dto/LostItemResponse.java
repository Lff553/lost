package com.hdu.iee.lostfound.dto;

import com.hdu.iee.lostfound.entity.LostItemStatus;

import java.time.LocalDateTime;
import java.util.List;

public class LostItemResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime createdAt;
    private String finderName;
    private String finderContact;
    private String imageUrl;
    private LostItemStatus status;
    private Long publisherId;
    private String publisherName;
    private List<ClaimResponse> claims;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFinderName() {
        return finderName;
    }

    public void setFinderName(String finderName) {
        this.finderName = finderName;
    }

    public String getFinderContact() {
        return finderContact;
    }

    public void setFinderContact(String finderContact) {
        this.finderContact = finderContact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LostItemStatus getStatus() {
        return status;
    }

    public void setStatus(LostItemStatus status) {
        this.status = status;
    }

    public List<ClaimResponse> getClaims() {
        return claims;
    }

    public void setClaims(List<ClaimResponse> claims) {
        this.claims = claims;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}

