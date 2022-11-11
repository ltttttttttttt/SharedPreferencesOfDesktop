// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
