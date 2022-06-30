package id.hikmah.binar.challenge5.database

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(context: Context) {
    private val userDb = UserDatabase.getInstance(context)

    suspend fun cekUsername(username: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.checkRegisteredUsername(username)
    }

    suspend fun cekEmail(email: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.checkRegisteredEmail(email)
    }

    suspend fun cekUser(email: String, password: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.checkLogin(email, password)
    }

    suspend fun getUsernameByMail(email: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.getUsernameByEmail(email)
    }

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.insertUser(user)
    }

    suspend fun getUserDetail(username: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.getAllUserDetail(username)
    }

    suspend fun getAUser(username: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.getAUserDetail(username)
    }

    suspend fun insertUserDetail(userDetail: UserDetail) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.insertUserDetail(userDetail)
    }

    suspend fun updateUserProfile(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String) = withContext(Dispatchers.IO) {
        userDb?.userDao()?.updateUserDetail(username, nama_lengkap, tgl_lahir, alamat)
    }

}
