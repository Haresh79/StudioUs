package com.example.studious.models

import android.media.Image

class User {
    var image: String? =null
    var Uname:String?=null
    var Uclass:String?=null
    var Email:String?=null
    var Pass:String?=null
    constructor()
    constructor( Uname:String?, Uclass:String?, Email:String?, Pass:String?){
        this.Uname=Uname
        this.Pass=Pass
        this.Uclass=Uclass
        this.Email=Email
    }

    constructor(Email: String?, Pass: String?) {
        this.Email = Email
        this.Pass = Pass
    }


}