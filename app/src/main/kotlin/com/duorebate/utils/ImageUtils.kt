package com.duorebate.utils

import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.config.BoxingConfig
import com.bilibili.boxing_impl.ui.BoxingActivity
import rx.Observable
import java.io.*



/**
 * Created by bingj on 2017/7/5.
 */

/**
 * 存放拍照图片的uri地址
 */
@JvmField var imageUriFromCamera: Uri? = null

/**
 * 打开相册
 * */
fun Activity.pickImageFromAlbum(addRequest: Int){
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    intent.type = "image/*"
    startActivityForResult(intent,addRequest)
}
/**
 * 打开相册多选
 */
fun Activity.pickImageFromMultAlbum(addRequest: Int,maxCunt: Int?) {
    val config = BoxingConfig(BoxingConfig.Mode.MULTI_IMG).needGif().withMaxCount(maxCunt?: ALBUM_MULT_COUNT)
    Boxing.of(config).withIntent(this, BoxingActivity::class.java).start(this, addRequest)
}
/**
 * 打开相机拍照获取图片
 */
fun Activity.pickImageFromCamera(addRequest: Int){
    // 先生成一个uri地址用于存放拍照获取的图片
    if (isSDCard()) {
        imageUriFromCamera = createImageUri(this)
    } else {
        this.toast("对不起请插入SD卡")
        return
    }
    val intent = Intent()
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera)
    startActivityForResult(intent,addRequest)
}

/**
 * 图片裁剪
 */
fun Activity.cropImage(srcUri: Uri?,addRequest: Int) {
    // 先生成一个uri地址用于存放拍照获取的图片
    if (isSDCard()) {
        imageUriFromCamera = createImageUri(this)
    } else {
        this.toast("对不起请插入SD卡")
        return
    }
    val intent = Intent("com.android.camera.action.CROP")
    intent.setDataAndType(srcUri,"image/*")
    intent.putExtra("crop","true")
    ////////////////////////////////////////////////////////////////
    // 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
    ////////////////////////////////////////////////////////////////
    // 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
    /////////////////////////////////
    // 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
    ////////////////////////////////////////////////////////////////
    // 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
    //	会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
    //  不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
    ////////////////////////////////////////////////////////////////

    // aspectX aspectY 是裁剪框宽高的比例
    intent.putExtra("aspectX", 1)
    intent.putExtra("aspectY", 1)
    //  outputX outputY 是裁剪后生成图片的宽高
    //	intent.putExtra("outputX", 300);
    //	intent.putExtra("outputY", 100);

    //  return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现OOM,推荐下面为false时的方式
    //  return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
    intent.putExtra("return-data", false)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera)
    //是否关闭面部识别
    intent.putExtra("noFaceDetection", false) // no face detection
    startActivityForResult(intent, addRequest)
}

/**
 * 创建一条图片uri,用于保存拍照后的照片
 * */
private fun createImageUri(context: Context): Uri {
    var name = "boreImg"+System.currentTimeMillis()
    var uri: Uri? = null
//    val temp = generateImagePath(context.applicationContext.packageResourcePath,name,context)
//    if (temp.exists())
//        temp.parentFile.mkdirs()
//    if (Build.VERSION.SDK_INT >= 24)
//        uri = FileProvider.getUriForFile(context,context.packageName+".fileprovider",temp)
//    else
//        uri = Uri.fromFile(temp)

    val values = ContentValues()
    values.put(MediaStore.Images.Media.TITLE, name)
    values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpg")
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    return uri
}
private fun generateImagePath(var1: String,name: String,var2: Context): File {
    val var3 = "/Android/data/" + var1 + "/image/" + name + ".jpg"
    return File(getStorageDir(var2),var3)
}
private var storageDir: File? = null
private fun getStorageDir(context: Context): File?{
        return if (storageDir == null) {
            val file = Environment.getExternalStorageDirectory()
            if (file.exists()) {
                return file
            }
            storageDir = context.getFilesDir()
            return storageDir
        } else {
            return storageDir
        }
}

fun compressWidthImage(absoluePath: String?,widthSize: Int): Observable<ByteArray>{
    // 先从本地获取图片,利用Glide压缩图片后获取byte[]
    return Observable.just(absoluePath)
            .flatMap { absoluePath->
                // 在work线程中，同步压缩图片，然后Observable返回
                var bytes: ByteArray = bitmapToBytes(revitionImageWidthSize(absoluePath!!,widthSize))
                Observable.just(bytes)
            }
}
//fun compressImageList(absoluePaths: ArrayList<String>,widthSize: Int){
//
//}

