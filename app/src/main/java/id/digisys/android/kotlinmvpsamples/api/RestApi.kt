package id.digisys.android.kotlinmvpsamples.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestApi{
    companion object {
        fun theSportDb() : Retrofit{
            return Retrofit.Builder()
                    .baseUrl("https://www.thesportsdb.com/api/v1/json/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }
}