package com.lt

import java.io.File

/**
 * creator: lt  2022/11/11  lt.dygzs@qq.com
 * effect :
 * warning:
 */

/**
 * 获取user目录
 */
internal fun getUserDir(): String = System.getProperty("user.home")

/**
 * 获取默认的配置目录
 */
internal fun getConfigDir(): String = getUserDir() + File.separator + ".config"