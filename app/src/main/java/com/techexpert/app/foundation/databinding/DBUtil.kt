package com.techexpert.app.foundation.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class DBUtil {
    companion object {
        /*
        Common image loading method
         */
        @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?, placeHolder: Drawable?) {
            if (url == null) {
                imageView.setImageDrawable(placeHolder)
            } else {
                Glide
                    .with(imageView.context)
                    .load(url)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .into(imageView)
            }
        }
    }
}
