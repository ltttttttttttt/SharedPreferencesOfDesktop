package com.lt

/**
 * creator: lt  2022/11/11  lt.dygzs@qq.com
 * effect : 保存值时的方法,一般用来将保存的任务放到单例子线程中执行
 * warning:
 */
fun interface ValueSaved {
    fun valueSaved(onSave: () -> Unit)
}

//在当前线程保存数据
object CurrThreadValueSaved : ValueSaved {
    override fun valueSaved(onSave: () -> Unit) {
        onSave()
    }
}