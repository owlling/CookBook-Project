package com.owlling.cookbook.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.owlling.cookbook.CookBookApp;
import com.owlling.cookbook.R;
import com.owlling.cookbook.constants.Constants;

/**
 * Created by PeOS on 2016/9/1 0001.
 */
public class GlideUtil {

    private static final String TAG = "GlideUtil";
    ImageView imageView;
    private DiskCacheStrategy diskCache = DiskCacheStrategy.ALL;//磁盘缓存
    private boolean isSkipMemoryCache = false;//禁止内存缓存

    public GlideUtil attach(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public GlideUtil injectImage(String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(diskCache)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(R.mipmap.ic_icon_loading)
                .crossFade()
                .into(imageView);
        return this;
    }

    public GlideUtil injectImageWithNull(String url) {
        Log.i("glide util", "url " + url);
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(diskCache)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(null)
                .crossFade()
                .into(imageView);
        return this;
    }

    public static void loadProfile(ImageView iv, int uid) {
        loadProfile(iv, uid + "");
    }

    public static void loadProfile(ImageView iv, String uid) {
        final String url = Constants.baseComProfileUrl.concat(uid)
//                .concat("&time=" + System.currentTimeMillis())
                ;
        Glide.with(iv.getContext())
                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .dontAnimate()
                .placeholder(R.drawable.def_profile)
                .into(iv);
    }

    public static void loadPostImg(final ImageView iv, String postImg) {
        if (TextUtils.isEmpty(postImg)) {
            iv.setVisibility(View.GONE);
            return;
        }
        final String url = Constants.baseComPostImgUrl.concat(postImg);
        Glide.with(iv.getContext())
                .load(url)
                .dontAnimate()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        iv.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(iv);
    }

    public GlideUtil injectImageWithoutCache(String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(R.mipmap.ic_icon_loading)
                .crossFade()
                .into(imageView);
        return this;
    }

    public GlideUtil injectTarget(String url, Target target, Context context, @Nullable RequestListener
            requestListener) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(diskCache)
                .listener(requestListener)
                .into(target);
        return this;
    }

    public GlideUtil clearImage() {
        Glide.clear(imageView);
        imageView.setImageResource(R.mipmap.ic_icon_loading);
        return this;
    }

    public GlideUtil clearImage(int res) {
        Glide.clear(imageView);
        imageView.setImageResource(res);
        return this;
    }

    public void downloadImage(String url, Target target) {
        Glide.with(CookBookApp.getContext())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(diskCache)
                .into(target);
    }

}
