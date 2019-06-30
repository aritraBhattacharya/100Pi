package com.aritra.mobioticspoc.domain.model;


import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

public class PiResponse {
    @Expose
    private Boolean success;
    @Expose
    private String message;
    @Expose
    private List<CurrencyDTO> result;

    public PiResponse(Boolean success, String message, List<CurrencyDTO> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<CurrencyDTO> getResult() {
        return result;
    }
}
