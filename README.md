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

    ...................
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

* Set the content view (for example, using setContentView in your activity).
* Create your array.xml (example) or construct a View programmatically. Under array.xml set title and drawer image.


-Initialization root view and access all filed.
```
slidingRootNav =  SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)//If first time load screen at a time menu open required so set tru values
                .withContentClickableWhenMenuOpened(true)//if needed after open navigation drawer back menu clicked drawer closed so values add true.
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();
```

-Menu behavior
```
new SlidingRootNavBuilder(this)
.withMenuOpened(true) //Initial menu opened/closed state. Default == false
.withMenuLocked(false) //If true, a user can't open or close the menu. Default == false.
.withGravity(SlideGravity.LEFT) //If LEFT you can swipe a menu from left to right, if RIGHT - the direction is opposite.
.withSavedState(savedInstanceState) //If you call the method, layout will restore its opened/closed state
.withContentClickableWhenMenuOpened(isClickable) //Pretty self-descriptive. Builder Default == true
```