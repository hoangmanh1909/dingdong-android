package com.vinatti.dingdong.utiles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.views.picker.PickerUI;


public class ViewUtils {

    private static boolean mAnimationEnd = false;

    public static void setupUI(final View view, final Activity context) {
    //Set up touch listener for non-text box views to hide keyboard.
    if (!(view instanceof EditText)) {
      view.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
          Utils.hideSoftKeyboard(context);
          view.requestFocus();
          if (view instanceof ViewGroup && !(view instanceof Spinner)) {
              view.setFocusable(true);
              view.setFocusableInTouchMode(true);
            }
          return false;
        }
      });
    }

    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {
      for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
        View innerView = ((ViewGroup) view).getChildAt(i);
        setupUI(innerView, context);
      }
    }
  }

  public static int convertDpToPixel(Context context, float dp) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    float px = dp * (metrics.densityDpi / 160f);
    return (int) px;
  }
  public static void setupUIEnable(final View view) {
    //Set up touch listener for non-text box views to hide keyboard.
    view.setEnabled(false);
    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {
      for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
        View innerView = ((ViewGroup) view).getChildAt(i);
        setupUIEnable(innerView);
      }
    }
  }

  public static double getSizeOfScreen(Activity activity){
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
    double sizeInches = Math.sqrt(x+y);
    return sizeInches;
  }

  public static int getScreenHeight(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return metrics.heightPixels;
  }

  public static int getScreenWidth(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return metrics.widthPixels;
  }



    public static void startAnimation(final TextView mTextView) {
        if(!mAnimationEnd) {
            ValueAnimator va = ValueAnimator.ofFloat(-1f, 0f);
            int mDuration = 400; //in millis
            va.setDuration(mDuration);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTextView.setAlpha(1 + (float) animation.getAnimatedValue());
                    mTextView.setTranslationY(mTextView.getHeight() * (float) animation.getAnimatedValue());

                }
            });
            va.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimationEnd = true;
                    final ValueAnimator va1 = ValueAnimator.ofFloat(0f, -1f);
                    int mDuration = 400; //in millis
                    va1.setDuration(mDuration);
                    va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mTextView.setAlpha(1 + (float) animation.getAnimatedValue());
                            mTextView.setTranslationY(mTextView.getHeight() * (float) animation.getAnimatedValue());
                        }
                    });
                    va1.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            mAnimationEnd = false;
                        }
                    });
                    Handler handler =new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            va1.start();
                        }
                    },400);


                }
            });
            va.start();
        }
    }

    public static void setupUI(View view, final PickerUI... params) {
        if (!(view instanceof PickerUI)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    for(PickerUI pickerUI:params)
                    {
                        if(pickerUI.isPanelShown())
                        {
                            pickerUI.slide(PickerUI.SLIDE.DOWN);
                        }
                    }

                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                if (!(innerView instanceof PickerUI)) {
                    setupUI(innerView, params);
                }
            }
        }
    }

    public static void setBackgroundColor(View view, int color, Context context) {
      int sdk = android.os.Build.VERSION.SDK_INT;
      if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        view.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(color)));
      } else {
        view.setBackground(new ColorDrawable(context.getResources().getColor(color)));
      }
    }

  public static void setTextFromHtml(TextView textView, String htmlText) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    } else {
      textView.setText(Html.fromHtml(htmlText));
    }
  }

  public static void setBackgroundDrawable(Context mContext, View view, int background) {
    int sdk = android.os.Build.VERSION.SDK_INT;
    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
      view.setBackgroundDrawable(mContext.getResources().getDrawable(background));
    } else {
      view.setBackground(mContext.getResources().getDrawable(background));
    }
  }

    public static void viewActionBar(ActionBar abar, LayoutInflater layoutInflater, String title) {
        View viewActionBar = layoutInflater.inflate(R.layout.custom_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.txtActionBar);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
    }
    public static void viewActionBar(ActionBar abar, LayoutInflater layoutInflater, CharSequence title) {
        View viewActionBar = layoutInflater.inflate(R.layout.custom_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.txtActionBar);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
    }
}
