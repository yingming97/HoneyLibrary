package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class TheLoaiDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addNewTheLoai(context: Context, theLoai: TheLoai) {
        Log.d(TAG, "addNewTheLoai: ${theLoai.maTheLoai}")
        db.collection(Constant.THELOAI.TB_NAME)
            .document(theLoai.maTheLoai.toString())
            .set(theLoai)
            .addOnSuccessListener {
                SuccessDialog(context,
                    context.getString(R.string.them_the_loai_thanh_cong),
                    ""){}.show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_the_loai_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_the_loai)
                ){}.show()
            }
    }

    fun getListTheLoai(listTheLoai: ((ArrayList<TheLoai>) -> Unit)) {
        val listTheLoai1 = ArrayList<TheLoai>()
        db.collection(Constant.THELOAI.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listTheLoai1.add(document.toObject(TheLoai::class.java))
                }
                listTheLoai1.sortBy { it.maTheLoai }
                listTheLoai(listTheLoai1)
                listTheLoai1.sortBy {(it.maTheLoai)}
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateTheLoai(context: Context, theLoai: TheLoai) {
        db.collection(Constant.THELOAI.TB_NAME)
            .document(theLoai.maTheLoai.toString())
            .set(theLoai)
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
}
