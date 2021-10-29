package com.example.news

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(){
      lateinit var adapter: NewsListAdapter
      private var article = mutableListOf<Article>()
   lateinit var newsList:RecyclerView
   lateinit var container:RelativeLayout
   var pageNum = 1
   // val TAG = "MainActivity"
    var totalResults = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsList = findViewById(R.id.newsList)
        container= findViewById(R.id.container)

        adapter = NewsListAdapter(this@MainActivity,article)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object :StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
               // Log.d(TAG,"First Visible item -${layoutManager.getFirstVisibleItemPosition()}")
               // Log.d(TAG,"Total count -${layoutManager.itemCount}")
                if(totalResults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount -5)
                {
                    pageNum ++
                    getNews()
                }
            }
        })
        newsList.layoutManager = layoutManager
        getNews()
    }
    private fun getNews() {
       val news = NewsService.newsInstant.getHeadlines("in",pageNum)
        news.enqueue(object : retrofit2.Callback<News> {
            override fun onResponse(call: retrofit2.Call<News>, response: Response<News>) {
                 val news = response.body()
                if (news!= null){
                  totalResults = news.totalResults
                    Log.d("Milan",news.toString())
                    article.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: retrofit2.Call<News>, t: Throwable) {
                Log.d("Milan","Error in Fetching News",t)
            }
        })
    }
}