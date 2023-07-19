package com.example.familysafetyapp

import android.app.Application

class MyFamilyApp:Application() {

	override fun onCreate() {
		super.onCreate()
		SharedPref.init(this)
	}
}