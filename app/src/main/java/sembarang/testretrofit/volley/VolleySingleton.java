package sembarang.testretrofit.volley;

/**
 * Copyright (C) 2016 hendrawd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;
    //private ImageLoader.ImageCache mImageCache;
    private OkHttp3Stack mOkHttp3Stack;
    private static final String TAG = VolleySingleton.class.getSimpleName();

    private VolleySingleton(Context context) {
        //use okhttp stack
        mOkHttp3Stack = new OkHttp3Stack();
        mRequestQueue = Volley.newRequestQueue(context, mOkHttp3Stack);

        //default
        //mRequestQueue = Volley.newRequestQueue(context);

        //initiateImageLoader();
    }

//    private void initiateImageLoader(){
//        mImageCache = new ImageLoader.ImageCache() {
//            // int cacheSize = 1024 * 1024 * 10;
//            private final BitmapLruCache mCache = new BitmapLruCache();
//
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//        };
//        mImageLoader = new ImageLoader(this.mRequestQueue, mImageCache);
//    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        return this.mImageLoader;
//    }

//    public ImageLoader.ImageCache getImageCache() {
//        return this.mImageCache;
//    }

    /**
     * Helper classes
     */
    public <T> void addToRequestQueue(Request<T> req, Object tag) {
        // set the default tag if tag is empty
        req.setTag(tag == null ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
            mOkHttp3Stack.cancelRequestOnGoing(tag);
        }
    }

    public void cancelPendingRequestsNoTag() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
            mOkHttp3Stack.cancelRequestOnGoing(TAG);
        }
    }

    public void clearVolleyCache() {
        if (mRequestQueue != null) {
            mRequestQueue.getCache().clear();
        }
    }
    /**
     * End helper classes
     */
}