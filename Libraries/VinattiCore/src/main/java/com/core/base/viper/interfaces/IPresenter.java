package com.core.base.viper.interfaces;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;

/**
 * Base Presenter
 * Created by neo on 2/5/2016.
 */
public interface IPresenter<V extends IView, I extends IInteractor> {

    void start();

    void cancelAllCallback();

    void addCallback(Call callback);

    void addDispose(Disposable d);

    V getView();

    I onCreateInteractor();

    V onCreateView();

    I getInteractor();

    Fragment getFragment();

    void presentView();

    void pushView();

    void replaceView();

    void pushChildView(int frameId, FragmentManager childFragmentManager);

    void loadChildView(int frameId, FragmentManager childFragmentManager);

    Activity getViewContext();

    void registerEventBus();

    void unregisterEventBus();

    void back();

    void onFragmentDisplay();

    boolean isViewShowing();
}
