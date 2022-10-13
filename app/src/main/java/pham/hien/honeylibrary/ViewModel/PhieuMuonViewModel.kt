package pham.hien.honeylibrary.ViewModel.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.launch

import java.io.IOException

class PhieuMuonViewModel : ViewModel() {

    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    private lateinit var mListSach: ArrayList<Sach>
    private val db = Firebase.firestore
    private val TAG = "YingMing"

    fun getListSach() {
        viewModelScope.launch(
            onPreExecute = { mListSach = ArrayList() },
            doInBackground = {
                mListSach = getListSachData()
            },
            onPostExecute = {
                mListSachLiveData.value = mListSach
            }
        )
    }

    fun getListSachData(): ArrayList<Sach> {
        val listSach = ArrayList<Sach>()
        db.collection(Constant.SACH.TB_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listSach.add(document.toObject(Sach::class.java))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return listSach
    }
}
