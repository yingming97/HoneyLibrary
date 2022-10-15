package pham.hien.honeylibrary.FireBase.Auth

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog

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
                    if (mUser.email == user?.email) {
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
        context: Context,
        activity: Activity,
        mUser: UserModel,
        pass: String,
        callback: (Boolean, UserModel) -> Unit
    ) {
        val auth = Firebase.auth
        val userC = mUser
        auth.createUserWithEmailAndPassword(mUser.email, pass)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    userC.firebaseId = user!!.uid
                    Firebase.firestore.collection(Constant.USER.TB_NAME)
                        .document((mUser.userId).toString())
                        .set(userC)
                        .addOnSuccessListener {
                            SuccessDialog(
                                context,
                                context.getString(R.string.dang_ky_thanh_cong),
                                ""
                            )
                        }
                        .addOnFailureListener { e ->
                            FailDialog(
                                context,
                                "Đăng ký thất bại",
                                "Vui lòng kiểm tra lại kết nối internet của bạn"
                            ).show()
                        }
                    callback(true,userC)
                } else {
                    callback(false, userC)
                }
            }
    }

}