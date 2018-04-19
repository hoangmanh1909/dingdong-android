package com.vinatti.dingdong.functions.mainhome.callservice;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.location.LocationFragment;
import com.vinatti.dingdong.functions.mainhome.location.LocationPresenter;
import com.vinatti.dingdong.utiles.ViewUtils;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class CallActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new CallServicePresenter(this).getFragment();
    }

}
