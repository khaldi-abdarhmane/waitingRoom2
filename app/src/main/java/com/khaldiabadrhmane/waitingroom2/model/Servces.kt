package com.khaldiabadrhmane.waitingroom2.model

import java.io.Serializable

data class Servces (val name:String,val type:String,val destripution:String,val img:String):Serializable{

    constructor(nam: String):this(nam,"","" ,"")
    constructor(name1: String,type1: String,destripution1: String,):this(name1,type1,destripution1,"")
    constructor():this("","","","")

}