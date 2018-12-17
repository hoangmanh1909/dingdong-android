package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SolutionResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<SolutionInfo> solutionInfos;

    public ArrayList<SolutionInfo> getSolutionInfos() {
        return solutionInfos;
    }
}
