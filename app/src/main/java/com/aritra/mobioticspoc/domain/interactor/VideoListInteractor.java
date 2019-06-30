package com.aritra.mobioticspoc.domain.interactor;

import com.aritra.mobioticspoc.domain.interactor.base.Interactor;
import com.aritra.mobioticspoc.domain.model.PiResponse;

public interface VideoListInteractor extends Interactor {

    void getAllCurrencies(Boolean isNetworkAvailable);

    interface CallBack {
        void onCurrenciesLoaded(PiResponse piResponse);
    }
}
