package pham.hien.honeylibrary.FireStoreDataBase

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Utils.Constant

@SuppressLint("StaticFieldLeak")
private val db = Firebase.firestore
private val TAG = "YingMing"

fun addNewTheLoai(theLoai: TheLoai) {

    db.collection(Constant.THELOAI.TB_NAME)
        .document(theLoai.maTheLoai)
        .set(theLoai)
        .addOnSuccessListener {
        }
        .addOnFailureListener { e ->
        }
}

fun checkTheLoaiTrung(idTheLoai: String, listTheLoai: ArrayList<TheLoai>): Boolean {
    for (theLoai in listTheLoai) {
        if (idTheLoai == theLoai.maTheLoai) {
            return true
        }
    }
    return false
}

fun getListTheLoai(): ArrayList<TheLoai> {
    val listTheLoai = ArrayList<TheLoai>()
    db.collection(Constant.THELOAI.TB_NAME)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                listTheLoai.add(document.toObject(TheLoai::class.java))
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
    return listTheLoai
}