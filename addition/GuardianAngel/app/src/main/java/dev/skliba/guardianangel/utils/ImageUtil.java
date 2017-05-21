package dev.skliba.guardianangel.utils;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.File;

import dev.skliba.guardianangel.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import timber.log.Timber;

public class ImageUtil {

    private ImageUtil() {
    }

    public static void displayImage(ImageView imageView, @NonNull File imageFile) {
        if (imageView == null) {
            Timber.e("Image view is null! Aborting.");
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageFile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user_placeholder)
                .into(imageView);
    }

    public static void displayImage(ImageView imageView, @NonNull File imageFile, @DrawableRes int drawableRes) {
        if (imageView == null) {
            Timber.e("Image view is null! Aborting.");
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageFile)
                .placeholder(drawableRes)
                .into(imageView);
    }

    public static void displayImage(ImageView imageView, @NonNull Drawable imageDrawable, @DrawableRes int drawableRes) {
        if (imageView == null) {
            Timber.e("Image view is null! Aborting.");
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageDrawable)
                .placeholder(drawableRes)
                .into(imageView);
    }


    public static void displayCircleCroppedImage(ImageView imageView, @NonNull File imageFile) {
        if (imageView == null) {
            Timber.e("Image view is null! Aborting.");
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageFile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    public static void displayCircleCroppedImage(ImageView imageView, @NonNull Drawable drawableFile) {
        if (imageView == null) {
            Timber.e("Image view is null! Aborting.");
            return;
        }
        Glide.with(imageView.getContext())
                .load(drawableFile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }
}
