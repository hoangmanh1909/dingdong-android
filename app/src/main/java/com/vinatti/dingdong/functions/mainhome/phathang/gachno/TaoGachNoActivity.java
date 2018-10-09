package com.vinatti.dingdong.functions.mainhome.phathang.gachno;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list.BaoPhatThanhCongFragment;
import com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list.BaoPhatThanhCongPresenter;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.ViewUtils;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class TaoGachNoActivity extends DingDongActivity {
    private SearchView searchView;
    private TaoGachNoFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        mFragment = (TaoGachNoFragment) new TaoGachNoPresenter(this).getFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int textSize18 = getResources().getDimensionPixelSize(R.dimen.text_size_18sp);
        int textSize14 = getResources().getDimensionPixelSize(R.dimen.text_size_14sp);
        String text1 = "Gạch nợ";
        String text2 = "";
        if (TextUtils.isEmpty(Constants.SHIFT)) {
            text2 = ("Bạn chưa chọn ca làm việc");
        } else {
            text2 = ("Ca làm việc: Ca " + Constants.SHIFT);
        }

        SpannableString span1 = new SpannableString(text1);
        span1.setSpan(new AbsoluteSizeSpan(textSize18), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString span2 = new SpannableString(text2);
        span2.setSpan(new AbsoluteSizeSpan(textSize14), 0, text2.length(), SPAN_INCLUSIVE_INCLUSIVE);

// let's put both spans together with a separator and all
        CharSequence finalText = TextUtils.concat(span1, "\n", span2);
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
        searchView.setQuery("", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }
}
