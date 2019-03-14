package com.emilda.dplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.emilda.dplayer.Reciever.ConnectivityReceiverLive
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class splash : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private  var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        internet()


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
               // authentication()
            } else  {
                //finish()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        if (mAuthStateListener!==null)
        firebaseAuth?.removeAuthStateListener(mAuthStateListener!!)
    }

    override fun onResume() {
        super.onResume()
        if(mAuthStateListener!=null)
            firebaseAuth?.addAuthStateListener(mAuthStateListener!!)

    }

    fun LoginActivity() {
                //user signed
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

    private fun internet() {
        val connectionLiveData = ConnectivityReceiverLive(application)
        connectionLiveData.observe(this, Observer {
            if (it) {
                Log.d("called","sam")
                authentication()
                firebaseAuth?.addAuthStateListener(mAuthStateListener!!)

            } else
                Toast.makeText(this, "Connection Lost", Toast.LENGTH_LONG).show()
        })
    }

    private fun authentication(){
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            var user = it.currentUser
            if (user != null) {
                var newuser: Boolean = user.metadata?.creationTimestamp == user.metadata?.lastSignInTimestamp

                var intent = Intent(this, detailsActivity::class.java).apply {
                    putExtra("USER_ID", user.uid)
                    putExtra("NEW_USER", newuser)
                }
                startActivity(intent)
                finish()
            } else {
                LoginActivity()
            }
        }
    }

}
