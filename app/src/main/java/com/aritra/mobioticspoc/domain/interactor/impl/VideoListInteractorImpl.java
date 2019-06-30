package com.aritra.mobioticspoc.domain.interactor.impl;

import android.content.Context;

import com.aritra.mobioticspoc.MobioticsApplication;
import com.aritra.mobioticspoc.R;
import com.aritra.mobioticspoc.domain.executor.Executor;
import com.aritra.mobioticspoc.domain.executor.MainThread;
import com.aritra.mobioticspoc.domain.interactor.VideoListInteractor;
import com.aritra.mobioticspoc.domain.interactor.base.AbstractInteractor;
import com.aritra.mobioticspoc.domain.model.CurrencyDTODao;
import com.aritra.mobioticspoc.domain.model.DaoSession;
import com.aritra.mobioticspoc.domain.model.PiResponse;
import com.aritra.mobioticspoc.network.MasterAPIClient;
import com.aritra.mobioticspoc.network.MasterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListInteractorImpl extends AbstractInteractor implements VideoListInteractor {

    private VideoListInteractor.CallBack mCallBack;
    private Context mContext;
    private Boolean networkStatus;

    private DaoSession daoSession;
    private CurrencyDTODao currencyDTODao;


    public VideoListInteractorImpl(Executor threadExecutor, MainThread mainThread, VideoListInteractor.CallBack callBack, Context context) {
        super(threadExecutor, mainThread);
        mCallBack = callBack;
        mContext = context;
        daoSession = ((MobioticsApplication) mContext.getApplicationContext()).getDaoSession();
        currencyDTODao = daoSession.getCurrencyDTODao();
    }

    @Override
    public void getAllCurrencies(Boolean isNetworkAvailable) {
        networkStatus = isNetworkAvailable;
        this.execute();
    }

    @Override
    public void run() {

        if (!networkStatus) {
            loadDataFromDiskCache();

        }
        else {

            MasterInterface masterInterface = MasterAPIClient.createService(MasterInterface.class, mContext);
            Call<PiResponse> getCurrencies = masterInterface.getCurrencies();
            getCurrencies.enqueue(new Callback<PiResponse>() {
                @Override
                public void onResponse(Call<PiResponse> call, Response<PiResponse> response) {
                    if (response.isSuccessful()) {
                        final PiResponse piResponse = response.body();
                        if (null != piResponse || piResponse.getSuccess() == true) {
                            //save the file in db
                            currencyDTODao.insertOrReplaceInTx(piResponse.getResult());
                            mCallBack.onCurrenciesLoaded(response.body());

                        }
                    } else {
                        loadDataFromDiskCache();
                    }
                }

                @Override
                public void onFailure(Call<PiResponse> call, Throwable t) {
                    loadDataFromDiskCache();

                }
            });
        }

    }

    private void loadDataFromDiskCache(){
        if(null!=currencyDTODao.loadAll() && !currencyDTODao.loadAll().isEmpty()){
            PiResponse resp = new PiResponse(true,mContext.getString(R.string.loaded_from_disk_cache),currencyDTODao.loadAll());
            mCallBack.onCurrenciesLoaded(resp);
        }
        else{
            mCallBack.onCurrenciesLoaded(null);
        }
    }

    void sendResponse(final PiResponse piResponse) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.onCurrenciesLoaded(piResponse);
            }
        });
    }
}
