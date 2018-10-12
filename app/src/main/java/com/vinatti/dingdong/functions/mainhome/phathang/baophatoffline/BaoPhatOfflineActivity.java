package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list.BaoPhatOfflineFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list.BaoPhatOfflinePresenter;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.StringUtils;
import com.vinatti.dingdong.utiles.ViewUtils;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class BaoPhatOfflineActivity extends DingDongActivity {
    private SearchView searchView;
    private BaoPhatOfflineFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        mFragment = (BaoPhatOfflineFragment) new BaoPhatOfflinePresenter(this).getFragment();
        return mFragment;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text1 = "BÁO PHÁT OFFLINE";
        CharSequence finalText = StringUtils.getCharSequence(text1, this);
        ViewUtils.viewActionBar(getSupportActionBar(), getLayoutInflater(), finalText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_camera) {
            mFragment.scanQr();
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra(Constants.IS_ONLINE, false)) {
            return false;
        } else {
            getMenuInflater().inflate(R.menu.menu_search_bao_phat, menu);
            // Associate searchable configuration with the SearchView
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            // listening to search query text change
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // filter recycler view when query submitted
                    mFragment.getQuery(query);
                    // mAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    // filter recycler view when text is changed
                    // mAdapter.getFilter().filter(query);
                    // mFragment.getAdapter().getFilter().filter(query);
                    return false;
                }
            });
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (searchView != null) {
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
                return;
            }
        }
        super.onBackPressed();
    }

    public void removeTextSearch() {
        searchView.setQuery("", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }*/
}
