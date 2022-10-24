package pham.hien.honeylibrary.FireBase.Auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.View.Main.MainActivity
import pham.yingming.honeylibrary.Dialog.FailDialog

class LoginRegisterAuth {
    fun accountLogin(
        context: Context,
        activity: Activity,
        email: String,
        pass: String,
        arrUser: List<UserModel>,
        callback: (Boolean) -> Unit
    ) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(
            email,
            pass
        ).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                for (mUser in arrUser) {
                    if (mUser.firebaseId == user?.uid) {
                        SharedPrefUtils.setUserData(context, mUser)
                    }
                }
                SharedPrefUtils.setPassword(context, pass)
                SharedPrefUtils.setLogin(context, true)
                callback(true)
            } else {
                FailDialog(
                    context,
                    "Đăng nhập thất bại",
                    "Email hoặc mật khẩu không đúng"
                ).show()
                callback(false)
            }
        }
    }


    fun registerNewAccount(
        activity: Activity,
        mUser: UserModel,
        pass: String,
        callback: (Boolean, UserModel) -> Unit
    ) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(mUser.email, pass)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    mUser.firebaseId = user!!.uid
                    Firebase.firestore.collection(Constant.USER.TB_NAME)
                        .document((mUser.userId).toString())
                        .set(mUser)
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { e ->
                            FailDialog(
                                activity.applicationContext,
                                "Đăng ký thất bại",
                                "Vui lòng kiểm tra lại kết nối internet của bạn"
                            ).show()
                        }
                    callback(true, mUser)
                } else {
                    callback(false, mUser)
                }
            }
    }

    fun loginAfterRegister(mUser: UserModel, pass: String, activity: Activity){
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(
            mUser.email,
            pass
        ).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                SharedPrefUtils.setPassword(activity, pass)
                SharedPrefUtils.setLogin(activity, true)
                SharedPrefUtils.setUserData(activity, mUser)
            }
        }
        activity.startActivity(Intent(activity.applicationContext, MainActivity::class.java))
    }
}