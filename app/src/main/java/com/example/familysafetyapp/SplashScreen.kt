package com.example.familysafetyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		setContentView(R.layout.activity_splash_screen)

//		SharedPref.init(this)
		val isUserLogin=SharedPref.getBoolean(PrefConst.IS_USER_LOGIN)

		if (isUserLogin){
			startActivity(Intent(this, MainActivity::class.java))
			finish()
		}else{
			startActivity(Intent(this, LoginActivity::class.java))
			finish()
		}

	}
}