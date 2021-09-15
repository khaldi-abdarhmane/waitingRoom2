package com.khaldiabadrhmane.waitingroom2.model

data class User(val name:String,val pathimage:String,val type:String){

    constructor():this("","","client")
    constructor(nam: String) : this(nam,"","client")
}