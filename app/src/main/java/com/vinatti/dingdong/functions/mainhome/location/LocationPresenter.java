package com.vinatti.dingdong.functions.mainhome.location;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The Location Presenter
 */
public class LocationPresenter extends Presenter<LocationContract.View, LocationContract.Interactor>
        implements LocationContract.Presenter {

    public LocationPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LocationContract.View onCreateView() {
        return LocationFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public LocationContract.Interactor onCreateInteractor() {
        return new LocationInteractor(this);
    }
}
