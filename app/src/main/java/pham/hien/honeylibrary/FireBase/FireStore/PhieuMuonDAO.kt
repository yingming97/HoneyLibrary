package pham.hien.honeylibrary.FireBase.FireStore

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
import pham.hien.honeylibrary.Utils.convertListSachThueToString
import pham.hien.honeylibrary.Utils.convertStringToListSachThue
import pham.yingming.honeylibrary.Dialog.FailDialog
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class PhieuMuonDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addPhieuMuon(context: Context, phieuMuon: PhieuMuon) {
        db.collection(Constant.PHIEUMUON.TB_NAME)
            .document(phieuMuon.maPhieuMuon)
            .set(phieuMuon)
            .addOnSuccessListener {
                SuccessDialog(context,
                    context.getString(R.string.them_phieu_muon_thanh_cong),
                    "") {}.show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_phieu_muon_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_phieu_muon)
                ) {}.show()
            }
    }

    fun getListPhieuMuon(listPhieuMuon: ((ArrayList<PhieuMuon>) -> Unit)) {
        val list = ArrayList<PhieuMuon>()
        getListUserDocGia {
            db.collection(Constant.PHIEUMUON.TB_NAME)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val phieuMuon = document.toObject(PhieuMuon::class.java)
                        phieuMuon.photoDocGia = getTenDocGia(phieuMuon.maDocGia, it) {
                            phieuMuon.tenDocGia = it
                        }
                        updateListSachThue(phieuMuon)
                        list.add(phieuMuon)
                        updatePhieuMuonDoNothing(phieuMuon)
                    }
                    listPhieuMuon(list)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

    }

    fun getListPhieuDocGia(
        context: Context,
        firebaseId: String,
        listPhieuMuon: ((ArrayList<PhieuMuon>) -> Unit),
    ) {
        val list = ArrayList<PhieuMuon>()
        val user = SharedPrefUtils.getUserData(context)!!
        db.collection(Constant.PHIEUMUON.TB_NAME)
            .whereEqualTo(Constant.PHIEUMUON.COL_MA_DOC_GIA, firebaseId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val phieuMuon = document.toObject(PhieuMuon::class.java)
                    phieuMuon.tenDocGia = user.name
                    phieuMuon.photoDocGia = user.avatar
                    updateListSachThue(phieuMuon)
                    updatePhieuMuonDoNothing(phieuMuon)
                    list.add(phieuMuon)
                }
                listPhieuMuon(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun getListUserDocGia(listDocGia: ((ArrayList<UserModel>) -> Unit)) {
        val listUser = ArrayList<UserModel>()
        db.collection(Constant.USER.TB_NAME)
            .whereEqualTo(Constant.USER.COL_TYPE, Constant.QUYEN.DOC_GIA)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listUser.add(document.toObject(UserModel::class.java))
                }
                listDocGia(listUser)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun getTenDocGia(
        firebaseId: String,
        listDocGia: ArrayList<UserModel>,
        tenDocGia: ((String) -> Unit),
    ): String {
        for (docGia in listDocGia) {
            if (docGia.firebaseId == firebaseId) {
                tenDocGia(docGia.name)
                return docGia.avatar
            }
        }
        return Constant.USER.AVATAR_DEFAULT
    }

    fun deletePhieuMuon(activity: Activity, phieuMuon: PhieuMuon) {
        db.collection(Constant.PHIEUMUON.TB_NAME).document(phieuMuon.maPhieuMuon)
            .delete()
            .addOnSuccessListener {
                SuccessDialog(activity,
                    activity.getString(R.string.da_xoa_phieu_muon),
                    "") {}.show()
                Handler().postDelayed({ activity.finish() }, 2000)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                FailDialog(activity, activity.getString(R.string.xoa_khong_thanh_cong), "").show()
            }
    }

    fun updatePhieuMuonDoNothing(phieuMuon: PhieuMuon) {
        val phieuMap: MutableMap<String, Any> = HashMap()
        phieuMap[Constant.PHIEUMUON.COL_SO_LUONG] = phieuMuon.soLuong
        phieuMap[Constant.PHIEUMUON.COL_TONG_TIEN] = phieuMuon.tongTien
        phieuMap[Constant.PHIEUMUON.COL_PHOTO_DOC_GIA] = phieuMuon.photoDocGia
        phieuMap[Constant.PHIEUMUON.COL_TEN_DOC_GIA] = phieuMuon.tenDocGia
        phieuMap[Constant.PHIEUMUON.COL_LIST_SACH_THUE] = phieuMuon.listSachThue
        phieuMap[Constant.PHIEUMUON.COL_TRANG_THAI] = phieuMuon.trangThai
        phieuMap[Constant.PHIEUMUON.COL_GHI_CHU] = phieuMuon.ghiChu

        db.collection(Constant.PHIEUMUON.TB_NAME)
            .document(phieuMuon.maPhieuMuon)
            .update(phieuMap)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    fun updatePhieuMuon(activity: Activity, phieuMuon: PhieuMuon) {
        val phieuMap: MutableMap<String, Any> = HashMap()
        phieuMap[Constant.PHIEUMUON.COL_SO_LUONG] = phieuMuon.soLuong
        phieuMap[Constant.PHIEUMUON.COL_TONG_TIEN] = phieuMuon.tongTien
        phieuMap[Constant.PHIEUMUON.COL_PHOTO_DOC_GIA] = phieuMuon.photoDocGia
        phieuMap[Constant.PHIEUMUON.COL_TEN_DOC_GIA] = phieuMuon.tenDocGia
        phieuMap[Constant.PHIEUMUON.COL_LIST_SACH_THUE] = phieuMuon.listSachThue
        phieuMap[Constant.PHIEUMUON.COL_TRANG_THAI] = phieuMuon.trangThai
        phieuMap[Constant.PHIEUMUON.COL_GHI_CHU] = phieuMuon.ghiChu
        phieuMap[Constant.PHIEUMUON.COL_NGAY_TRA] = phieuMuon.ngayTra

        db.collection(Constant.PHIEUMUON.TB_NAME)
            .document(phieuMuon.maPhieuMuon)
            .update(phieuMap)
            .addOnSuccessListener {
                SuccessDialog(activity,
                    activity.getString(R.string.cap_nhap_phieu_muon_thanh_cong),
                    "") {}.show()
            }
            .addOnFailureListener {
                FailDialog(activity,
                    activity.getString(R.string.cap_nhap_phieu_muon_khong_thanh_cong),
                    "").show()
            }
    }

    fun updateTrangThaiPhieuMuon(phieuMuon: PhieuMuon, updateDone: () -> Unit,updateNotDone: () -> Unit) {
        db.collection(Constant.PHIEUMUON.TB_NAME)
            .document(phieuMuon.maPhieuMuon)
            .update(Constant.PHIEUMUON.COL_TRANG_THAI, phieuMuon.trangThai)
            .addOnCompleteListener {
                updateDone()
            }
            .addOnFailureListener {
                updateNotDone()
            }
    }

    fun updateSoLuongSachConLai(sach: Sach, sachThue: SachThue) {
        db.collection(Constant.SACH.TB_NAME)
            .document(sach.maSach.toString())
            .update(Constant.SACH.COL_SO_LUONG_CON_LAI, sach.soLuongConLai - sachThue.soLuong)
            .addOnSuccessListener {
                Log.d(TAG, "update ${sach.soLuongConLai - sachThue.soLuong}")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }
    fun updateSoLuongSachConLai(maSach :Int,soLuongConLai :Int) {
        db.collection(Constant.SACH.TB_NAME)
            .document(maSach.toString())
            .update(Constant.SACH.COL_SO_LUONG_CON_LAI, soLuongConLai)
            .addOnSuccessListener {
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }

    fun updateSoLuongSach(sach: Sach) {
        db.collection(Constant.SACH.TB_NAME)
            .document(sach.maSach.toString())
            .update(Constant.SACH.COL_SO_LUONG, sach.soLuong)
            .addOnSuccessListener {
                Log.w(TAG, "update")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun addSachTraThieu(context: Context, sach: Sach) {
        db.collection(Constant.SACHTRATHIEU.TB_NAME)
            .document(sach.maSach.toString())
            .set(sach)
            .addOnSuccessListener {
                Log.d(TAG, "add")
            }
            .addOnFailureListener {
                Log.d(TAG, "error")
            }
    }

    fun updateSoLuongSachThieu(sach: Sach) {
        db.collection(Constant.SACHTRATHIEU.TB_NAME)
            .document(sach.maSach.toString())
            .update(Constant.SACH.COL_SO_LUONG, sach.soLuong)
            .addOnSuccessListener {
                Log.w(TAG, "update")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun updateListSachThue(phieuMuon: PhieuMuon) {
        val list = convertStringToListSachThue(phieuMuon.listSachThue)
        SachDAO().getListSachChuaThuHoi {
            for (i in 0 until list.size) {
                val sachThue = list[i]
                for (sach in it) {
                    if (sach.maSach == sachThue.maSach) {
                        sachThue.tenSach = sach.tenSach
                        sachThue.biaSach = sach.anhBia
                        sachThue.giaSach = sach.giaSach
                        list[i] = sachThue
                    }
                }
            }
            phieuMuon.listSachThue = convertListSachThueToString(list)
        }
    }
}
