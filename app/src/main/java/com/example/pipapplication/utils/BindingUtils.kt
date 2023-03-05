package com.example.pipapplication.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.pipapplication.R

object BindingUtils {
    @BindingAdapter("image")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
//        view.setImageURI(url)

        Glide.with(view.context)
//            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_member_profile))
            .load(url)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_video_player)
//            .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
            .into(view)
    }
}