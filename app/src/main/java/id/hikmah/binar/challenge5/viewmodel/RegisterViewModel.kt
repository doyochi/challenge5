package id.hikmah.binar.challenge5.viewmodel
//
//import android.content.SharedPreferences
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import id.hikmah.binar.challenge5.database.UserEntity
//import id.hikmah.binar.challenge5.database.UserRepo
//import kotlinx.coroutines.launch
//
//class RegisterViewModel (private val userRepo: UserRepo, private val sharedPrefs: SharedPreferences?): ViewModel() {
//
//    val isRegist = MutableLiveData<Boolean>()
//    val emailIsRegist = MutableLiveData<Boolean>()
//    val userIsRegist = MutableLiveData<Boolean>()
//
//    fun addUserToDb(username: String, email: String, password: String) {
//        var result1 = false
//        var result2 = false
//
//        viewModelScope.launch {
//            val user = UserEntity(null, username, email, password)
//
//            // Query check username & email
//            val checkUsername = userRepo.checkRegisteredkUsername(username)
//            val checkEmail = userRepo.checkRegisteredEmail(email)
//
//            if (!checkUsername.isNullOrEmpty()) { // jika ditemukan username sudah dipakai
//                userIsRegist.value = true
//            } else {
//                result1 = true
//            }
//
//            if (!checkEmail.isNullOrEmpty()) { // jika ditemukan email sudah dipakai
//                emailIsRegist.value = true
//            } else {
//                result2 = true
//            }
//
//            if (result1 && result2) {
//                isRegist.value = true
//                // Jalankan query insert to db
//                userRepo.insertUser(user)
//            } else {
//                isRegist.value = false
//            }
//        }
//    }
//}