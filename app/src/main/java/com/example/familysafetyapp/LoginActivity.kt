package com.example.familysafetyapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

//	lateinit var  sharedPreferences : SharedPreferences
//	val MyPREFERENCES = "MyPrefs"

	val permission = arrayOf(
		android.Manifest.permission.ACCESS_FINE_LOCATION,
		android.Manifest.permission.READ_CONTACTS)

	val permissionCode = 1


	private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
	private lateinit var auth: FirebaseAuth
	private lateinit var googleSiginClient: GoogleSignInClient


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		val googleSignInBtn = findViewById<Button>(R.id.googleBtn)
		auth = FirebaseAuth.getInstance()
		askForPermission()

//		sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_we_client))
			.requestEmail()
			.build()

		googleSiginClient = GoogleSignIn.getClient(this, gso)


		googleSignInBtn.setOnClickListener(View.OnClickListener {

			SiginGoogle()

		})
	}


	private fun SiginGoogle() {
		val signInIntent = googleSiginClient.signInIntent
//		startActivityForResult(signInIntent, REQ_ONE_TAP)
		launcher.launch(signInIntent)
	}

	private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode == Activity.RESULT_OK) {
				val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
				handleResults(task)
			}
		}

	private fun handleResults(task: Task<GoogleSignInAccount>) {
		if (task.isSuccessful) {

//			SharedPref.init(this)
			SharedPref.putBoolean(PrefConst.IS_USER_LOGIN,true)

			val account: GoogleSignInAccount? = task.result
			if (account != null) {
				updateUI(account)
			}
		} else {
			Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
		}
	}

	private fun updateUI(account: GoogleSignInAccount) {
		val credential = GoogleAuthProvider.getCredential(account.idToken, null)
		auth.signInWithCredential(credential).addOnCompleteListener {
			if (it.isSuccessful) {

				SharedPref.putBoolean(PrefConst.IS_USER_LOGIN,true)

				val intent: Intent = Intent(this, MainActivity::class.java)
				intent.putExtra("email", account.email)
				intent.putExtra("name", account.displayName)
				startActivity(intent)
				Toast.makeText(this, "" + account.email, Toast.LENGTH_SHORT).show()
				Toast.makeText(this, "" + account.displayName, Toast.LENGTH_SHORT).show()
			} else {
				Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
			}
		}
	}
	private fun askForPermission() {
		ActivityCompat.requestPermissions(this, permission, permissionCode)
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == permissionCode) {
			if (requestPermissionGranted()) {

			} else {

			}
		}
	}

	private fun requestPermissionGranted(): Boolean {
		for (item in permission){
			if (ActivityCompat.checkSelfPermission(this,item)!= PackageManager.PERMISSION_GRANTED){
				return false
			}
		}
		return true

	}

	/*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode == REQ_ONE_TAP) {
			val task = GoogleSignIn.getSignedInAccountFromIntent(data)
			try {
				val account = task.getResult(ApiException::class.java)
				firebaseAuthWithGoogle(account.idToken!!)
			} catch (e: ApiException) {
				Log.w("user", "google sigin failed", e)
			}
		}
	}*/


	/*private fun firebaseAuthWithGoogle(idToken: String) {
		*//*val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
		val idToken = googleCredential.googleIdToken*//*
		when {
			idToken != null -> {
				// Got an ID token from Google. Use it to authenticate
				// with Firebase.
				val auth=FirebaseAuth.getInstance()
				val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
				auth.signInWithCredential(firebaseCredential)
					.addOnCompleteListener(this) { task ->
						if (task.isSuccessful) {
							// Sign in success, update UI with the signed-in user's information
							Log.d("user", "signInWithCredential:success")
							val user = auth.currentUser

							startActivity(Intent(this,MainActivity::class.java))
							Log.d("user", "firebaseAuthWithGoogle: ${user?.displayName}")
						} else {
							// If sign in fails, display a message to the user.
							Log.d("user", "signInWithCredential:failure", task.exception)
						}
					}
			}
			else -> {
				// Shouldn't happen.
				Log.d("user", "No ID token!")
			}
		}
	}*/


}


