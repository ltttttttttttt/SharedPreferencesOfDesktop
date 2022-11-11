/*
 * Copyright lt 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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