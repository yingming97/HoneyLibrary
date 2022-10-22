package pham.hien.honeylibrary.FireBase.Auth

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class CreateNewAccount {

    private val TAG = "YingMing"
    private val mAuth = Firebase.auth

    // user : userId đã đc + 1 so với userId của phần tử cuối cùng ListUser
    // user : đã qua được validate

    fun createNewUser(activity: Activity, user: UserModel, getUID: ((String) -> Unit)) {
        mAuth.createUserWithEmailAndPassword(user.email, user.sdt)
            .addOnSuccessListener {
                val currentUser = Firebase.auth.currentUser
                user.firebaseId = currentUser?.uid.toString()
                UserDAO().addUser(activity, user)
                mAuth.signOut()
            }
            .addOnFailureListener {
                Log.d(TAG, "getFirebaseId: error $it")
            }
    }


}