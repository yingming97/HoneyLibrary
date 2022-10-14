package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class UserDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addUser(context: Context, user: UserModel) {
        db.collection(Constant.USER.TB_NAME)
            .document((getListUser().last().userId +1).toString())
            .set(user)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.dang_ky_thanh_cong), "")
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.dang_ky_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_dang_ky)
                )
            }
    }

    fun checkUserTrung(email: String, listUser: ArrayList<UserModel>): Boolean {
        for (user in listUser) {
            if (email == user.email) {
                return true
            }
        }
        return false
    }

    fun getListUser(): ArrayList<UserModel> {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listUser
    }
}
