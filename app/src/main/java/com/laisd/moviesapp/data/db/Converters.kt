package com.laisd.moviesapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

//    @TypeConverter
//    fun toCastMemberList(value: String): List<CastMember> {
//        val gson = Gson()
//        val type = object: TypeToken<List<CastMember>>() {}.type
//        return gson.fromJson(value, type)
//    }

    @TypeConverter
    fun restoreList(listOfString: String?): List<String?>? {
        return Gson().fromJson<List<String>>(
            listOfString,
            object : TypeToken<List<String?>?>() {}.type
        )
    }

    @TypeConverter
    fun saveList(listOfString: List<String?>?): String? {
        return Gson().toJson(listOfString)
    }


}