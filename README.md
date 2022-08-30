[![](https://jitpack.io/v/mujjuTrootech/navigation_drawer.svg)](https://jitpack.io/#mujjuTrootech/navigation_drawer)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

# navigation_drawer_slider
Create custom navigation drawer with good animation. This is really good navigation slider drawer.

## Setup
```gradle
//project label build.gradle
buildscript {
    repositories {
         ....
        maven { url 'https://jitpack.io' }
    }
}

allprojects {
    repositories {
     .......
        maven { url 'https://www.jitpack.io' }
    }
}
//app label build.gradle
dependencies {
    implementation 'com.github.mujjuTrootech:navigation_drawer:V1'
}
```

## Usage
- Create menu.xml under your res/layout/drawer_menu
  Create slide menu view view details regarding UI.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    android:orientation="vertical">

    <Space
        android:layout_width="wrap_content"
        android:layout_height="90dp" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingStart="24dp"
        android:paddingLeft="24dp"
        android:paddingRight="24dp"
        android:text="TrooTech Solution"
        android:textStyle="bold"
        android:textColor="@color/black"
        android:textSize="14sp" />
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:baselineAligned="false"
        android:orientation="horizontal"
        android:paddingLeft="24dp"
        android:paddingRight="24dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="6dp"
            android:text="Balance Currency"
            android:textSize="12sp" />
        
    </LinearLayout>
    <Space
        android:layout_width="wrap_content"
        android:layout_height="56dp" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/list"
        android:layout_width="240dp"
        android:layout_height="wrap_content" />

    <Space
        android:layout_width="wrap_content"
        android:layout_height="40dp" />

</LinearLayout>
```
