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
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lt.SharedPreferences
import com.lt.ValueHandler

@Composable
@Preview
fun App() {
    val sp = remember {
        SharedPreferences("sp", valueHandler = object : ValueHandler {
            override fun convertTo(value: String): String = "111$value"

            override fun convertBack(convertedValue: String): String = convertedValue.substring(3)
        }, valueSaved = {
            ThreadPool.submitToSingleThreadPool {
                it()
            }
        })
    }
    var text by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            Button(onClick = {
                text = sp.getString("test", "mKey", "no data")
            }) {
                Text("读取配置")
            }
            TextField(text, { text = it })
            Button(onClick = {
                sp.putString("test", "mKey", text)
            }) {
                Text("写入配置")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
