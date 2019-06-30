package com.aritra.mobioticspoc.presentation.presenters.impl;

import android.content.Context;

import com.aritra.mobioticspoc.domain.executor.Executor;
import com.aritra.mobioticspoc.domain.executor.MainThread;
import com.aritra.mobioticspoc.domain.interactor.VideoListInteractor;
import com.aritra.mobioticspoc.domain.interactor.impl.VideoListInteractorImpl;
import com.aritra.mobioticspoc.domain.model.PiResponse;
import com.aritra.mobioticspoc.presentation.presenters.VideoListPresenter;
import com.aritra.mobioticspoc.presentation.presenters.base.AbstractPresenter;

public class VideoListPresenterImpl extends AbstractPresenter implements VideoListPresenter, VideoListInteractor.CallBack {

    private Context mContext;
    private VideoListPresenter.View mView;
    private VideoListInteractor videoListInteractor;

    public VideoListPresenterImpl(Executor executor, MainThread mainThread,Context context, View view) {
        super(executor, mainThread);
        mContext =context;
        mView = view;
        videoListInteractor =new VideoListInteractorImpl(executor,mainThread,this,mContext);
    }

    @Override
    public void onCurrenciesLoaded(PiResponse piResponse) {
        mView.onCurrenciesLoaded(piResponse);
    }

    @Override
    public void getAllCurrencies(Boolean isNetworkAvailable) {
        videoListInteractor.getAllCurrencies(isNetworkAvailable);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
