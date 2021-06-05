package com.example.freespace
data class Place(var pName:String, var pMaxNum:Int, var pNum:Int, var pInfo:String){
    constructor():this("noinfo",0, 0, "noinfo")
}