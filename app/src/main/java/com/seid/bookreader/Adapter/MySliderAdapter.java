package com.seid.bookreader.Adapter;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MySliderAdapter extends SliderAdapter {
    private final List<String> imageList;
    PhotoView view_pager;

    public MySliderAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {

        imageSlideViewHolder.bindImageSlide(imageList.get(position));




    }
}
