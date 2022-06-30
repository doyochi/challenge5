package id.hikmah.binar.challenge5.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hikmah.binar.challenge5.database.UserDetail
import id.hikmah.binar.challenge5.database.UserRepo
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepo: UserRepo, private val sharedPrefs: SharedPreferences?): ViewModel() {

    val showUsername = MutableLiveData<String?>()
    val usernameDetail = MutableLiveData<String>()
    val namaLengkapDetail = MutableLiveData<String>()
    val tglLahirDetail = MutableLiveData<String>()
    val alamatDetail = MutableLiveData<String>()
    val statusUpdateProfile = MutableLiveData<Boolean>()
    val statusLogout = MutableLiveData<Boolean>()

    fun getUsername() {
        val getUsername = sharedPrefs?.getString("USERNAME", "DefaultUsername")
        showUsername.value = getUsername
    }

    fun getUserDetail() {
        val getUsername = sharedPrefs?.getString("USERNAME", "DefaultUsername")
        viewModelScope.launch {
            // Query mencari UserDetail berdasarkan username
            val result = userRepo.getUserDetail(getUsername!!)
            // Query mencari username, namalengkap, tgllahir, alamat berdasarkan Username
            val userDetail = userRepo.getAUser(getUsername)

            if (!result.isNullOrEmpty()) {
                // Munculkan value dari tabel UserDetail ke EditText via MutableLiveData
                usernameDetail.value = userDetail?.username!!
                namaLengkapDetail.value = userDetail.nama_lengkap!!
                tglLahirDetail.value = userDetail.tgl_lahir!!
                alamatDetail.value = userDetail.alamat!!
            } else {
                usernameDetail.value = getUsername!!
            }
        }
    }

    fun updateUserDetail(username: String, namaLengkap: String, tglLahir: String, alamat: String) {
        viewModelScope.launch {
            val userDetail = UserDetail(null, username, namaLengkap, tglLahir, alamat)
            val result = userRepo.getUserDetail(username)
            if (!result.isNullOrEmpty()) { // Jika datanya sudah ada
                // Jalankan query Update UserDetail
                userRepo.updateUserProfile(username, namaLengkap, tglLahir, alamat)
                statusUpdateProfile.value = true
            } else { // Jika datanya belum ada
                // Tambahkan data baru pada tabel UserDetail
                userRepo.insertUserDetail(userDetail)
                statusUpdateProfile.value = true
            }
        }
    }

    fun logoutAccount() {
        val editor = sharedPrefs?.edit()
        editor?.apply {
            clear()
            putBoolean("LOGIN_STATE", false)
            apply()
            statusLogout.value = true
        }
    }

}