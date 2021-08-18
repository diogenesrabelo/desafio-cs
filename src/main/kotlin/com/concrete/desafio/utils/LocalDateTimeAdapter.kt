package com.concrete.desafio.utils

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


internal class LocalDateTimeAdapter : JsonSerializer<LocalDateTime?> {

    override fun serialize(date: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (date != null) {
            return JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        }
        else{
            val date1 = LocalDateTime.now()
            return return JsonPrimitive(date1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        }
    }
}