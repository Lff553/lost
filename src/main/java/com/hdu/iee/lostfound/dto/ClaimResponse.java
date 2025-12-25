package com.hdu.iee.lostfound.dto;

import com.hdu.iee.lostfound.entity.ClaimStatus;

public class ClaimResponse {

    private Long id;
    private String claimerName;
    private String claimerContact;
    private String reason;
    private ClaimStatus status;
    private Long lostItemId;
    private String lostItemTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimerName() {
        return claimerName;
    }

    public void setClaimerName(String claimerName) {
        this.claimerName = claimerName;
    }

    public String getClaimerContact() {
        return claimerContact;
    }

    public void setClaimerContact(String claimerContact) {
        this.claimerContact = claimerContact;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public Long getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(Long lostItemId) {
        this.lostItemId = lostItemId;
    }

    public String getLostItemTitle() {
        return lostItemTitle;
    }

    public void setLostItemTitle(String lostItemTitle) {
        this.lostItemTitle = lostItemTitle;
    }
}


