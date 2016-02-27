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

import com.opticalix.opticalixtemplate.BuildConfig;
import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.bitmap.DiskLruCache;
import com.opticalix.opticalixtemplate.bitmap.ImageResizer;
import com.opticalix.opticalixtemplate.bitmap.OpxLruCache;
import com.opticalix.opticalixtemplate.component.base.BaseFragment;
import com.opticalix.opticalixtemplate.utils.FileUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.utils.ToastUtils;
import com.opticalix.opticalixtemplate.utils.local_log.Hash;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by opticalix@gmail.com on 16/1/12.
 */
public class DemoImageLoadFragment extends BaseFragment implements View.OnClickListener {

    public static final String URL_IMG = "http://pic87.nipic.com/file/20160107/5918804_184624870001_2.jpg";
    @Bind(R.id.button_1)
    Button mButton1;
    @Bind(R.id.button_2)
    Button mButton2;
    @Bind(R.id.button_3)
    Button mButton3;
    @Bind(R.id.iv_1)
    ImageView mIv1;
    private OpxLruCache mLruCache;
    private DiskLruCache mDiskLruCache;

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
        try {
            mDiskLruCache = DiskLruCache.open(FileUtils.getDiskCacheDir(getContext(), "bitmap"), BuildConfig.VERSION_CODE, 1, 1024 * 1024 * 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                try {
                    ViewGroup viewGroup = (ViewGroup) mIv1.getParent();
                    View btn3 = viewGroup.findViewById(R.id.button_3);
                    int remainWidth, remainHeight = 0;
                    remainWidth = viewGroup.getWidth();
                    if (btn3 != null) {
                        remainHeight = viewGroup.getHeight() - btn3.getBottom() - mIv1.getPaddingTop();
                    }

//                    //FIXME 手动measure分三种情况（对应match/wrap/exactly），这里想量match的宽高是无法实现的，因为要依赖parentW/H，而知道了parent就知道了自身应该多大了
//                    int specW = View.MeasureSpec.makeMeasureSpec(viewGroup.getWidth(), View.MeasureSpec.AT_MOST);
//                    int specH = View.MeasureSpec.makeMeasureSpec(remainHeight, View.MeasureSpec.AT_MOST);
//                    mIv1.measure(specW, specH);
//                    LogUtils.d(this, String.format("Iv get measured, width=%d, height=%d, specWidthSize=%d", mIv1.getMeasuredWidth(), mIv1.getMeasuredHeight(), View.MeasureSpec.getSize(specW)));
                    setBitmap(mIv1, URL_IMG, remainWidth, remainHeight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_2:
                ToastUtils.showLong(getContext(), String.format("LruCache size=%d, maxSize=%d, use=%f", mLruCache.size(), mLruCache.maxSize(), (float) (mLruCache.size() / ((float) mLruCache.maxSize()))));
                break;
            case R.id.button_3:
                mIv1.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    private void setBitmap(ImageView iv, String url, int reqW, int reqH) throws IOException {
        Bitmap bitmap = null;
        String key = Hash.md5(url);
        if (key != null) {
            //1. check if cached
            if ((bitmap = mLruCache.get(key+"salt")) == null && (bitmap = getBitmapFromDiskCache(key)) == null) {
                LogUtils.d(this, String.format("bitmap doesn't exist in LruCache\nurl=%s", url));

                //2. load from net
                new Thread(new LoadRunnable(url, reqW, reqH)).start();
            } else {
                //3. set Bitmap directly or send Msg
                LogUtils.d(this, String.format("bitmap find! send msg to setImage\nurl=%s", url));
                iv.setImageBitmap(bitmap);//其实这样并不是强制iv宽高，如需强制要求得设置
            }
        }
    }

    private Bitmap getBitmapFromDiskCache(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                FileInputStream fis = (FileInputStream) snapshot.getInputStream(0);

                FileDescriptor fd = fis.getFD();
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);

                if (key != null) {//cache again to mem
                    mLruCache.put(key, bitmap);
                }
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            ByteArrayOutputStream baos = null;
            InputStream is = null;
            OutputStream os = null;
            try {
                //2.1 load use httpUrlConn
                baos = new ByteArrayOutputStream();//use byteArray to store inputStream!
                int byteSize = 0;
                final URL u = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) u.openConnection();
                httpCon.connect();
                if (httpCon.getResponseCode() == 200) {
                    LogUtils.d(this, "http connection successful");
                    is = httpCon.getInputStream();
                    byte[] buf = new byte[1024];
                    int n;
                    while ((n = is.read(buf)) != -1) {
                        baos.write(buf, 0, n);
                        byteSize += n;
                    }
                    baos.flush();
                }


                //2.2 resize
                ImageResizer imageResizer = new OpxImageResize(baos.toByteArray(), byteSize);
                final Bitmap bitmap = imageResizer.resizeBitmap(reqW, reqH);

                //2.3 cache
                String key = Hash.md5(url);
                if (key != null) {
                    mLruCache.put(key, bitmap);

                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    os = editor.newOutputStream(0);
                    baos.writeTo(os);//REMEMBER baos.writeTo
                    editor.commit();
                }

                //2.4 set bitmap by handler
                getBaseActivity().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mIv1.setImageBitmap(bitmap);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                }
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
}
