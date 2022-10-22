package pham.hien.honeylibrary.FireBase.FireStore

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Utils.Constant

class DoanhThuDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addDoanhThu(doanhThu: DoanhThu) {
        db.collection(Constant.DOANHTHU.TB_NAME)
            .document(doanhThu.time.toString())
            .set(doanhThu)
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->

            }
    }

    fun deleteDoanhThu(time: Long) {
        db.collection(Constant.DOANHTHU.TB_NAME)
            .document(time.toString())
            .delete()
    }

    fun getListDoanhThu(listDoanhThu: ((ArrayList<DoanhThu>) -> Unit)) {
        val list = ArrayList<DoanhThu>()
        db.collection(Constant.DOANHTHU.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject(DoanhThu::class.java))
                }
                listDoanhThu(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}
