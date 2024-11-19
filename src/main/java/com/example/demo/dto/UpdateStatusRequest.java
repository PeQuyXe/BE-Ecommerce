package com.example.demo.dto;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    private Integer statusId;


    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
