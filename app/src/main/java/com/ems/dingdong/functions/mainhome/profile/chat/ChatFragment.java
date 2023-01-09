package com.ems.dingdong.functions.mainhome.profile.chat;


import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.core.widget.PopupWindowCompat;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
//import com.ringme.ott.sdk.customer.vnpost.model.QueueInfo;
//import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfo;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
import com.ringme.ott.sdk.RingmeOttSdk;
import com.zoho.livechat.android.ZohoLiveChat;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatFragment extends ViewFragment<ChatContract.Presenter> implements ChatContract.View {

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    @BindView(R.id.rl_ringme)
    RelativeLayout rlRingme;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initLayout() {
        super.initLayout();


    }

    @OnClick({R.id.img_back, R.id.rl_chat, R.id.rl_ringme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.rl_chat:
                ZohoLiveChat.Chat.show();
                break;
            case R.id.rl_ringme:
//                QueueInfo queueInfo = new QueueInfo("110130",
//                        "Trung tâm dịch vụ hoàn kiếm - thu gom",
//                        "7deca21d4ed9de60b2d08aadf8cb0f86@queue.vnpost",
//                        "https://play-lh.googleusercontent.com/IuSjZfSx0FPIlZE1iKeCt5Tqc1CuxwS4tsYTe-kieaqMEq0tqLTLzDzozOVzhL0yiP4");
//                VnpostOrderInfo vnpostOrderInfo = new VnpostOrderInfo(
//                        "11111",
//                        "1",
//                        "https://play-lh.googleusercontent.com/IuSjZfSx0FPIlZE1iKeCt5Tqc1CuxwS4tsYTe-kieaqMEq0tqLTLzDzozOVzhL0yiP4",
//                        "21000",
//                        "2400",
//                        "1",
//                        "A"
//                );
//                RingmeOttSdk.openChatList(
//                        requireActivity()
//                );
                mPresenter.showMenuChat();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(getViewContext());
//        View layout = getLayoutInflater().inflate(R.layout.custom_marker_view, null);
//        popup.setContentView(layout);
//        // Set content width and height
//        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
//        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
//        // Show anchored to button
        popup.showAtLocation(anchorView, Gravity.RIGHT, (int) anchorView.getX(),
                (int) anchorView.getY());

//        popup.showAsDropDown(anchorView);
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setContentView(LayoutInflater.from(anchorView.getContext()).inflate(R.layout.topping_view, null));
        PopupWindowCompat.showAsDropDown(popup, anchorView, 0, 0, Gravity.RIGHT);
    }

}
