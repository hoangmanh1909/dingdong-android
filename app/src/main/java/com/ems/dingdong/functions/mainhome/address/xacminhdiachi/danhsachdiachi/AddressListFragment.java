package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.XacMinhDiaChiFragment;
import com.ems.dingdong.model.AddressListModel;
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

    private AddressListAdapter addressListAdapter;

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

        addressListAdapter = new AddressListAdapter(getContext(), mListObject){
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.showAddressDetail(mListObject.get(position));
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
            Object object = mPresenter.getObject();
            Gson gson = new Gson();
            String json = gson.toJson(object);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");

            JSONArray features = data.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject item = features.getJSONObject(i);
                JSONObject geometry = item.getJSONObject("geometry");
                JSONObject properties = item.getJSONObject("properties");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                double longitude = coordinates.getDouble(0);
                double latitude =coordinates.getDouble(1);

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }
}