/**
 * Bitmap transfer to bytes

 * @param
 * *
 * @return
 */
fun bitmapToBytes(bm: Bitmap): ByteArray{
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG,100,baos)
    return baos.toByteArray()
}

/**
 * Byte transfer to bitmap

 * @param byteArray
 * *
 * @return
 */
fun byteToBitmap(byteArray: ByteArray): Bitmap? {
    if (byteArray.size != 0) {
        return BitmapFactory
                .decodeByteArray(byteArray, 0, byteArray.size)
    } else {
        return null
    }
}

/**
 * Byte transfer to drawable

 * @param byteArray
 * *
 * @return
 */
fun byteToDrawable(byteArray: ByteArray?): Drawable {
    var ins: ByteArrayInputStream? = null
    if (byteArray != null) {
        ins = ByteArrayInputStream(byteArray)
    }
    return Drawable.createFromStream(ins, null)
}

@Throws(IOException::class)
fun revitionImageWidthSize(path: String, widthSize: Int): Bitmap {
    val degree = readPictureDegree(path) //读取图片翻转角度(为了适配三星机型)

    // 取得图片
    var temp: InputStream = FileInputStream(File(path))
    val options = BitmapFactory.Options()
    // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
    options.inJustDecodeBounds = true
    // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
    BitmapFactory.decodeStream(temp, null, options)
    // 关闭流
    temp.close()

    var oHeight = options.outHeight //图片高度
    var oWidth = options.outWidth //图片宽度
    //按照片比例计算出来的高度
    var scale = 1f
    if (oWidth >= widthSize) {
        scale = oWidth.toFloat() / widthSize
    } else {
        scale = widthSize.toFloat() / oWidth
    }
    oHeight = (oHeight * 1.0 / oWidth * widthSize).toInt()
    oWidth = widthSize
    // 生成压缩的图片
    var i = 0
    var bitmap: Bitmap? = null
    while (true) {
        // 这一步是根据要设置的大小，使宽和高都能满足
        if (options.outWidth shr i <= oHeight) {
            //		options.outHeight = oHeight;
            // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
            temp = FileInputStream(File(path))
            // 这个参数表示 新生成的图片为原始图片的几分之一。
            options.inSampleSize = Math.pow(2.0, i.toDouble()).toInt()
            // 这里之前设置为了true，所以要改为false，否则就创建不出图片
            options.inJustDecodeBounds = false

            bitmap = BitmapFactory.decodeStream(temp, null, options)
            break
        }
        i += 1
    }
    //根据degree来创建新的bitmap
    bitmap = zoomBitmap(bitmap, oWidth.toFloat(), oHeight.toFloat(), degree)
    return bitmap
}

/**
 * Resize the bitmap

 * @param bitmap
 * *
 * @param width
 * *
 * @param height
 * *
 * @return
 */
fun zoomBitmap(bitmap: Bitmap?, width: Float, height: Float,
               degree: Int): Bitmap {
    val w = bitmap!!.width
    val h = bitmap!!.height
    val matrix = Matrix()
    var scaleWidth = width.toFloat() / w
    var scaleHeight = height.toFloat() / h

    if (degree != 0) {
        matrix.preRotate(degree.toFloat())
        if (degree == 90 || degree == 270) {
            val tmp = scaleWidth
            scaleWidth = scaleHeight
            scaleHeight = tmp
        }
    }
    matrix.postScale(scaleWidth, scaleHeight)
    val newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)
    return newbmp
}

/**
 * 获取图片信息

 * @param path
 * *
 * @return
 */
fun readPictureDegree(path: String): Int {
    var degree = 0
    try {
        val exifInterface = ExifInterface(path)
        val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL)
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return degree
}

/**
 * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
 */
fun getImageAbsolutePath19(context: Context?, imageUri: Uri?): String? {
    if (context == null || imageUri == null)
        return null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
        if (isExternalStorageDocument(imageUri)) {
            val docId = DocumentsContract.getDocumentId(imageUri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(imageUri)) {
            val id = DocumentsContract.getDocumentId(imageUri)
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(imageUri)) {
            val docId = DocumentsContract.getDocumentId(imageUri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = MediaStore.Images.Media._ID + "=?"
            val selectionArgs = arrayOf(split[1])
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    }

    // MediaStore (and general)
    if ("content".equals(imageUri.scheme, ignoreCase = true)) {
        // Return the remote address
        if (isGooglePhotosUri(imageUri))
            return imageUri.lastPathSegment
        return getDataColumn(context, imageUri, null, null)
    } else if ("file".equals(imageUri.scheme, ignoreCase = true)) {
        return imageUri.path
    }// File
    return null
}

private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
    var cursor: Cursor? = null
    val column = MediaStore.Images.Media.DATA
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        if (cursor != null)
            cursor.close()
    }
    return null
}
/**
 * Save image to the SD card
 *
 * @param photoName
 * @param path
 */
