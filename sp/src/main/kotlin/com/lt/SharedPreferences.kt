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

import com.lt.data_structure.lru.LruMapWithGetFirst
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

/**
 * creator: lt  2022/11/11  lt.dygzs@qq.com
 * effect : 简单的存取配置文件
 * warning:
 * @param appName 应用名
 * @param configDir 配置文件夹
 * @param valueHandler 处理value值,可以用作加解密等
 * @param valueSaved 保存值时的方法,一般用来将保存的任务放到单例子线程中执行
 * @param lruMaxSize LruMap最多保存多少条数据(多少个文件里的数据)
 */
class SharedPreferences(
    internal val appName: String,
    configDir: String = getConfigDir(),
    internal val valueHandler: ValueHandler = NoValueHandler,
    internal val valueSaved: ValueSaved = CurrThreadValueSaved,
    lruMaxSize: Int = 5,
) {
    internal val configDir = File(configDir, appName)//配置保存目录
    internal val jsonLibrary = Json//json解析
    internal val cache = LruMapWithGetFirst<String, MutableMap<String, String?>>(lruMaxSize)//存放文件数据的LruMap,暂定最多能存放五个文件

    fun getString(fileName: String, key: String, defaultValue: String): String =
        getStringOrNull(fileName, key) ?: defaultValue

    /**
     * 获取String类型的值,如果不存在则返回null
     */
    fun getStringOrNull(fileName: String, key: String): String? {
        val value = cache.getOrPut(fileName) {
            getOrCreateValues(getAndCheckFile(fileName))
        }[key] ?: return null
        return valueHandler.convertBack(value)
    }

    /**
     * 提交String类型的值
     */
    fun putString(fileName: String, key: String, value: String) {
        val file = getAndCheckFile(fileName)
        val values = cache.getOrPut(fileName) {
            getOrCreateValues(file)
        }
        values[key] = valueHandler.convertTo(value)
        saveValuesToFile(values, file)
    }

    fun getInt(fileName: String, key: String, defaultValue: Int): Int = getIntOrNull(fileName, key) ?: defaultValue
    fun getIntOrNull(fileName: String, key: String): Int? = getStringOrNull(fileName, key)?.toInt()
    fun putInt(fileName: String, key: String, value: Int) {
        putString(fileName, key, value.toString())
    }

    fun getLong(fileName: String, key: String, defaultValue: Long): Long = getLongOrNull(fileName, key) ?: defaultValue
    fun getLongOrNull(fileName: String, key: String): Long? = getStringOrNull(fileName, key)?.toLong()
    fun putLong(fileName: String, key: String, value: Long) {
        putString(fileName, key, value.toString())
    }

    fun getBoolean(fileName: String, key: String, defaultValue: Boolean): Boolean =
        getBooleanOrNull(fileName, key) ?: defaultValue

    fun getBooleanOrNull(fileName: String, key: String): Boolean? = getStringOrNull(fileName, key)?.toBoolean()
    fun putBoolean(fileName: String, key: String, value: Boolean) {
        putString(fileName, key, value.toString())
    }

    fun getFloat(fileName: String, key: String, defaultValue: Float): Float =
        getFloatOrNull(fileName, key) ?: defaultValue

    fun getFloatOrNull(fileName: String, key: String): Float? = getStringOrNull(fileName, key)?.toFloat()
    fun putFloat(fileName: String, key: String, value: Float) {
        putString(fileName, key, value.toString())
    }

    /**
     * 删除指定文件中的指定key
     */
    fun removeKey(fileName: String, key: String) {
        val file = getAndCheckFile(fileName)
        val values = cache.getOrPut(fileName) {
            getOrCreateValues(file)
        }
        values.remove(key)
        saveValuesToFile(values, file)
    }

    /**
     * 删除指定文件
     */
    fun removeFile(fileName: String) {
        cache.remove(fileName)
        val file = getAndCheckFile(fileName)
        if (file.isFile)
            file.delete()
    }

    //获取并检查指定的配置文件
    private fun getAndCheckFile(fileName: String): File {
        val file = File(configDir, fileName)
        if (!configDir.isDirectory)
            configDir.mkdirs()
        if (!file.isFile)
            file.createNewFile()
        return file
    }

    //创建或获取本地的values
    @Synchronized
    private fun getOrCreateValues(file: File): MutableMap<String, String?> {
        val json = file.readText()
        if (json.isEmpty())
            return Collections.synchronizedMap(HashMap())
        return try {
            Collections.synchronizedMap(jsonLibrary.decodeFromString<HashMap<String, String?>>(json))
        } catch (e: Exception) {
            e.printStackTrace()
            Collections.synchronizedMap(HashMap())
        }
    }

    //将数据保存至本地
    @Synchronized
    private fun saveValuesToFile(values: MutableMap<String, String?>, file: File) {
        valueSaved.valueSaved {
            synchronized(this@SharedPreferences) {
                try {
                    val json = jsonLibrary.encodeToString(values)
                    file.writeText(json)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
