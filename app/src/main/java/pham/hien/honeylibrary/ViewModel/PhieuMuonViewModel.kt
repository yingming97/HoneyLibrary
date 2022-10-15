package pham.hien.honeylibrary.ViewModel.Main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.launch

import java.io.IOException

class PhieuMuonViewModel : ViewModel() {

    private val TAG = "YingMing"

    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    var mListDocGiaLiveData = MutableLiveData<ArrayList<UserModel>>()
    var mListPhieuMuonLiveData = MutableLiveData<ArrayList<PhieuMuon>>()

    fun getListSach() {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                SachDAO().getListSach {
                    mListSachLiveData.value = it
                }
            },
            onPostExecute = {
            }
        )
    }

    fun getListDocGia() {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                UserDAO().getListUserDocGia {
                    mListDocGiaLiveData.value = it
                }
            },
            onPostExecute = {}
        )
    }

    fun getListPhieuMuon(context: Context,typeUser: Int, firebaseId: String) {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                when (typeUser) {
                    Constant.QUYEN.DOC_GIA -> {
                        PhieuMuonDAO().getListPhieuDocGia(context,firebaseId) {
                            mListPhieuMuonLiveData.value = it
                        }
                    }
                    Constant.QUYEN.THU_THU, Constant.QUYEN.ADMIN -> {
                        PhieuMuonDAO().getListPhieuMuon {
                            mListPhieuMuonLiveData.value = it
                        }
                    }
                }
            },
            onPostExecute = {}
        )
    }
}
