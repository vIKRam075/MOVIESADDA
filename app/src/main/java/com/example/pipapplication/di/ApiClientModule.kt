package com.example.pipapplication.di

import androidx.viewbinding.BuildConfig
import com.example.pipapplication.data.api.ApiService
import com.example.pipapplication.data.api.AuthInterceptor
import com.example.pipapplication.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiClientModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().client(getApiClient())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
//
    @Singleton
    @Provides
    fun getApiClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        /*return OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()*/
        val builder = OkHttpClient().newBuilder()
//        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .addInterceptor(AuthInterceptor())
//                .addNetworkInterceptor(StethoInterceptor())
        } else {
            builder.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(AuthInterceptor())
//                .addNetworkInterceptor(StethoInterceptor())
        }
        return builder.build()

    }


//    val apiInterface: ApiService = getRestApiService().create(ApiService::class.java)

    @Singleton
    @Provides
     fun getApiService(retrofit: Retrofit): ApiService {
         return retrofit.create(ApiService::class.java)
     }
}