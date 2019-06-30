package com.aritra.mobioticspoc.presentation.presenters;

import com.aritra.mobioticspoc.domain.model.PiResponse;
import com.aritra.mobioticspoc.presentation.presenters.base.BasePresenter;

public interface VideoListPresenter extends BasePresenter {
    void getAllCurrencies(Boolean isNetworkAvailable);

    interface View {
        void onCurrenciesLoaded(PiResponse piResponse);
    }
}
