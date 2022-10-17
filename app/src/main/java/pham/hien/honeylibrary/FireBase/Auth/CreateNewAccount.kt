package pham.hien.honeylibrary.FireBase.Auth

import android.content.Context
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel

class CreateNewAccount {

    private val TAG = "YingMing"
    private val mAuth = Firebase.auth

    // user : userId đã đc + 1 so với userId của phần tử cuối cùng ListUser
    // user : đã qua được validate

    fun createNewUser(context: Context, user: UserModel) {
        mAuth.createUserWithEmailAndPassword(user.email, user.sdt)
            .addOnSuccessListener {
                mAuth.currentUser?.let { u ->
                    {
                        user.firebaseId = u.uid
                        UserDAO().addUser(context, user)
                    }
                }
                mAuth.signOut()
            }
            .addOnFailureListener {
                Log.d(TAG, "getFirebaseId: error")
            }
    }
}