package com.example.freespace
data class Place(var pName:String, var pMaxNum:Int, var pNum:Int, var pInfo:String, var bookmark:Boolean, var bookmarkSetting:Boolean){
    constructor():this("noinfo",0, 0, "noinfo", false, false)
}