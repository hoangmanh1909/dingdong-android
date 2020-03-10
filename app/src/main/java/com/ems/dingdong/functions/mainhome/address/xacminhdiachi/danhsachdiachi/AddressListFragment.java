package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.form.FormItemEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressListFragment extends ViewFragment<AddressListContract.Presenter>
        implements AddressListContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_search_address)
    FormItemEditText edtSearchAddress;
    @BindView(R.id.img_search)
    ImageView search;

    private AddressListAdapter addressListAdapter;
    private boolean isBack = false;

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (isBack) {
            isBack = false;
            initData();
        }
    }

    ArrayList<AddressListModel> mListObject = new ArrayList<>();

    public static AddressListFragment getInstance() {
        return new AddressListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adddress_list;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        edtSearchAddress.setSelected(true);
        if (mPresenter.getType() == Constants.TYPE_ROUTE) {
            search.setVisibility(View.VISIBLE);
            edtSearchAddress.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.GONE);
            edtSearchAddress.setVisibility(View.GONE);
        }
        addressListAdapter = new AddressListAdapter(getContext(), mListObject) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.showAddressDetail(mListObject.get(position));
                        isBack = true;
                    }
                });
            }
        };

        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(addressListAdapter);

        initData();
    }

    private void initData() {
        try {
            mListObject.clear();
            Object object = mPresenter.getObject();
            handleObjectList(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_search:
                mPresenter.vietmapSearch(edtSearchAddress.getText());
                break;
        }
    }

    @Override
    public void showAddressList(Object object) {
        mListObject.clear();
        try {
            handleObjectList(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String message) {

    }

    private void handleObjectList(Object object) throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray features = data.getJSONArray("features");

        if (features.length() > 0) {
            for (int i = 0; i < features.length(); i++) {
                JSONObject item = features.getJSONObject(i);
                JSONObject geometry = item.getJSONObject("geometry");
                JSONObject properties = item.getJSONObject("properties");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                double longitude = coordinates.getDouble(0);
                double latitude = coordinates.getDouble(1);

                AddressListModel addressListModel = new AddressListModel();
                addressListModel.setName(properties.optString("name"));
                addressListModel.setConfidence(Float.parseFloat(properties.optString("confidence")));
                addressListModel.setCountry(properties.optString("country"));
                addressListModel.setCounty(properties.optString("county"));
                addressListModel.setId(properties.optString("id"));
                addressListModel.setLabel(properties.optString("label"));
                addressListModel.setLayer(properties.optString("layer"));
                addressListModel.setLocality(properties.optString("locality"));
                addressListModel.setRegion(properties.optString("region"));
                addressListModel.setStreet(properties.optString("street"));
                addressListModel.setLongitude(longitude);
                addressListModel.setLatitude(latitude);
                mListObject.add(addressListModel);
            }

            addressListAdapter.notifyDataSetChanged();
        }
    }
}
