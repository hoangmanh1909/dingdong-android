package com.vinatti.dingdong.callback;

import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;

public interface BaoPhatOfflineFailCallback {
    void onResponse(ReasonInfo reason, SolutionInfo solution, String note, String sign);
}
