package com.example.reactivexretrofit.retrofit

import com.example.reactivexretrofit.model.Person
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
        @GET(".")
        fun getPerson(@Query("name")name: String) : Observable<Person>
}