package pham.hien.honeylibrary.ViewModel.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.launch

import java.io.IOException

class PhieuMuonViewModel : ViewModel() {

    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    val mListDocGiaLiveData = MutableLiveData<ArrayList<UserModel>>()
    private lateinit var mListSach: ArrayList<Sach>
    private lateinit var mListUser: ArrayList<UserModel>
    private val TAG = "YingMing"

    fun getListSach() {
        viewModelScope.launch(
            onPreExecute = { mListSach = ArrayList() },
            doInBackground = {
                mListSach = SachDAO().getListSach()
                Log.d(TAG, "getListSach: ${mListSach.size}")
            },
            onPostExecute = {
                mListSachLiveData.value = mListSach
            }
        )
    }

    fun getListDocGia() {
        viewModelScope.launch(
            onPreExecute = {
                mListUser = ArrayList()
            },
            doInBackground = {
                mListUser = UserDAO().getListUser()
            },
            onPostExecute = {
                if (mListSach.isNotEmpty()) {
                    mListDocGiaLiveData.value = mListUser
                    Log.d(TAG, "getListSach: ${mListUser.size}")
                }
            }
        )
    }
}
