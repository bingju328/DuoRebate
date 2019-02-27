package com.duorebate.base.view

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Message
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.duorebate.utils.getScreenWidth
import com.duorebate.utils.isFastDoubleClick
import com.network.glide.GlideHelper
import java.lang.ref.WeakReference
import java.util.*
/**
 * 顶部广告的ViewPager
 *
 * @author Stone
 */
open class RollViewPagerFitXY : ViewPager {

    private lateinit var mActivity: Activity
    private lateinit var ct: Context
    private lateinit var dotList: List<View>
    private var dotFocus: Int = 0
    private var dotNormal: Int = 0
    private var tvDescription: TextView? = null
    private var mLastMotionX: Float = 0.toFloat()
    private var mLastMotionY: Float = 0.toFloat()
//    private val mImageLoader: HhyjImageLoader?


    private var isCanScroll = true
    private val sScreenWidth: Int = 0
    private val sScreenHeight: Int = 0

    private var application: Application? = null

    private var myHandler: MyHandler? = null

    lateinit var onTouchCllickCallBack: MyOnTouchCllickCallBack

    // public class TaskPager implements Runnable {
    //
    // @Override
    // public void run() {
    // currentItem = (currentItem + 1) % imageUrlList.size();
    // handler.obtainMessage().sendToTarget();
    // }
    //
    // }
    //
    // public Handler handler = new Handler() {
    // @Override
    // public void handleMessage(android.os.Message msg) {
    // RollViewPagerFitXY.this.setCurrentItem(currentItem);
    // // startRoll();
    // };
    // };

    // 需要显示的文本内容
    private var titleList: List<String>? = null

    private var imageUrlList: List<String>? = null
    private var adapter: ViewPagerAdatper? = null

    //
    //	Handler myHandler = new Handler() {
    //		@Override
    //		public void handleMessage(Message msg) {
    //			super.handleMessage(msg);
    //			if (msg.what == 1) {
    //				HhyjRollViewPagerFitXY.this.setCurrentItem(currentItem);
    //			}
    //		}
    //	};
    internal var myTimer = Timer()
    internal var myTimerTask: TimerTask = object : TimerTask() {

        override fun run() {
            // 需要做的事:发送消息
            val message = Message.obtain()
            message.what = 1
            if (imageUrlList!!.size > 1 && isCanScroll) {
                currentItem = (currentItem + 1) % imageUrlList!!.size
                if (myHandler == null) {
                    myHandler = MyHandler(this@RollViewPagerFitXY)
                }
                myHandler!!.sendMessage(message)
                //				message.recycle();
            }
        }
    }

    var hasSetAdapter = false
    private lateinit var myOnTouchListener: MyOnTouchListener
    private val sp: SharedPreferences? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

//    interface OnPagerClickCallBack {
//        fun pagerClickCallBack(position: Int)
//    }

    interface MyOnTouchCllickCallBack {
        fun OnTouchCllick(position: Int)
    }

    constructor(application: Application?, ct: Context, dotList: List<View>, dotFocus: Int, dotNormal: Int,
                myOnTouchCllickCallBack: MyOnTouchCllickCallBack) : super(ct) {
        this.application = application
        this.ct = ct
        this.dotList = dotList
        this.dotFocus = dotFocus
        this.dotNormal = dotNormal
        this.mActivity = ct as Activity
        //		bitmapUtils = new BitmapUtils(ct);
        //		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
        // taskPager = new TaskPager();
        this.onTouchCllickCallBack = myOnTouchCllickCallBack
        myOnTouchListener = MyOnTouchListener()
//        mImageLoader = HhyjImageLoader.getInstance()
        myHandler = MyHandler(this@RollViewPagerFitXY)
    }

    //	public void initInfo() {
    //		DisplayMetrics metric = new DisplayMetrics();
    //		mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    //		Constants.sScreenWidth = metric.widthPixels;
    //		Constants.sScreenHeight = metric.heightPixels;
    //	}

    override fun scrollTo(x: Int, y: Int) {
        if (dotList != null && dotList.size <= 1) {
            return
        }
        super.scrollTo(x, y)
    }

