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