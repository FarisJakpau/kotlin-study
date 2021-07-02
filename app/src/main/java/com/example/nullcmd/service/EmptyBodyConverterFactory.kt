package com.example.nullcmd.service

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * this class is to handle empty response when response.isSuccessful
 */
class EmptyBodyConverterFactory : Converter.Factory()  {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = Converter<ResponseBody, Any> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(this, type, annotations)
        if (it.contentLength() != 0L ) nextResponseBodyConverter.convert(it) else null
    }
}