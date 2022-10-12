package pham.hien.honeylibrary.FireBase.FireStore

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.yingming.honeylibrary.Dialog.SuccessDialog

class PhieuMuonDAO {

    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun addPhieuMuon(context: Context, phieuMuon: PhieuMuon) {
        db.collection(Constant.PHIEUMUON.TB_NAME)
            .document(getListPhieuMuon().last().maPhieuMuon.toString() + 1)
            .set(phieuMuon)
            .addOnSuccessListener {
                SuccessDialog(context, context.getString(R.string.them_phieu_muon_thanh_cong), "")
            }
            .addOnFailureListener { e ->
                SuccessDialog(
                    context,
                    context.getString(R.string.them_phieu_muon_khong_thanh_cong),
                    context.getString(R.string.da_xay_ra_loi_trong_qua_trinh_them_phieu_muon)
                )
            }
    }

    fun checkPhieuMuonTrung(idTheLoai: Int, listTheLoai: ArrayList<TheLoai>): Boolean {
        for (theLoai in listTheLoai) {
            if (idTheLoai == theLoai.maTheLoai) {
                return true
            }
        }
        return false
    }

    fun getListPhieuMuon(): ArrayList<PhieuMuon> {
        val listPhieuMuon = ArrayList<PhieuMuon>()
        db.collection(Constant.PHIEUMUON.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listPhieuMuon.add(document.toObject(PhieuMuon::class.java))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listPhieuMuon
    }
}
