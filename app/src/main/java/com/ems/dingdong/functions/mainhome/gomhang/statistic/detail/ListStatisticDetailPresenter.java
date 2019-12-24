package com.ems.dingdong.functions.mainhome.gomhang.statistic.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.model.StatisticDetailCollect;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListStatisticDetailPresenter extends Presenter<ListStatisticDetailContract.View, ListStatisticDetailContract.Interactor>
        implements ListStatisticDetailContract.Presenter {

    private List<StatisticDetailCollect> mList;

    public ListStatisticDetailPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public ListStatisticDetailContract.View onCreateView() {
        return ListStatisticDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ListStatisticDetailContract.Interactor onCreateInteractor() {
        return new ListStatisticDetailInteractor(this);
    }

   public ListStatisticDetailPresenter setData(List<StatisticDetailCollect> list)
   {
       mList = list;
       return this;
   }

    @Override
    public List<StatisticDetailCollect> getList() {
        return mList;
    }
}
