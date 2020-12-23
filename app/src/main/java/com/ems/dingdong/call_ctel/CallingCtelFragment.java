package com.ems.dingdong.call_ctel;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ems.dingdong.R;

import com.sip.cmc.SipCmc;
import com.sip.cmc.callback.PhoneCallback;


public class CallingCtelFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calling_ctel, container, false);
    }
}