fun savePhotoToSDCard(bytes: ByteArray, path: String,
                      photoName: String, quality: Int): String {
    var imageUrl = ""
    val photoBitmap = byteToBitmap(bytes)

    if (checkSDCardAvailable()) {
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val photoFile = File(path, photoName)
        imageUrl = photoFile.absolutePath
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(photoFile)
            if (photoBitmap != null) {
                if (photoBitmap!!.compress(Bitmap.CompressFormat.JPEG,
                        quality, fileOutputStream)) {
                    fileOutputStream.flush()
                    //						fileOutputStream.close();
                }
            }
        } catch (e: FileNotFoundException) {
            photoFile.delete()
            imageUrl = ""
            e.printStackTrace()
        } catch (e: IOException) {
            photoFile.delete()
            imageUrl = ""
            e.printStackTrace()
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return imageUrl
        }

    }
    return imageUrl
}

/**
 * @param uri The Uri to check.
 * *
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * *
 * @return Whether the Uri authority is DownloadsProvider.
 */
private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * *
 * @return Whether the Uri authority is MediaProvider.
 */
private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * *
 * @return Whether the Uri authority is Google Photos.
 */
private fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}

/**
 * 压缩图片的方法

 * @param image bitmap对象
 * *
 * @param path  生成的文件地址
 * *
 * @return
 */
fun compress_bitmap(image: Bitmap, path: String): File {

    /*
         * image——图片对象  path——产生的新文件的位置（不要把原文件的路径传递过来）
		 */

    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    var options = 99
    while (baos.toByteArray().size / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
        baos.reset()// 重置baos即清空baos
        image.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
        if (options == 10) {
            options = 10
            break
        }
        options -= 10// 每次都减少10
    }
    //       int a= baos.toByteArray().length / 1024;
    val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
    //        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
    val destDir = File(Environment.getExternalStorageDirectory().toString() + "/repair")
    if (!destDir.exists()) {
        destDir.mkdirs()
    }
    val file = File(path)// 将要保存图片的路径
    try {
        val bos = BufferedOutputStream(
                FileOutputStream(file))
        //bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
        bos.write(baos.toByteArray())
        bos.flush()
        bos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return file
}
/**
 * 图片压缩方法测试
 */
fun compressImageFromFile(srcPath: String): Bitmap {
    Log.i("tag", "file=" + File(srcPath).length())
    val newOpts = BitmapFactory.Options()
    newOpts.inJustDecodeBounds = true//只读边,不读内容
    var bitmap = BitmapFactory.decodeFile(srcPath, newOpts)

    newOpts.inJustDecodeBounds = false
    val w = newOpts.outWidth
    val h = newOpts.outHeight
    val hh = 1024f//
    val ww = 768f//
    //        float hh = 60f;//
    //        float ww = 60f;//
    var be = 1
    if (w > h && w > ww) {
        be = (newOpts.outWidth / ww).toInt()
    } else if (w < h && h > hh) {
        be = (newOpts.outHeight / hh).toInt()
    }
    if (be <= 0)
        be = 1
    newOpts.inSampleSize = be//设置采样率

    newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888//该模式是默认的,可不设
    newOpts.inPurgeable = true// 同时设置才会有效
    newOpts.inInputShareable = true//。当系统内存不够时候图片自动被回收

    bitmap = BitmapFactory.decodeFile(srcPath, newOpts)
    //	      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
    //其实是无效的,大家尽管尝试
    return bitmap
}

fun compressWidthImage(context: Context, absoluePath: String, widthSize: Int): Observable<ByteArray> {
    // 先从本地获取图片,利用Glide压缩图片后获取byte[]
    return Observable.just(absoluePath)
            .flatMap { absoluePath ->
                // 在work线程中，同步压缩图片，然后Observable返回
                // 即将Glide的回调封装成RxJava的Observable
                var bytes: ByteArray? = null
                try {
                    bytes = handleWidthImage(absoluePath, widthSize)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Observable.just(bytes)
            }
}
@Throws(IOException::class)
fun handleWidthImage(imageurl: String, widthSize: Int): ByteArray? {
    return bitmapToBytes(revitionImageWidthSize(imageurl, widthSize))
}