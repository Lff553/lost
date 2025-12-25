package com.hdu.iee.lostfound.dto;

import com.hdu.iee.lostfound.entity.LostItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class LostItemRequest {

    @NotBlank(message = "物品名称不能为空")
    private String title;

    @NotBlank(message = "请填写物品描述")
    private String description;

    @NotBlank(message = "请填写拾取地点")
    private String location;

    private LocalDateTime createdAt;

    @NotBlank(message = "联系人姓名不能为空")
    private String finderName;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^\\+?[0-9\\-]{6,20}$", message = "联系电话格式不正确")
    private String finderContact;

    private String imageUrl;

    private LostItemStatus status;

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
}

