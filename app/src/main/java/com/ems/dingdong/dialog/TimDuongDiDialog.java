package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ReasonCallback;
import com.ems.dingdong.callback.VposcodeCallback;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AdapterDialog;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.model.AddressListModel;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomEditText;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

public class TimDuongDiDialog extends Dialog {
    private final VpostcodeModel vpostcodeModel;
    private final VposcodeCallback mDelegate;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.edt_picker_search)
    EditText edtSearch;
    List<Item> mList;
    AdapterDialog adapterDialog;
    private LocationManager mLocationManager;
    private Location mLocation;
    AddressListPresenter presenter;
    List<AddressListModel> listModels;
    int mType;

    public TimDuongDiDialog(Context context, VpostcodeModel vpostcodeModel, int type, AddressListPresenter presenter, VposcodeCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.vpostcodeModel = vpostcodeModel;
        this.mDelegate = reasonCallback;
        this.presenter = presenter;
        mType = type;
        View view = View.inflate(getContext(), R.layout.dialog_timduongdi, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        listModels = new ArrayList<>();
        edtSearch.setText(vpostcodeModel.getFullAdress());
        adapterDialog = new AdapterDialog(getContext(), listModels) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mType == 99)
                            vpostcodeModel.setReceiverVpostcode(listModels.get(position).getSmartCode());
                        else
                            vpostcodeModel.setSenderVpostcode(listModels.get(position).getSmartCode());
                        vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                        mDelegate.onVposcodeResponse(vpostcodeModel);
                        dismiss();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(adapterDialog);
        mLocation = getLastKnownLocation();
        listModels = new ArrayList<>();
        if (!edtSearch.getText().toString().isEmpty())
            NetWorkController.vietmapSearch(edtSearch.getText().toString(), mLocation.getLongitude(), mLocation.getLatitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            try {
                                listModels.addAll(handleObjectList(simpleResult.getResponseLocation()));
                                adapterDialog.notifyDataSetChanged();
                                adapterDialog = new AdapterDialog(getContext(), listModels) {
                                    @Override
                                    public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                                        super.onBindViewHolder(holder, position);
                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (mType == 99)
                                                    vpostcodeModel.setReceiverVpostcode(listModels.get(position).getSmartCode());
                                                else
                                                    vpostcodeModel.setSenderVpostcode(listModels.get(position).getSmartCode());
                                                vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                                                mDelegate.onVposcodeResponse(vpostcodeModel);
                                                dismiss();
                                            }
                                        });
                                    }
                                };
                                recyclerView.setAdapter(adapterDialog);
                                if (listModels.isEmpty())
                                    Toast.showToast(getContext(), "Không tìm thấy dữ liệu");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else Toast.showToast(getContext(), simpleResult.getMessage());
                    });
    }


    public TimDuongDiDialog(Context context, VpostcodeModel vpostcodeModel, int type, VposcodeCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.vpostcodeModel = vpostcodeModel;
        this.mDelegate = reasonCallback;
        this.presenter = presenter;
        View view = View.inflate(getContext(), R.layout.dialog_timduongdi, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();
        listModels = new ArrayList<>();
        mType = type;
        adapterDialog = new AdapterDialog(getContext(), listModels) {
            @Override
            public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpostcodeModel.setSmartCode(listModels.get(position).getSmartCode());
                        vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                        mDelegate.onVposcodeResponse(vpostcodeModel);
                        dismiss();
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(adapterDialog);


        mLocation = getLastKnownLocation();
        listModels = new ArrayList<>();
        if (!edtSearch.getText().toString().isEmpty())
            NetWorkController.vietmapSearch(edtSearch.getText().toString(), mLocation.getLongitude(), mLocation.getLatitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            try {
                                listModels.addAll(handleObjectList(simpleResult.getResponseLocation()));
                                adapterDialog.notifyDataSetChanged();
                                adapterDialog = new AdapterDialog(getContext(), listModels) {
                                    @Override
                                    public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                                        super.onBindViewHolder(holder, position);
                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (mType == 99)
                                                    vpostcodeModel.setReceiverVpostcode(listModels.get(position).getSmartCode());
                                                else
                                                    vpostcodeModel.setSenderVpostcode(listModels.get(position).getSmartCode());
                                                vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                                                mDelegate.onVposcodeResponse(vpostcodeModel);
                                                dismiss();
                                            }
                                        });
                                    }
                                };
                                recyclerView.setAdapter(adapterDialog);
                                if (listModels.isEmpty())
                                    Toast.showToast(getContext(), "Không tìm thấy dữ liệu");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else Toast.showToast(getContext(), simpleResult.getMessage());
                    });
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.ll_scan_qr, R.id.img_back, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                edtSearch.setText("");
                listModels = new ArrayList<>();
                adapterDialog = new AdapterDialog(getContext(), listModels) {
                    @Override
                    public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vpostcodeModel.setSmartCode(listModels.get(position).getSmartCode());
                                vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                                mDelegate.onVposcodeResponse(vpostcodeModel);
                                dismiss();
                            }
                        });
                    }
                };
                RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
                recyclerView.setAdapter(adapterDialog);
                break;
            case R.id.img_back:
                dismiss();
                break;
            case R.id.ll_scan_qr:
                mLocation = getLastKnownLocation();
                listModels = new ArrayList<>();
                NetWorkController.vietmapSearch(edtSearch.getText().toString(), mLocation.getLongitude(), mLocation.getLatitude())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simpleResult -> {
                            if (simpleResult.getErrorCode().equals("00")) {
                                try {
                                    listModels.addAll(handleObjectList(simpleResult.getResponseLocation()));
                                    adapterDialog.notifyDataSetChanged();
                                    adapterDialog = new AdapterDialog(getContext(), listModels) {
                                        @Override
                                        public void onBindViewHolder(@NonNull @NotNull AdapterDialog.HolderView holder, int position) {
                                            super.onBindViewHolder(holder, position);
                                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (mType == 99)
                                                        vpostcodeModel.setReceiverVpostcode(listModels.get(position).getSmartCode());
                                                    else
                                                        vpostcodeModel.setSenderVpostcode(listModels.get(position).getSmartCode());
                                                    vpostcodeModel.setFullAdress(listModels.get(position).getLabel());
                                                    mDelegate.onVposcodeResponse(vpostcodeModel);
                                                    dismiss();
                                                }
                                            });
                                        }
                                    };
                                    if (listModels.isEmpty())
                                        Toast.showToast(getContext(), "Không tìm thấy dữ liệu");
                                    recyclerView.setAdapter(adapterDialog);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else Toast.showToast(getContext(), simpleResult.getMessage());
                        });
                ;
//                listModels.addAll(presenter.getListSearch());
//                if (listModels.size() <= 0)
//                    listModels.addAll(presenter.getListSearch());
                break;
        }
    }

    private List<AddressListModel> handleObjectList(Object object) throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        List<AddressListModel> listObject = new ArrayList<>();
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
                addressListModel.setSmartCode(properties.optString("smartcode"));
                addressListModel.setLongitude(longitude);
                addressListModel.setLatitude(latitude);
                listObject.add(addressListModel);
            }
        }
        return listObject;
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
