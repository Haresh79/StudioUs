package com.example.studious.models

import java.security.Timestamp

class Post {
    var image:String?=null
    var caption: String? =null
    var like:Int=0
    var idName:String?=null
    var profile:String?=null
    var timeStamp:String?=null
    constructor()
    constructor(image: String?, caption: String?) {
        this.image = image
        this.caption = caption
    }

    constructor(image: String?, caption: String?, like: Int, idName: String?, profile: String?,timeStamp:String? ) {
        this.image = image
        this.caption = caption
        this.like = like
        this.idName = idName
        this.profile = profile
        this.timeStamp=timeStamp
    }


}