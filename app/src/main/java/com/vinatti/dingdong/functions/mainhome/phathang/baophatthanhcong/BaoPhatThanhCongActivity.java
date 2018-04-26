package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong;

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
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list.BaoPhatThanhCongFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list.BaoPhatThanhCongPresenter;
import com.vinatti.dingdong.utiles.ViewUtils;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class BaoPhatThanhCongActivity extends DingDongActivity {
    private SearchView searchView;
    private BaoPhatThanhCongFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        mFragment = (BaoPhatThanhCongFragment) new BaoPhatThanhCongPresenter(this).getFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.viewActionBar(getSupportActionBar(), getLayoutInflater(), "Báo phát thành công");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
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

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public void removeTextSearch() {
        searchView.setQuery("",false);
    }
}
