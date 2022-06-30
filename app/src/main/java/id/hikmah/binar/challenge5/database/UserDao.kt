package id.hikmah.binar.challenge5.database

import androidx.room.*

@Dao
interface UserDao {

    // Cek ketersediaan username pada saat register
    @Query("SELECT * FROM User WHERE username = :username")
    fun checkRegisteredUsername(username: String): List<User>

    // Cek ketersediaan email pada saat register
    @Query("SELECT * FROM User WHERE email = :email")
    fun checkRegisteredEmail(email: String): List<User>

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUsernameByEmail(email: String): User

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun checkLogin(email: String, password: String): List<User>

    // Insert ke DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(userDetail: UserDetail): Long

    @Query("SELECT * FROM UserDetail WHERE username = :username")
    fun getAllUserDetail(username: String): List<UserDetail>

    @Query("SELECT * FROM UserDetail WHERE username = :username")
    fun getAUserDetail(username: String): UserDetail

    @Query("UPDATE UserDetail SET nama_lengkap = :nama_lengkap, tgl_lahir = :tgl_lahir, alamat = :alamat WHERE username = :username")
    fun updateUserDetail(username: String, nama_lengkap: String, tgl_lahir: String, alamat: String): Int
}
