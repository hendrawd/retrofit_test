# Retrofit VS Modified Volley

I have read many articles about both Volley vs Retrofit, but most of them are obsolete, so i decided to create a project to test them directly.

# Phone model used
One Plus One(A001)

# Android version used
CyanogenMod version 14.1-20161123-NIGHTLY-bacon

Marshmallow 7.1

## Library used

### Retrofit side
```
compile 'com.google.code.gson:gson:2.6.2'
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```
### Volley side
```
compile 'com.android.volley:volley:1.0.0'
compile 'com.alibaba:fastjson:1.1.55.android'
compile 'com.squareup.okhttp3:okhttp:3.5.0'
```
## End point used
https://api.github.com/users/{user}/repos

## Test Results
From 10 tries

***Average Execution Time for Retrofit : 1279 ms***

***Average Execution Time for Volley : 1572 ms***

But wait!

That is the result if i clear the application data or un-install the application before re-try the request.

If i don't do above action, Volley will win. Volley will cache our response in memory by default, while retrofit uses disk cache, so it will be faster if re-used. The execution time will drop to average 164 ms!

## Conclusion
Retrofit has lower execution time, but with some modifications, Volley can still compete with Retrofit. Volley has memory cache mechanism by default too that makes it faster for loading the same request. So which one should i use? It is up to you and depends on your requirements. Volley is easier to modif but harder to implement, while retrofit is easier to implement but harder to modif.

More links:

https://medium.com/@ali.muzaffar/is-retrofit-faster-than-volley-the-answer-may-surprise-you-4379bc589d7c
