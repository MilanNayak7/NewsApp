package com.example.news

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&apiKey=API_KEY
//https://newsapi.org/v2/everything?domains=wsj.com&apiKey=API_KEY
//react
//mongo db
const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "d5a82e8ff0064231bd6500ba039c7289"
interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country:String,@Query("page")page:Int): Call<News>

}
object NewsService{ //Single tone object
    val newsInstant: NewsInterface
    init {
        val retrofit  = Retrofit.Builder()      // This is our retrofit object
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstant = retrofit.create(NewsInterface::class.java)
    }
}