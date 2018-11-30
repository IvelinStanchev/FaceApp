package bg.adastragrp.adastrafaceapp.data.custom_bindings;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CircularProgressDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import bg.adastragrp.adastrafaceapp.utils.AppUtils;

public class ImageViewCustomBindings {

    @BindingAdapter(value = { "android:src", "placeholderImage", "errorImage" }, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable placeholderImage, Drawable errorImage) {
        if (TextUtils.isEmpty(url)) {
            if (errorImage != null) {
                imageView.setImageDrawable(errorImage);
            }
        } else {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imageView.getContext());
            circularProgressDrawable.setStrokeWidth(AppUtils.convertDpToPx(2));
            circularProgressDrawable.setCenterRadius(AppUtils.convertDpToPx(10));
            circularProgressDrawable.start();

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(placeholderImage != null
                            ? placeholderImage
                            : circularProgressDrawable)
                    .error(errorImage);
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true);

            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    @BindingAdapter("app:srcDrawable")
    public static void setImageUrl(ImageView imageView, Bitmap bitmapSrc) {
        if (bitmapSrc != null) {
            imageView.setImageBitmap(bitmapSrc);
        }
    }
}