    inner class MyOnTouchListener : View.OnTouchListener {

        private var satartCurrentTimeMillis: Long = 0

        override fun onTouch(arg0: View, arg1: MotionEvent): Boolean {
            val currentX = arg1.x
            val currentY = arg1.y
            when (arg1.action) {
                MotionEvent.ACTION_DOWN -> {
                    satartCurrentTimeMillis = System.currentTimeMillis()
                    //				myHandler.removeCallbacksAndMessages(null);
                    isCanScroll = false
                }

                MotionEvent.ACTION_UP -> {
                    val duration = System.currentTimeMillis() - satartCurrentTimeMillis
                    val upX = arg1.x
                    if (duration < 500 && upX == currentX) {
                        if (!isFastDoubleClick()) {
                            onTouchCllickCallBack.OnTouchCllick(currentItem)
                        }

                    }
                    isCanScroll = true
                    // startRoll();
                    isCanScroll = true
                }
                // startRoll();
                MotionEvent.ACTION_CANCEL -> isCanScroll = true
                MotionEvent.ACTION_MOVE -> isCanScroll = false
            }//				 myHandler.removeCallbacksAndMessages(null);
            return true
        }

    }
    //	public boolean onInterceptTouchEvent(MotionEvent ev) {
    //		// TODO Auto-generated method stub
    //		//当拦截触摸事件到达此位置的时候，返回true，
    //		//说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
    //		/*--------------------------------------------------------*/
    //		switch (ev.getAction()) {
    //			case MotionEvent.ACTION_DOWN:
    //				Log.i(TAG, "onInterceptTouchEvent--->ACTION_DOWN");
    //				break;
    //			case MotionEvent.ACTION_MOVE:
    //				Log.i(TAG, "onInterceptTouchEvent--->ACTION_MOVE");
    //				break;
    //		}
    //		boolean test = super.onInterceptTouchEvent(ev);
    ////		Log.i(TAG, "super.onInterceptTouchEvent--->"+test);
    //		/*--------------------------------------------------------*/
    //
    //		return super.onInterceptTouchEvent(ev);
    //	}

