# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\arnal\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# --- ExoApp ---
# Conservar nombres de archivo y lineas para que el stacktrace de release sea legible
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# SQLiteAssetHelper (copia la BD desde assets) y nuestras clases de BD
-keep class com.readystatesoftware.sqliteasset.** { *; }
-keep class com.exoapp.basededatos.** { *; }

# AdMob
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.**

# WorkManager / Room (los trae play-services-ads). Room crea WorkDatabase_Impl por reflexion
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-keep class androidx.work.impl.WorkDatabase_Impl { *; }
-keep class androidx.work.impl.** { *; }
-keep class androidx.work.** { *; }
-keep class androidx.room.** { *; }
-dontwarn androidx.work.**
-dontwarn androidx.room.**
