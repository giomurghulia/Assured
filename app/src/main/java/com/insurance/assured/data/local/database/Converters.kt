package com.insurance.assured.data.local.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringToStringList(str: String): List<String> = str.split("|")

    @TypeConverter
    fun fromStringListToString(list: List<String>): String = list.joinToString("|")
}