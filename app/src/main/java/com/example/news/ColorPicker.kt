package com.example.news
object ColorPicker{
    val colors = arrayOf("#c5cae9","#f50057","#e040fb","#bdbdbd","#ff8a65","#009688","#ff80ab","#e91e63","#9575cd")
    var colorIndex = 1
    fun getColor():String{
     return colors[colorIndex++ % colors.size]
    }
}
