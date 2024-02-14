package com.hanssem.ontime.payload.mapper.util

import com.hanssem.ontime.payload.YN
import org.mapstruct.Qualifier

class EnumYNMapper {

    @Qualifier
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(AnnotationRetention.BINARY)
    annotation class ToBoolean

    @Qualifier
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(AnnotationRetention.BINARY)
    annotation class ToYN

    @ToBoolean
    fun toBoolean(yn: YN): Boolean {
        return yn === YN.Y
    }

    @ToYN
    fun toYN(bool: Boolean): YN {
        return if (bool) YN.Y else YN.N
    }
}