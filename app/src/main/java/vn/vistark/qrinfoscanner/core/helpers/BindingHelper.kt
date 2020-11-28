package vn.vistark.qrinfoscanner.core.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageUrl(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}