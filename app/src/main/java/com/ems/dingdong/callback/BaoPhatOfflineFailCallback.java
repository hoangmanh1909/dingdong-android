package com.ems.dingdong.callback;

import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.SolutionInfo;

public interface BaoPhatOfflineFailCallback {
    void onResponse(ReasonInfo reason, SolutionInfo solution, String note, String sign);
}
