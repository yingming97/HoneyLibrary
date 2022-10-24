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

    fun addSach(context: Context, sach: Sach, addSuccess: (Boolean) -> Unit) {
        db.collection(Constant.SACH.TB_NAME)
            .document(sach.maSach.toString())
            .set(sach)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.them_sach_thanh_cong), "").show()
                addSuccess(true)
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_sach_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_sach)
                ).show()
                addSuccess(false)
            }
    }

    fun getListSachChuaThuHoi(listSach: ((ArrayList<Sach>) -> Unit)) {
        val list = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .whereEqualTo(Constant.SACH.COL_THU_HOI, false)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject(Sach::class.java))
                }
                list.sortBy { it.maSach }
                listSach(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getListSachDaThuHoi(listSach: ((ArrayList<Sach>) -> Unit)) {
        val list = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .whereEqualTo(Constant.SACH.COL_THU_HOI, true)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject(Sach::class.java))
                }
                list.sortBy { it.maSach }
                listSach(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getListAllSach(listSach: ((ArrayList<Sach>) -> Unit)) {
        val list = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject(Sach::class.java))
                }
                list.sortBy { it.maSach }
                listSach(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateSach(sach: Sach) {
        val sachMap: MutableMap<String, Any> = HashMap()
        sachMap[Constant.SACH.COL_TEN_SACH] = sach.tenSach
        sachMap[Constant.SACH.COL_ANH_BIA] = sach.anhBia
        sachMap[Constant.SACH.COL_GIA_SACH] = sach.giaSach
        sachMap[Constant.SACH.COL_GIA_THUE] = sach.giaThue
        sachMap[Constant.SACH.COL_SO_LUONG] = sach.soLuong
        sachMap[Constant.SACH.COL_SO_LUONG_CON_LAI] = sach.soLuongConLai
        sachMap[Constant.SACH.COL_GIOI_THIEU] = sach.gioiThieu
        sachMap[Constant.SACH.COL_MA_THE_LOAI] = sach.maLoai
        sachMap[Constant.SACH.COL_THU_HOI] = sach.thuHoi

        db.collection(Constant.SACH.TB_NAME)
            .document(sach.maSach.toString())
            .update(sachMap)
    }

    fun updateThuHoiSach(sach: Sach) {
        db.collection(Constant.SACH.TB_NAME)
            .document(sach.maSach.toString())
            .update(Constant.SACH.COL_THU_HOI, sach.thuHoi)
    }
}
