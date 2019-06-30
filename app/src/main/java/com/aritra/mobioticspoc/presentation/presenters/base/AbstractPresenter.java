package com.aritra.mobioticspoc.presentation.presenters.base;

import com.aritra.mobioticspoc.domain.executor.Executor;
import com.aritra.mobioticspoc.domain.executor.MainThread;

public abstract class AbstractPresenter {
    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }

}
