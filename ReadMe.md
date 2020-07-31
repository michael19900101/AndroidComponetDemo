# 快速入门

### 1.新建一个水印文字图片，压缩照片质量到90k

```kotlin
val waterStr = "中国始终支持多边主义、践行多边主义，以开放、合作、共赢精神同世界各国共谋发展" // 水印文字
val waterPosition = WaterMarkPosition.BOTTOM // 水印位置（TOP：上方，左对齐 CENTER:居中，左对齐 BOTTOM:下方，左对齐）
val filePath = ".../com.aotuman.watermak/img.jpg" // 文件保存路径
val needCompress = true  // 是否压缩图片质量
val saveFileLength = 90  // 压缩图片质量到指定大小(单位:kb)
val targetWidth = 600 // 目标照片宽度
val targetHeight = 840 // 目标照片高度
val targetView = imageView // 照片显示对应的imageView
WaterMark.Builder()
    .setWaterMarkText(waterStr)
    .setWaterMarkPosition(waterPosition)
    .setSaveFilePath(filePath) 
    .setSaveFileSize(saveFileLength)
    .setNeedCompress(needCompress)
    .setTargetWidth(targetWidth)
    .setTargetHeight(targetHeight)
    .setTargetView(targetView)
    .build()
```



### 2.效果展示

![img](https://raw.githubusercontent.com/michael19900101/AndroidComponetDemo/master/img/c7b6c017-4c09-428a-92d2-dfcbcbbfefbd.png)

