package com.example.familysafetyapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.familysafetyapp.DashboardFragment.Companion.newInstance
import com.example.familysafetyapp.HomeFragment.Companion.newInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.cert.TrustAnchor
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val bottombar = findViewById<BottomNavigationView>(R.id.bottom_nav)


		//navigate between fragments
		bottombar.setOnItemSelectedListener {
			when (it.itemId) {
				R.id.home -> {
					inflateFragment(HomeFragment.newInstance())
				}
				R.id.guard -> {
					inflateFragment(GuardFragment.newInstance())
				}
				R.id.dashboard -> {
					inflateFragment(MapsFragment())
				}
				else -> {
					inflateFragment(ProfileFragment.newInstance())
				}
			}


			true
		}
		//default fragment
		bottombar.selectedItemId = R.id.home
	}




	private fun inflateFragment(newInstance: Fragment) {
		val transaction = supportFragmentManager.beginTransaction()
		transaction.replace(R.id.container_frame, newInstance)
		transaction.commit()
	}


}