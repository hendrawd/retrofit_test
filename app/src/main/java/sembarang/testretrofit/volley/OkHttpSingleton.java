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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpSingleton {

    private OkHttpClient mOkHttpClient;

    private static OkHttpSingleton ourInstance;

    public static OkHttpSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new OkHttpSingleton();
        }
        return ourInstance;
    }

    private OkHttpSingleton() {
        OkHttpClient.Builder builderOkhttp = new OkHttpClient.Builder();
        builderOkhttp.connectTimeout(20, TimeUnit.SECONDS);
        builderOkhttp.readTimeout(15, TimeUnit.SECONDS);
        builderOkhttp.writeTimeout(15, TimeUnit.SECONDS);
        builderOkhttp.addInterceptor(getInterceptor());
        mOkHttpClient = builderOkhttp.build();
        setOkHttpClient(mOkHttpClient);
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }
}