package id.hikmah.binar.challenge5.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge5.database.User
import id.hikmah.binar.challenge5.database.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val userRepo: UserRepo, private val sharedPrefs: SharedPreferences?): ViewModel() {

    val statusUsername = MutableLiveData<Boolean>()
    val statusEmail = MutableLiveData<Boolean>()
    val statusRegistration = MutableLiveData<Boolean>()
    val statusLogin = MutableLiveData<String>()

    fun addUser(username: String, email: String, password: String) {
        var result1 = false
        var result2 = false
        viewModelScope.launch {
            val user = User(null, username, email, password)

            // Query check username & email
            val checkUsername = userRepo.cekUsername(username)
            val checkEmail = userRepo.cekEmail(email)

            if (!checkUsername.isNullOrEmpty()) { // jika ditemukan username sudah dipakai
                statusUsername.value = false
            } else {
                result1 = true
            }

            if (!checkEmail.isNullOrEmpty()) { // jika ditemukan email sudah dipakai
                statusEmail.value = false
            } else {
                result2 = true
            }

            if (result1 && result2) { // Jika username & email tersedia
                statusRegistration.value = true
                // Jalankan query insert to db
                userRepo.insertUser(user)
            } else {
                statusRegistration.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            // Query check email dan password pada db
            val checkUser = userRepo.cekUser(email, password)
            // Mencari username dari inputan login
            val getUsername = userRepo.getUsernameByMail(email)

            // Jika email dan password ditemukan pada db
            if (!checkUser.isNullOrEmpty()) {
                // Simpan username ke variabel
                val username = getUsername?.username
                // Buat editor shared preferences
                val editor = sharedPrefs?.edit()
                // Simpan ke sharedpref
                editor?.apply {
                    putString("USERNAME", username)
                    putBoolean("LOGIN_STATE", true)
                    apply()
                }
                // set statusLogin pada MutableLiveData
                statusLogin.value = "Berhasil Login"
            } else {
                statusLogin.value = "Email atau Password salah!"
            }
        }
    }

}
