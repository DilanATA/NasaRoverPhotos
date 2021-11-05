package com.dilanata.nasaroverphotos.helper

import android.graphics.pdf.PdfDocument
import com.dilanata.nasaroverphotos.util.Constants.API_KEY

fun queryMap(page: Int) : Map<String, String>{
    val map = HashMap<String, String>()
    map["sol"] = "1000"
    map["page"] = page.toString()
    map["api_key"] = API_KEY
    return map
}

fun queryMapByCamera(page: Int, camera: String) : Map<String, String>{
    val map = HashMap<String, String>()
    map["sol"] = "1000"
    map["page"] = page.toString()
    map["api_key"] = API_KEY
    map["camera"] = camera
    return map
}