package com.lt

/**
 * creator: lt  2022/11/11  lt.dygzs@qq.com
 * effect : 处理value值,可以用作加解密等
 * warning:
 */
interface ValueHandler {
    /**
     * 将真实的值转换为你想存储的值,比如加密
     */
    fun convertTo(value: String): String

    /**
     * 将存储的值转换为真实的值,比如解密
     */
    fun convertBack(convertedValue: String): String
}

//不处理
object NoValueHandler : ValueHandler {
    override fun convertTo(value: String): String = value

    override fun convertBack(convertedValue: String): String = convertedValue
}