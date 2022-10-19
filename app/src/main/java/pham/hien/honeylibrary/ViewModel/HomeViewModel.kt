package pham.hien.honeylibrary.ViewModel.Main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.launch


class HomeViewModel : ViewModel() {
    val mListDoanhThuLiveData = MutableLiveData<ArrayList<DoanhThu>>()
    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    var mListDocGiaLiveData = MutableLiveData<ArrayList<UserModel>>()

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
    fun getListSach() {
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                SachDAO().getListSachChuaThuHoi {
                    mListSachLiveData.value = it
                }
            },
            onPostExecute = { })
    }
}
