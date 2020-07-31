package com.aotuman.watermark;

import android.widget.ImageView;

import com.aotuman.common.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class WaterMark {

    private String waterMarkText;
    private WaterMarkPosition waterMarkPosition;
    private String saveFilePath;
    private int saveFileSize;
    private boolean needCompress;
    private int targetWidth;
    private int targetHeight;
    private ImageView targetView;

    public WaterMark(Builder builder){
        this.waterMarkText = builder.waterMarkText;
        this.waterMarkPosition = builder.waterMarkPosition;
        this.saveFilePath = builder.saveFilePath;
        this.saveFileSize = builder.saveFileSize;
        this.needCompress = builder.needCompress;
        this.targetWidth = builder.targetWidth;
        this.targetHeight = builder.targetHeight;
        this.targetView = builder.targetView;
    }

    public static class Builder {
        private String waterMarkText;
        private WaterMarkPosition waterMarkPosition;
        private String saveFilePath;
        private int saveFileSize;
        private boolean needCompress;
        private int targetWidth;
        private int targetHeight;
        private ImageView targetView;

        public Builder setWaterMarkText(String waterMarkText) {
            this.waterMarkText = waterMarkText;
            return this;
        }

        public Builder setWaterMarkPosition(WaterMarkPosition waterMarkPosition) {
            this.waterMarkPosition = waterMarkPosition;
            return this;
        }

        public Builder setSaveFilePath(String saveFilePath) {
            this.saveFilePath = saveFilePath;
            return this;
        }

        public Builder setSaveFileSize(int saveFileSize) {
            this.saveFileSize = saveFileSize;
            return this;
        }

        public Builder setNeedCompress(boolean needCompress) {
            this.needCompress = needCompress;
            return this;
        }

        public Builder setTargetWidth(int targetWidth) {
            this.targetWidth = targetWidth;
            return this;
        }

        public Builder setTargetHeight(int targetHeight) {
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder setTargetView(ImageView targetView) {
            this.targetView = targetView;
            return this;
        }

        public WaterMark build(){
            WaterMark waterMark = new WaterMark(this);
            waterMark.handle();
            return waterMark;
        }
    }

    private void handle() {
        WaterTransformation waterTransformation = new WaterTransformation.Builder()
                .setWaterStr(waterMarkText)
                .setWatermarkposition(waterMarkPosition)
                .setSaveFilePath(saveFilePath)
                .setSaveFileLength(saveFileSize)
                .setNeedCompress(needCompress)
                .build();

        int[] imageOption = Utils.getImageOption(saveFilePath, targetWidth, targetHeight);

        if (targetView != null) {
            Glide.with(targetView)
                    .load(saveFilePath)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .override(imageOption[0], imageOption[1])
                    .apply(RequestOptions.bitmapTransform(waterTransformation))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(targetView);
        }

    }

}
