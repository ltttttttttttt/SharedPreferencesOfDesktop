# SharedPreferencesOfDesktop

SharedPreferences in Desktop, suitable for Kotlin, for simple access to configuration files in Windows, Linux, MacOs

<div align="center">us English | <a href="https://github.com/ltttttttttttt/SharedPreferencesOfDesktop/blob/main/README_CN.md">cn 简体中文</a></div>

# Add to your project

1. Your root dir, build.gradle.kts add:

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

2. Your desktop dir, build.gradle.kts add:

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

3. Use SharedPreferences

```kotlin
//Create SharedPreferences
val sp = SharedPreferences("appName")
//Get String value
sp.getStringOrNull("fileName", "key")
//Set String value
sp.putString("fileName", "key", "value")
```

ps:Support String, Int, Long, Boolean, Float

4. Custom Configuration, When creating a SharedPreferences object, the following content can be configured

```kotlin
/**
 * @param appName Application Name
 * @param configDir Configure dir
 * @param valueHandler Process value values, which can be used for encryption and decryption, etc
 * @param valueSaved The method of saving values is generally used to place the saved task into a single instance thread for execution
 * @param lruMaxSize How many pieces of data can LruMap save at most (data from multiple files)
 */
class SharedPreferences()
```
