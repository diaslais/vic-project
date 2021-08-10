package com.laisd.moviesapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.laisd.moviesapp.domain.model.CastMember

class Converters {

    @TypeConverter
    fun listOfStringToString(listOfString: List<String>): String {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun stringToListOfString(string: String): List<String> {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, type)
    }


    @TypeConverter
    fun listOfCastMemberToString(cast: List<CastMember>): String {
        return Gson().toJson(cast)
    }

    @TypeConverter
    fun stringToListOfCastMember(string: String): List<CastMember> {
        val type = object: TypeToken<List<CastMember>>() {}.type
        return Gson().fromJson(string, type)
    }
}