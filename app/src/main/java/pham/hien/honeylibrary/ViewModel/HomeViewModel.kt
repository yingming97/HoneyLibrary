package pham.hien.honeylibrary.ViewModel.Main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.FireBase.FireStore.PhieuMuonDAO
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.launch


class HomeViewModel : ViewModel() {
    val mListDoanhThuLiveData = MutableLiveData<ArrayList<DoanhThu>>()
    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    var mListDocGiaLiveData = MutableLiveData<ArrayList<UserModel>>()
    val mListPhieuMuonLiveData = MutableLiveData<ArrayList<PhieuMuon>>()

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

    fun getListDoanhThu() {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                DoanhThuDAO().getListDoanhThu {
                    mListDoanhThuLiveData.value = it
                }
            },
            onPostExecute = {
            }
        )
    }
    fun getListSachMoi() {
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                SachDAO().getListSachChuaThuHoi {
                    mListSachLiveData.value = it
                }
            },
            onPostExecute = { })
    }
    fun getListPhieuMuon(){
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                PhieuMuonDAO().getListPhieuMuon {
                    mListPhieuMuonLiveData.value = it
                }
            },
            onPostExecute = { })
    }
}
