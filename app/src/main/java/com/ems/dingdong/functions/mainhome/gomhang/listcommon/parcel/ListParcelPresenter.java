package com.ems.dingdong.functions.mainhome.gomhang.listcommon.parcel;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.ParcelCodeInfo;

import java.util.List;

/**
 * The ListParcel Presenter
 */
public class ListParcelPresenter extends Presenter<ListParcelContract.View, ListParcelContract.Interactor>
        implements ListParcelContract.Presenter {



    private List<ParcelCodeInfo> mList;

    public ListParcelPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ListParcelContract.View onCreateView() {
        return ListParcelFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListParcelContract.Interactor onCreateInteractor() {
        return new ListParcelInteractor(this);
    }

    @Override
    public List<ParcelCodeInfo> getListData() {
        return mList;
    }
    public ListParcelPresenter setList(List<ParcelCodeInfo>list) {
        this.mList = list;
        return this;
    }
}
