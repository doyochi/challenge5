package id.hikmah.binar.challenge5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge5.database.UserEntity
import id.hikmah.binar.challenge5.database.UserRepo
import kotlinx.coroutines.launch

class RegisterViewModel (private val userRepo: UserRepo): ViewModel() {

    val isRegist = MutableLiveData<Boolean>()
    val emailIsRegist = MutableLiveData<Boolean>()
    val userIsRegist = MutableLiveData<Boolean>()

    fun addUserToDb(username: String, email: String, user: UserEntity) {
        var result1 = false
        var result2 = false

        viewModelScope.launch {

            // Query check username & email
            val checkUsername = userRepo.checkRegisteredkUsername(username)
            val checkEmail = userRepo.checkRegisteredEmail(email)

            if (!checkUsername.isNullOrEmpty()) { // jika ditemukan username sudah dipakai
                userIsRegist.value = true
            } else {
                result1 = true
            }

            if (!checkEmail.isNullOrEmpty()) { // jika ditemukan email sudah dipakai
                emailIsRegist.value = true
            } else {
                result2 = true
            }
// Jika username & email tersedia
            if (result1 && result2) {
                isRegist.value = true
                // Jalankan query insert to db
                userRepo.insertUser(user)
            } else {
                isRegist.value = false
            }
        }
    }
}