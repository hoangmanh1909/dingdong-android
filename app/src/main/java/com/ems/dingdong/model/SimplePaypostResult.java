package com.ems.dingdong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SimplePaypostResult implements Parcelable {
    @SerializedName("code")
    String code = "ERROR";

    @SerializedName("status")
    int status = -1;

    @SerializedName("message")
    String message = "";

    protected SimplePaypostResult(Parcel in) {
        code = in.readString();
        message = in.readString();
        status = in.readInt();
    }

    public static final Creator<SimplePaypostResult> CREATOR = new Creator<SimplePaypostResult>() {
        @Override
        public SimplePaypostResult createFromParcel(Parcel in) {
            return new SimplePaypostResult(in);
        }

        @Override
        public SimplePaypostResult[] newArray(int size) {
            return new SimplePaypostResult[size];
        }
    };

    @Override
    public String toString() {
        return "SimpleResult{" +
                "errorCode=" + code +
                ", message='" + message + '\'' +
                ", status='" + String.valueOf(status) + '\'' +
                '}';
    }

    public String getErrorCode() {
        return code;
    }

    public void setErrorCode(String errorCode) {
        this.code = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplePaypostResult that = (SimplePaypostResult) o;

        if (!code.equals(that.code)) return false;
        if (status != that.status) return false;
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeInt(this.status);
    }

    public SimplePaypostResult() {
    }
}
