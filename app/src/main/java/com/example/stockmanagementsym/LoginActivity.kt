package com.example.stockmanagementsym

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanagementsym.presentation.AndroidModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private var account: GoogleSignInAccount ?= null
    private lateinit var androidModel:AndroidModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        androidModel = AndroidModel()

        buttonLogin.setOnClickListener  {
            showLoading(true)
            GlobalScope.launch(Dispatchers.IO){
                val userName = editTextUser.text.toString()
                val password = editTextPass.text.toString()
                if(userName.isEmpty()||password.isEmpty()){
                    androidModel.showToastMessage(getString(R.string.loginFailure) + ". " + getString(R.string.voidData),context)
                    (context as LoginActivity).runOnUiThread {
                        showLoading(false)
                    }
                }else{
                    androidModel.setGoogleAccount(null)
                    androidModel.setGoogleSingInClient(null)
                    login(userName, password)
                }
            }
        }
        configureGoogleClient()
        account = GoogleSignIn.getLastSignedInAccount(this)
        buttonLoginGoogle.setOnClickListener {
            showLoading(true)
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 50)
        }
        buttonRegister.setOnClickListener {
            showLoading(true)
            GlobalScope.launch(Dispatchers.IO){
                val userName = editTextUser.text.toString()
                val password = editTextPass.text.toString()
                if(userName.isEmpty()||password.isEmpty()){
                    androidModel.showToastMessage(getString(R.string.loginFailure) + ". " + getString(R.string.voidData),context)
                    (context as LoginActivity).runOnUiThread {
                        showLoading(false)
                    }
                }else{
                    androidModel.setGoogleAccount(null)
                    androidModel.setGoogleSingInClient(null)
                    register(userName, password)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==50){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                account = task.result
                androidModel.setGoogleAccount(account)
                androidModel.setGoogleSingInClient(googleSignInClient)

                GlobalScope.launch(Dispatchers.IO){
                    login(account!!.displayName.toString(), account!!.id.toString())
                }

            }catch (e: Exception){
                androidModel.showAlertMessage(R.string.loginFailure,R.string.loginFailure,context)
                showLoading(false)
            }
        }
    }


    private suspend fun login(user: String, password: String) {
        showLoading(false)
        androidModel.confirmLogin(this, user, password)
    }

    private suspend fun register(user: String, password: String) {
        val login = this
        withContext(Dispatchers.IO){
            try{
                androidModel.register(login, user, password)
                showLoading(false)
                androidModel.showAlertMessage(R.string.titleUserRegistered,R.string.messageUserRegistered,login)
            }catch (e:Exception){
                showLoading(false)
                androidModel.showAlertMessage(R.string.titleUserRegistered,R.string.messageUserNotRegistered,login)
            }
        }
    }

    private fun configureGoogleClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .requestId()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    private fun showLoading(booleanLoading: Boolean){
        if(booleanLoading){
            this.runOnUiThread {
                progressBar.visibility = View.VISIBLE
                buttonLogin.isEnabled = false
                buttonLoginGoogle.isEnabled = false
            }
        }else{
            this.runOnUiThread {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = true
                buttonLoginGoogle.isEnabled = true
            }
        }
    }
}