package com.aritra.mobioticspoc.network;

import com.aritra.mobioticspoc.domain.model.PiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MasterInterface {

    @GET("api/v1.1/public/getcurrencies")
    Call<PiResponse> getCurrencies();
}
