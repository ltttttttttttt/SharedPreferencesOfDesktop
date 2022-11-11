# SharedPreferencesOfDesktop

Desktop中的SharedPreferences,适用于Kotlin,用于在Windows,Linux,MacOs中简单的存取配置文件

# 如何使用

1. 在你根目录下的build.gradle.kts中添加:

```kotlin
buildscript {
    repositories {
        maven("https://jitpack.io")//this
        ...
    }
}

allprojects {
    repositories {
        maven("https://jitpack.io")//this
        ...
    }
}
```

2. 在你的Compose-jb项目的代码目录(一般是desktop)下的build.gradle.kts中添加:

version
= [![](https://jitpack.io/v/ltttttttttttt/SharedPreferencesOfDesktop.svg)](https://jitpack.io/#ltttttttttttt/SharedPreferencesOfDesktop)

```kotlin
kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                ...
                implementation("com.github.ltttttttttttt:SharedPreferencesOfDesktop:$version")//this
            }
        }
    }
}
```

3. 使用SharedPreferences

```kotlin
//创建sp对象
val sp = SharedPreferences("appName")
//获取string值
sp.getStringOrNull("fileName", "key")
//设置string值
sp.putString("fileName", "key", "value")
```

ps:不止支持string类型,也支持Int,Long,Boolean,Float类型

4. 自定义配置,在创建SharedPreferences对象的时候可以配置以下内容

```kotlin
/**
 * @param appName 应用名
 * @param configDir 配置文件夹
 * @param valueHandler 处理value值,可以用作加解密等
 * @param valueSaved 保存值时的方法,一般用来将保存的任务放到单例子线程中执行
 * @param lruMaxSize LruMap最多保存多少条数据(多少个文件里的数据)
 */
class SharedPreferences()
```