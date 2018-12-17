package com.ems.dingdong.views.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class FormItem extends LinearLayout {

  public enum FormEvent {
    REMOVER_TEXT_CHANGED,
    ADD_TEXT_CHANGED,
    TEXT_LOST_FOCUS,
    TEXT_CHANGGING,
    TEXT_CHANGE,
    ACTION_CLICK,
    TEXT_FOCUS,
    ITEM_SELECTED;
  }
  public interface FormItemListener {
    void handleEvent(FormEvent event);
  }
  protected boolean showClear;
  protected FormItemListener mListener;

  @BindView(R.id.form_item_layout)
  View mLayout;
  @BindView(R.id.form_item_icon)
  ImageView mIcon;
  @BindView(R.id.form_item_action)
  ImageView mAction;

  public FormItem(Context context) {
    super(context);
  }

  public FormItem(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (inflater != null) {
      inflater.inflate(getLayoutId(), this);
      ButterKnife.bind(this);

      final TypedArray input = getContext().obtainStyledAttributes(
          attrs, R.styleable.FormItem, 0, 0);

      Drawable drawable = input.getDrawable(R.styleable.FormItem_formIcon);
      if (drawable != null) {
        mIcon.setImageDrawable(drawable);
        mIcon.setVisibility(VISIBLE);
      } else {
        mIcon.setVisibility(GONE);
      }

      drawable = input.getDrawable(R.styleable.FormItem_formAction);
      if (drawable != null) {
        mAction.setVisibility(VISIBLE);
        mAction.setImageDrawable(drawable);
      } else {
        mAction.setVisibility(GONE);
      }

      if(input.getBoolean(R.styleable.FormItem_formShowClear, true)){
        showClear = true;
      } else {
        showClear = false;
      }

      mAction.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mListener != null) {
            onActionClicked();
            mListener.handleEvent(FormEvent.ACTION_CLICK);
          }
        }
      });
      initAttribute(input);
      input.recycle();
    }
  }

  protected void onActionClicked() {
  }

  protected abstract void initAttribute(TypedArray input);

  protected abstract int getLayoutId();

  public void onVerified(boolean isValid) {
    if (isValid) {
      mLayout.setBackgroundResource(R.drawable.bg_round_corner_valid);
    } else {
      mLayout.setBackgroundResource(R.drawable.bg_round_corner_invalid);
    }
  }

  public void setFormItemListener(FormItemListener listener) {
    mListener = listener;
  }

  public ImageView getActionView() {
    return mAction;
  }
}
