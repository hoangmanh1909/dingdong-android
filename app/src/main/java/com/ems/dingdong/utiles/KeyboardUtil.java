package com.ems.dingdong.utiles;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class KeyboardUtil {
  public static void hide(@NonNull Activity activity) {
    final InputMethodManager inputManager = (InputMethodManager) activity
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    final View v = activity.getCurrentFocus();
    if (v != null) {
      v .postDelayed(new Runnable() {
        @Override
        public void run() {
          // TODO Auto-generated method stub
          inputManager.hideSoftInputFromWindow( v.getWindowToken(), 0);
        }
      },50);
    }
  }

  public static void hide(@NonNull View view) {
    InputMethodManager imm = (InputMethodManager) view.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    if (!imm.isActive()) {
      return;
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void show(@NonNull Context context) {
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  public static void show(@NonNull View view) {
    InputMethodManager inputManager = (InputMethodManager) view.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
  }
}
