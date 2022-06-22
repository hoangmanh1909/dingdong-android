package com.ems.dingdong.utiles

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ems.dingdong.R

fun AppCompatImageView.loadImageWithCustomCorners(urlImage: String?, radius: Int){
    Glide.with(context).load(urlImage)
        .transform(CenterCrop(), RoundedCorners(radius))
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this@loadImageWithCustomCorners)

}
fun View.setSingleClick(execution: (View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke(this@setSingleClick)
        }
    })
}