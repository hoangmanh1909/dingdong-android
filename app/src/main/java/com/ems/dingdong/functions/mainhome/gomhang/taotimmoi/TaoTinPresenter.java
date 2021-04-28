package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.StatisticDetailCollect;

import java.util.List;

/**
 * The CommonObject Presenter
 */
public class TaoTinPresenter extends Presenter<TaoTinContract.View, TaoTinContract.Interactor>
        implements TaoTinContract.Presenter {

    private List<StatisticDetailCollect> mList;

    public TaoTinPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public TaoTinContract.View onCreateView() {
        return TaoTInFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public TaoTinContract.Interactor onCreateInteractor() {
        return new TaoTInInteractor(this);
    }

   public TaoTinPresenter setData(List<StatisticDetailCollect> list)
   {
       mList = list;
       return this;
   }

}