    //	@Override
    //	public boolean onTouchEvent(MotionEvent ev) {
    //		switch (ev.getAction()) {
    //			case MotionEvent.ACTION_DOWN:
    //				Log.i(TAG, "onTouchEvent--->ACTION_DOWN");
    //				break;
    //			case MotionEvent.ACTION_MOVE:
    //				Log.i(TAG, "onTouchEvent--->ACTION_MOVE");
    //				break;
    //		}
    //		boolean test = super.onTouchEvent(ev);
    ////		Log.i(TAG, "super.onTouchEvent--->"+test);
    //		return super.onTouchEvent(ev);
    //	}

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (null != this.parent) {
                    this.parent.requestDisallowInterceptTouchEvent(true)
                }
                mLastMotionX = x
                mLastMotionY = y
            }
            MotionEvent.ACTION_MOVE -> if (Math.abs(x - mLastMotionX) < Math.abs(y - mLastMotionY)) {
                if (null != this.parent) {
                    this.parent.requestDisallowInterceptTouchEvent(false)
                }
            } else {
                if (null != this.parent) {
                    this.parent.requestDisallowInterceptTouchEvent(true)
                }
                Log.d(TAG, "in viewpager")
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> if (null != this.parent) {
                this.parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        /*--------------------------------------------------------*/
        //		switch (ev.getAction()) {
        //			case MotionEvent.ACTION_DOWN:
        //				Log.i(TAG, "dispatchTouchEvent--->ACTION_DOWN");
        //				break;
        //			case MotionEvent.ACTION_MOVE:
        //				Log.i(TAG, "dispatchTouchEvent--->ACTION_MOVE");
        //				break;
        //		}
        //		boolean test = super.dispatchTouchEvent(ev);
        ////		Log.i(TAG, "super.dispatchTouchEvent--->"+test);
        /*--------------------------------------------------------*/
        return super.dispatchTouchEvent(ev)
    }

    fun setTitle(tvDescription: TextView?, titleList: List<String>?) {
        this.tvDescription = tvDescription
        this.titleList = titleList
        if (tvDescription != null && titleList != null && titleList.size > 0) {
            tvDescription.text = titleList[0]
        }
    }

    //	private BitmapUtils bitmapUtils;
    // private TaskPager taskPager;
    //有效避免OOM异常
    class MyHandler(activity: RollViewPagerFitXY) : Handler() {
        private val mActivityReference: WeakReference<RollViewPagerFitXY>

        init {
            mActivityReference = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            val activity = mActivityReference.get()
            if (activity != null) {
                if (msg.what == 1) {
                    activity.currentItem = currentItem
                    mActivityReference.clear()
                }
            }
        }
    }

    //

    fun setImageUrl(imageUrlList: List<String>) {
        this.imageUrlList = imageUrlList
    }

    fun startRoll() {
        if (!hasSetAdapter) {
            hasSetAdapter = true
            this.setOnPageChangeListener(MyOnPageChangeListener())
            adapter = ViewPagerAdatper()
            this.setAdapter(adapter)
        }
        // handler.postDelayed(taskPager, 4000);
        //		myTimer.schedule(myTimerTask, 1000, 4000);

    }

    inner class MyOnPageChangeListener : ViewPager.OnPageChangeListener {
        internal var oldPositionItem = 0

        override fun onPageScrollStateChanged(arg0: Int) {

        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageSelected(arg0: Int) {
            currentItem = arg0 % dotList!!.size
            if (tvDescription != null) {
                tvDescription!!.text = titleList!![arg0 % titleList!!.size]
            }
            if (dotList != null && dotList.size > 1) {
                dotList[arg0 % dotList.size].setBackgroundResource(dotNormal)
                dotList[oldPositionItem % dotList.size].setBackgroundResource(dotFocus)
            }
            oldPositionItem = arg0
        }

    }

    inner class ViewPagerAdatper : PagerAdapter() {

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
//            if (sScreenWidth === 0 || HhyjConstants.sScreenHeight === 0) {
//                HhyjBaseUtils.getScreen(mActivity)
//            }
            val iv = ImageView(ct)
            iv.layoutParams = RelativeLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,
                    getScreenWidth(mActivity) * 248 / 640)

            // HhyjImageLoader.getInstance().displayImage(imageUrlList.get(position
            // % dotList.size()),
            // iv);

            iv.scaleType = ImageView.ScaleType.FIT_XY
            val imagePath = imageUrlList!![position % dotList!!.size]
            if (!TextUtils.isEmpty(imagePath)) {
//                if (hhyjRelaseBitmap == null) {
//                    hhyjRelaseBitmap = HhyjRelaseBitmap()
//                }
                //todo 正在加载中时，还没处理
                GlideHelper.showImage(context,imagePath,iv as ImageView)
//                mImageLoader!!.displayImage(imagePath, iv, hhyjRelaseBitmap)
            }

            iv.setOnTouchListener(myOnTouchListener)
            container.addView(iv)
            return iv
        }

        override fun getCount(): Int {
            return 100000
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

    }

//    fun cleanBitmapList() {
//        if (hhyjRelaseBitmap == null) {
//            hhyjRelaseBitmap = HhyjRelaseBitmap()
//        }
//        if (mImageLoader != null) {
//            mImageLoader!!.clearDiskCache()
//            mImageLoader!!.clearMemoryCache()
//            hhyjRelaseBitmap!!.cleanBitmapList()
//        }
//
//    }

    fun removeMessage() {
        if (myHandler != null) {
            myHandler!!.removeCallbacksAndMessages(null)
        }
    }

    override fun onDetachedFromWindow() {
        // handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow()
    }

    companion object {
        private val TAG = "RollViewPagerFitXY"
//        private var hhyjRelaseBitmap: HhyjRelaseBitmap? = HhyjRelaseBitmap()

        var currentItem = 0
    }
}
