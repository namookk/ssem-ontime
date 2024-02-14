package com.hanssem.ontime.payload

enum class YN {
    Y, N;

    companion object{
        fun toYN(flag: Boolean): YN {
            if(flag) {
                return YN.Y
            }
            return YN.N
        }

        fun toBoolean(yn: YN): Boolean {
            return YN.Y == yn
        }
    }
}