package pham.hien.honeylibrary.FireBase.Auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.View.Tab.Option.Activity.DocGia.DocGiaActivity
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class CreateNewAccount {

    private val TAG = "YingMing"
    private val mAuth = Firebase.auth
    private val db = Firebase.firestore

    // user : userId đã đc + 1 so với userId của phần tử cuối cùng ListUser
    // user : đã qua được validate

    fun createNewUser(activity: Activity, user: UserModel, callback: (() -> Unit)?) {
        Log.d(TAG, "createNewUser: $user")
        mAuth.createUserWithEmailAndPassword(user.email, user.sdt)
            .addOnSuccessListener {
                val currentUser = mAuth.currentUser
                user.firebaseId = currentUser?.uid.toString()
                Log.d(TAG, "createNewUser: $user")

                db.collection(Constant.USER.TB_NAME)
                    .document(user.userId.toString())
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TAG, "addOnSuccessListener: $user")
                        SuccessDialog(
                            activity,
                            activity.getString(R.string.dang_ky_thanh_cong),
                            ""
                        ){
                            activity.finish()
                        }.show()
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "addOnFailureListener: $user")
                        SuccessDialog(
                            activity,
                            activity.getString(R.string.dang_ky_khong_thanh_cong),
                            activity.getString(R.string.da_xay_ra_loi_trong_qua_trinh_dang_ky)
                        ){
                            activity.finish()
                        }.show()
                    }
            }
            .addOnFailureListener {
                Log.d(TAG, "getFirebaseId: error $it")
            }
    }


    fun createNewDocGia(activity: Activity, user: UserModel, callback: (() -> Unit)?) {
        Log.d(TAG, "createNewUser: $user")
        mAuth.createUserWithEmailAndPassword(user.email, user.sdt)
            .addOnSuccessListener {
                val currentUser = mAuth.currentUser
                user.firebaseId = currentUser?.uid.toString()
                Log.d(TAG, "createNewUser: $user")

                db.collection(Constant.USER.TB_NAME)
                    .document(user.userId.toString())
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TAG, "addOnSuccessListener: $user")
                        SuccessDialog(
                            activity,
                            activity.getString(R.string.dang_ky_thanh_cong),
                            ""
                        ){
                            activity.finish()
                        }.show()

                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "addOnFailureListener: $user")
                        SuccessDialog(
                            activity,
                            activity.getString(R.string.dang_ky_khong_thanh_cong),
                            activity.getString(R.string.da_xay_ra_loi_trong_qua_trinh_dang_ky)
                        ){
                            activity.finish()
                        }.show()
                    }
            }
            .addOnFailureListener {
                Log.d(TAG, "getFirebaseId: error $it")
            }
    }


}