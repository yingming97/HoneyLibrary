package pham.hien.honeylibrary.Utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pham.hien.honeylibrary.Model.UserModel
import kotlin.collections.ArrayList

object SharedPrefUtils {

    private val PREFERENCES_NAME = "HoneyLibrary"

    /**
     * Không sửa các sharedPre này
     */
    /**
     * Lưu kiểu booblean
     */
    fun setBoolean(context: Context, value: Boolean) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("boolean", value).apply()
    }

    fun getBoolean(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean("boolean", false)
    }

    /**
     * Lưu kiểu string
     */
    fun getString(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("string", "")
    }

    fun setString(context: Context, value: String?) {
        val sharedPreferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("string", value).apply()
    }

    /**
     * Lưu kiêu int
     */
    fun getInt(context: Context): Int {
        val sharedPreferences =
            context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getInt("int", -1)
    }

    fun setInt(context: Context, value: Int) {
        val sharedPreferences =
            context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putInt("int", value).apply()
    }

    /**
     * Lưu kiểu long
     */
    fun setLong(context: Context, time: Long) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("long", time).apply()
    }

    fun getLong(context: Context): Long {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getLong("long", 0L)
    }

    /**
     * Lưu lại dạng list
     */
    fun setListString(context: Context, lisString: ArrayList<String>) {
        val pref = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        val gson = Gson()
        val listHistoryDayWorkout = gson.toJson(lisString)
        editor.putString("list_string", listHistoryDayWorkout)
        editor.apply()
    }

    fun getListString(context: Context): ArrayList<String> {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val result = preferences.getString("list_string", null)
        return if (result != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<String>>() {}.type
            gson.fromJson(result, type)
        } else {
            ArrayList()
        }
    }

    /**
     * lưu dạng model
     */
    fun setModel(context: Context, userModel: UserModel) {
        val pref = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        val gson = Gson()
        val model = gson.toJson(userModel)
        editor.putString("model", model)
        editor.apply()
    }

    fun getModel(context: Context): UserModel? {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val result = preferences.getString("model", null)
        return if (result != null) {
            val gson = Gson()
            val type = object : TypeToken<UserModel?>() {}.type
            gson.fromJson(result, type)
        } else {
            UserModel()
        }
    }
    /**
     *Thêm các Shared theo mẫu trên
     */

    fun setUserData(context: Context, userModel: UserModel) {
        val pref = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        val gson = Gson()
        val model = gson.toJson(userModel)
        editor.putString("user_login", model)
        editor.apply()
    }

    fun getUserData(context: Context): UserModel? {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        val result = preferences.getString("user_login", null)
        return if (result != null) {
            val gson = Gson()
            val type = object : TypeToken<UserModel?>() {}.type
            gson.fromJson(result, type)
        } else {
            UserModel()
        }
    }

    fun setLogin(context: Context, value: Boolean) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("user_login_status", value).apply()
    }

    fun getLogin(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean("user_login_status", false)
    }

    fun setPassword(context: Context, value: String) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("password_user", value).apply()
    }

    fun getPassword(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("password_user", "")
    }

}
