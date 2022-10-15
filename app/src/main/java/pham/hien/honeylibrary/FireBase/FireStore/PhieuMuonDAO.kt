package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils
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
                    "").show()
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_phieu_muon_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_phieu_muon)
                ).show()
            }
    }

    fun checkPhieuMuonTrung(idPhieuMuon: String, listPhieuMuon: ArrayList<PhieuMuon>): Boolean {
        for (phieuMuon in listPhieuMuon) {
            if (idPhieuMuon == phieuMuon.maPhieuMuon) {
                return true
            }
        }
        return false
    }

    fun getListPhieuMuon(listPhieuMuon: ((ArrayList<PhieuMuon>) -> Unit)) {
        val list = ArrayList<PhieuMuon>()
        getListUserDocGia {
            db.collection(Constant.PHIEUMUON.TB_NAME)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val phieuMuon = document.toObject(PhieuMuon::class.java)
                       phieuMuon.photoDocGia = getTenDocGia(phieuMuon.maDocGia,it){
                           phieuMuon.tenDocGia = it
                       }
                        list.add(phieuMuon)
                    }
                    listPhieuMuon(list)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

    }

    fun getListPhieuDocGia(context: Context,firebaseId: String, listPhieuMuon: ((ArrayList<PhieuMuon>) -> Unit)) {
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
            .whereEqualTo(Constant.USER.COL_TYPE, 0)
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
}
