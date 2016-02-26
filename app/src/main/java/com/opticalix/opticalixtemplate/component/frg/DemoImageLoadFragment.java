package com.opticalix.opticalixtemplate.component.frg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.bitmap.ImageResizer;
import com.opticalix.opticalixtemplate.bitmap.OpxLruCache;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.utils.ToastUtils;
import com.opticalix.opticalixtemplate.utils.local_log.Hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by opticalix@gmail.com on 16/1/12.
 */
public class DemoImageLoadFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.button_1)
    Button mButton1;
    @Bind(R.id.button_2)
    Button mButton2;
    @Bind(R.id.button_3)
    Button mButton3;
    @Bind(R.id.iv_1)
    ImageView mIv1;
    private OpxLruCache mLruCache;
    public static final String URL_IMG = "http://pic87.nipic.com/file/20160107/5918804_184624870001_2.jpg";

    public static DemoImageLoadFragment newInstance() {
        LogUtils.i("newInstance method invoke!");
        return new DemoImageLoadFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.frg_image_load, container, false);
        ButterKnife.bind(this, view);

        mLruCache = new OpxLruCache();
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);

        return view;
    }

    private void setBitmap(ImageView iv, String url, int reqW, int reqH) throws IOException {
        Bitmap bitmap = null;
        String key = Hash.md5(url);
        if (key != null) {
            //1. check if cached
            if ((bitmap = mLruCache.get(key)) == null) {
                LogUtils.d(this, String.format("bitmap doesn't exist in LruCache\nurl=%s", url));

                //2 load from net
                new Thread(new LoadRunnable(url, reqW, reqH)).start();
            } else {
                //3. set Bitmap directly or send Msg
                LogUtils.d(this, String.format("bitmap find! send msg to setImage\nurl=%s", url));
                iv.setImageBitmap(bitmap);//其实这样并不是强制iv宽高，如需强制要求得设置
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                try {
                    ViewGroup viewGroup = (ViewGroup) mIv1.getParent();
                    View btn3 = viewGroup.findViewById(R.id.button_3);
                    int remainWidth, remainHeight;
                    remainWidth = viewGroup.getWidth();
                    if (btn3 != null) {
                        remainHeight = viewGroup.getHeight() - btn3.getBottom();
                    } else {
                        remainHeight = viewGroup.getHeight() / 2;
                    }

                    //FIXME？？ how to force measure
//                    mIv1.measure(View.MeasureSpec.makeMeasureSpec(viewGroup.getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(remainHeight, View.MeasureSpec.AT_MOST));
//                    LogUtils.d(this, String.format("Iv get measured, width=%d, height=%d", mIv1.getMeasuredWidth(), mIv1.getMeasuredHeight()));
//                    setBitmap(mIv1, URL_IMG, remainWidth, remainHeight);
                    setBitmap(mIv1, URL_IMG, 200, 150);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_2:
                ToastUtils.showShort(getContext(), String.format("LruCache size=%d, maxSize=%d, use=%f", mLruCache.size(), mLruCache.maxSize(), (float) (mLruCache.size() / ((float) mLruCache.maxSize()))));
                break;
            case R.id.button_3:
                mIv1.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    private class LoadRunnable implements Runnable {
        String url;
        int reqW, reqH;

        public LoadRunnable(String url, int reqW, int reqH) {
            this.url = url;
            this.reqW = reqW;
            this.reqH = reqH;
        }

        @Override
        public void run() {
            try {
                //2.1 load use httpUrlConn
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//use byteArray to store inputStream!
                int byteSize = 0;
                final URL u = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) u.openConnection();
                httpCon.connect();
                if (httpCon.getResponseCode() == 200) {
                    LogUtils.d(this, "http connection successful");
                    InputStream is = httpCon.getInputStream();
                    byte[] buf = new byte[1024];
                    int n;
                    while ((n = is.read(buf)) != -1) {
                        baos.write(buf, 0, n);
                        byteSize += n;
                    }
                    baos.flush();

                    is.close();
                    baos.close();
                }


                //2.2 resize
                ImageResizer imageResizer = new OpxImageResize(baos.toByteArray(), byteSize);
                final Bitmap bitmap = imageResizer.resizeBitmap(reqW, reqH);

                //2.3 cache
                String key = Hash.md5(url);
                if (key != null) {
                    mLruCache.put(key, bitmap);
                }

                getBaseActivity().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mIv1.setImageBitmap(bitmap);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class OpxImageResize extends ImageResizer {
        private byte[] mBytes;
        private int mByteSize;

        public OpxImageResize(byte[] bytes, int byteSize) {
            mBytes = bytes;
            mByteSize = byteSize;
        }

        @Override
        protected Bitmap decodeBitmap(BitmapFactory.Options opt) {
            return BitmapFactory.decodeByteArray(mBytes, 0, mByteSize, opt);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
