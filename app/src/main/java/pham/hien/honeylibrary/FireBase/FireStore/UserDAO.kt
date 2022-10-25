package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class UserDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addUser(context: Context, user: UserModel) {
        Log.d(TAG, "addUser:$user ")
        db.collection(Constant.USER.TB_NAME)
            .document(user.userId.toString())
            .set(user)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.dang_ky_thanh_cong), ""){}.show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.dang_ky_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_dang_ky)
                ){}.show()
            }
    }

    fun getListUser(listDocGia: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }

                listUser.sortBy { it.userId }
                listDocGia(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    fun updateDocGia(context: Context, docGia: UserModel) {
        db.collection(Constant.USER.TB_NAME)
            .document(docGia.userId.toString())
            .set(docGia)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.sua_the_loai_thanh_cong), ""){}
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.sua_the_loai_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_sua_the_loai)
                ){}
            }
    }

    fun updateNhanVien(context: Context, user: UserModel) {
        db.collection(Constant.USER.TB_NAME)
            .document(user.userId.toString())
            .set(user)
            .addOnSuccessListener {
                SuccessDialog(context, "Sửa Thành Công", ""){}.show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    "Sửa lỗi",
                    "Đã xảy ra lỗi trong quá trình update"
                ){}.show()
            }
    }

    fun getListUserDocGia(listDocGia: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .whereEqualTo(Constant.USER.COL_TYPE, Constant.QUYEN.DOC_GIA)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }
                listUser.sortBy { it.userId }
                listDocGia(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getListUserNhanVienAndAdmin(listNhanVien: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .whereIn(Constant.USER.COL_TYPE, arrayListOf(1, 2))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }
                listNhanVien(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getListUserNhanVien(listNhanVien: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .whereEqualTo(Constant.USER.COL_TYPE, 1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }
                listNhanVien(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getListUserAdmin(listAdmin: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .whereEqualTo(Constant.USER.COL_TYPE, 2)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }
                listAdmin(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}
