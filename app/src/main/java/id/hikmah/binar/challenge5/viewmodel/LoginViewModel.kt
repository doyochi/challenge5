package id.hikmah.binar.challenge5.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge5.database.UserRepo
import kotlinx.coroutines.launch

class LoginViewModel (private val userRepo: UserRepo, private val sharedPref: SharedPreferences?): ViewModel() {

    val statusLogin = MutableLiveData<Boolean>()
    val username = MutableLiveData<String>()
    val userid = MutableLiveData<Int>()
    val useremail = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val isLogin = userRepo.isLogin(email, password)
            val getUsername = userRepo.getUsernameByMail(email)

            if (!isLogin.isNullOrEmpty){
                val username = getUsername?.username
                val editor = sharedPref?.edit()
                editor?.apply {
                    putString("USERNAME", username)
                    putBoolean("LOGIN_STATE", true)
                    apply()
                }
                // set statusLogin pada MutableLiveData
                statusLogin.value = ""
            } else {
                statusLogin.value = "Email atau Password salah!"
            }
        }
    }

}