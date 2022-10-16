package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class SachDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addSach(context: Context, sach: Sach) {
        db.collection(Constant.SACH.TB_NAME)
            .document((getListSach().last().maSach + 1).toString())
            .set(sach)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.them_sach_thanh_cong), "")
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_sach_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_sach)
                )
            }
    }

    fun checkSachTrung(idSach: Int, listSach: ArrayList<Sach>): Boolean {
        for (sach in listSach) {
            if (idSach == sach.maSach) {
                return true
            }
        }
        return false
    }

    fun getListSach(): ArrayList<Sach> {
        val listSach = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listSach.add(document.toObject(Sach::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listSach
    }
    fun getListSach(listSach : ((ArrayList<Sach>) -> Unit)) {
        val list = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject(Sach::class.java))
                }
                listSach(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}
