package com.emilda.dplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class splash : BaseActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN = 123
    private var userID ="null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Firebase

        firebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            var user = it.currentUser
            if (user != null) {
                var newuser:Boolean = user.metadata?.creationTimestamp == user.metadata?.lastSignInTimestamp

                var intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_ID", user.uid)
                    putExtra("NEW_USER",newuser)
                }
                startActivity(intent)
                finish()
            } else {
                //user signed out

                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.PhoneBuilder().build()
                            )
                        ).build()
                    , RC_SIGN_IN
                )

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT)
            } else {
                finish()
            }
        }
        Log.d("sample", "Sign In Successfull")
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(mAuthStateListener)
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    fun sample() {
        firebaseAuth.currentUser?.metadata
    }

}
