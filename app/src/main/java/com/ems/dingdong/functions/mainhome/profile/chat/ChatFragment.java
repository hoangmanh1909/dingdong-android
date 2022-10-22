package com.ems.dingdong.functions.mainhome.profile.chat;


import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ringme.ott.sdk.customer.vnpost.model.VnpostOrderInfoDB;
import com.ringme.ott.sdk.model.RingmeConversation;
import com.ringme.ott.sdk.utils.RingmeOttSdk;
import com.zoho.livechat.android.ZohoLiveChat;

import java.util.Objects;

import butterknife.OnClick;

public class ChatFragment extends ViewFragment<ChatContract.Presenter> implements ChatContract.View {

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

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
//                RingmeOttSdk.openChatList(requireActivity());
//                RingmeOttSdk.openChat(
//                        getActivity(),
//                    "0969803622",
//                       null,
//                        5
//                );

                RingmeOttSdk.openChat(
                        getActivity(),
                        "0969803622",
                        1
                );

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}
