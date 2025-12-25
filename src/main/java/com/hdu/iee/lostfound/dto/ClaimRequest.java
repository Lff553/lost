package com.hdu.iee.lostfound.dto;

import jakarta.validation.constraints.NotBlank;

public class ClaimRequest {

    @NotBlank(message = "请填写认领人姓名")
    private String claimerName;

    @NotBlank(message = "请填写联系方式")
    private String claimerContact;

    @NotBlank(message = "请填写认领说明")
    private String reason;

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
}

