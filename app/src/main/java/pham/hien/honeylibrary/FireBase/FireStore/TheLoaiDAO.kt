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

        db.collection(Constant.THELOAI.TB_NAME)
            .document((getListTheLoai1().last().maTheLoai + 1).toString())
            .set(theLoai)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.them_the_loai_thanh_cong), "").show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_the_loai_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_the_loai)
                ).show()
            }
    }

    fun checkTheLoaiTrung(idTheLoai: Int, listTheLoai: ArrayList<TheLoai>): Boolean {
        for (theLoai in listTheLoai) {
            if (idTheLoai == theLoai.maTheLoai) {
                return true
            }
        }
        return false
    }

    fun getListTheLoai1() : ArrayList<TheLoai> {
        val listTheLoai = ArrayList<TheLoai>()
        db.collection(Constant.THELOAI.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listTheLoai.add(document.toObject(TheLoai::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listTheLoai
    }

    fun getListTheLoai(listTheLoai:((ArrayList<TheLoai>)-> Unit)){
        val listTheLoai1 = ArrayList<TheLoai>()
        db.collection(Constant.THELOAI.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listTheLoai1.add(document.toObject(TheLoai::class.java))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                listTheLoai(listTheLoai1)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateTheLoai(context: Context, theLoai: TheLoai){
        db.collection(Constant.THELOAI.TB_NAME)
            .document(theLoai.maTheLoai.toString())
            .update(theLoai.maTheLoai.toString(), true)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.sua_the_loai_thanh_cong), "")
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.sua_the_loai_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_sua_the_loai)
                )
            }
    }

    fun deleteTheLoai(context: Context, theLoai: TheLoai){
        db.collection(Constant.THELOAI.TB_NAME)
            .document(theLoai.maTheLoai.toString())
            .delete()
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.xoa_the_loai_thanh_cong), "")
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.xoa_the_loai_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_xoa_the_loai)
                )
            }
    }
}
