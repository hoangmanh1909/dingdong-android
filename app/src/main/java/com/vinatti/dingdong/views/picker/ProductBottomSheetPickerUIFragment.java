package com.vinatti.dingdong.views.picker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ProgressBar;


import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.Item;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 06/12/2017.
 */

@SuppressLint("ValidFragment")
public class ProductBottomSheetPickerUIFragment extends BottomSheetDialogFragment {

    private  int mPosition = -1;
    @BindView(R.id.picker_ui)
    PickerUI pickerUI;

    @BindView(R.id.process_bar)
    ProgressBar process_bar;


    private ArrayList<Item> provinceModels;
    private PickerUiListener pickerUiListener;
    private String title;
    public boolean isShow;

    CoordinatorLayout.Behavior behavior;
    @SuppressLint("ValidFragment")
    public ProductBottomSheetPickerUIFragment(ArrayList<Item> products, String title,
                                              PickerUiListener pickerUiListener, int position) {
        this.provinceModels = products;
        this.title = title;
        this.pickerUiListener = pickerUiListener;
        this.mPosition = position;
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                ((BottomSheetBehavior)behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_piker_ui, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this,contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        setPickerUi();

    }


    private void setPickerUi() {
        if (getActivity() == null || pickerUI == null) {
            return;
        }
        pickerUI.setTitle(title);
        ArrayList<String> options = new ArrayList<>();
        for (Item product : provinceModels){
            options.add(product.getText());
        }
        if(!options.isEmpty()) {
            process_bar.setVisibility(View.GONE);
            pickerUI.mHiddenPanelPickerUI.setVisibility(View.VISIBLE);
            pickerUI.mChosseTv.setEnabled(true);
            PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                    .withItems(options)
                    .withBackgroundColor(android.R.color.white)
                    .withAutoDismiss(true)
                    .withItemsClickables(false)
                    .withUseBlur(false)
                    .build();
            pickerUI.setSettings(pickerUISettings);
            if (mPosition > -1) {
                pickerUI.slide(mPosition);
            }
            else
            {
                pickerUI.slide(0);
            }


        }
        else
        {
            process_bar.setVisibility(View.VISIBLE);
            pickerUI.mHiddenPanelPickerUI.setVisibility(View.GONE);
            pickerUI.mChosseTv.setEnabled(false);
        }
        pickerUI.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
            }

            @Override
            public void onCloseClick(View view) {
                dismiss();
            }

            @Override
            public void onChooseClick(int which, int position) {
                dismiss();
                pickerUiListener.onChooseClick(provinceModels.get(position), position);
            }
        });
    }
    public void setData(ArrayList<Item> products, int position)
    {
        this.provinceModels = products;
        this.mPosition = position;
        setPickerUi();
    }


    public interface PickerUiListener {
        void onChooseClick(Item provinceModel, int position);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isShow) return;

        super.show(manager, tag);
        isShow = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        isShow = false;
        super.onDismiss(dialog);
    }
}
