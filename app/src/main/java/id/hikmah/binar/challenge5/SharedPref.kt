package id.hikmah.binar.challenge5

import android.content.Context

class SharedPref(context: Context) {
    private val sharedPref = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

    fun setUsername(username: String) {
        val editor = sharedPref.edit()
        editor.putString("USERNAME", username)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPref.getString("USERNAME", "default username")
    }

    fun setLoginState(state: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("LOGIN_STATE", state)
        editor.apply()
    }

}