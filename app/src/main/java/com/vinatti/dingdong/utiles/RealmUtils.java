package com.vinatti.dingdong.utiles;

import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUtils {
    public static List<SolutionInfo> getSolutionByReason(String code) {
        List<SolutionInfo> list = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        ReasonInfo result = realm.where(ReasonInfo.class).equalTo(Constants.REASONINFO_CODE, code).findFirst();
        if (result != null) {
            ReasonInfo data = realm.copyFromRealm(result);
            RealmResults<SolutionInfo> solutionInfos = realm.where(SolutionInfo.class).findAll();
            for (SolutionInfo infoRealm : solutionInfos) {
                if (data.getSolution().contains(realm.copyFromRealm(infoRealm).getCode())) {
                    list.add(realm.copyFromRealm(infoRealm));
                }
            }
        }

        return list;

    }

    public static List<ReasonInfo> getReasons() {
        List<ReasonInfo> list = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ReasonInfo> result = realm.where(ReasonInfo.class).findAll();
        for (ReasonInfo data : result) {
            list.add(realm.copyFromRealm(data));
        }

        return list;

    }

    public static String getReasonByCode(String reasonCode) {
        String title = "";
        Realm realm = Realm.getDefaultInstance();
        ReasonInfo result = realm.where(ReasonInfo.class).equalTo(Constants.REASONINFO_CODE, reasonCode).findFirst();
        if (result != null) {
            ReasonInfo data = realm.copyFromRealm(result);
            title = data.getName();
        }
        return title;
    }

    public static String getSolutionByCode(String solutionCode) {
        String title = "";
        Realm realm = Realm.getDefaultInstance();
        SolutionInfo result = realm.where(SolutionInfo.class).equalTo(Constants.SOLUTIONINFO_CODE, solutionCode).findFirst();
        if (result != null) {
            SolutionInfo data = realm.copyFromRealm(result);
            title = data.getName();
        }
        return title;
    }
}
