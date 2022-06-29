package id.hikmah.binar.challenge5.database

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(context: Context) {

    private val userDb = UserDatabase.getInstance(context)

    suspend fun checkRegisteredkUsername(username: String) = withContext(Dispatchers.IO){
        userDb?.userDao()?.checkRegisteredUsername(username)
    }

    suspend fun checkRegisteredEmail(email: String) = withContext(Dispatchers.IO){
        userDb?.userDao()?.checkRegisteredEmail(email)
    }

    suspend fun isLogin(email: String, password: String)= withContext(Dispatchers.IO){
        userDb?.userDao()?.isLogin(email, password)
    }
    //
    suspend fun getUsernameByMail(email: String) = withContext(Dispatchers.IO){
        userDb?.userDao()?.getUsernameByEmail(email)
    }
    //
    suspend fun getUserDetail(username: String){
        userDb?.userDao()?.getAllUserDetail(username)
    }
    //
    suspend fun getAUser(username: String){
        userDb?.userDao()?.getAUserDetail(username)
    }
    //
    suspend fun insertUserDetail(profilEntity: ProfilEntity){
        userDb?.userDao()?.insertUserDetail(profilEntity)
    }

    suspend fun updateUserProfile(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String) = withContext(
        Dispatchers.IO) {
        userDb?.userDao()?.updateUserDetail(username, nama_lengkap, tgl_lahir, alamat)
    }
    //
    suspend fun insertUser(user: UserEntity){
        userDb?.userDao()?.insertUser(user)
    }

}