package com.hdu.iee.lostfound.dto;

import com.hdu.iee.lostfound.entity.ClaimStatus;
import jakarta.validation.constraints.NotNull;

public class ClaimStatusUpdateRequest {

    @NotNull(message = "状态不能为空")
    private ClaimStatus status;

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }
}

