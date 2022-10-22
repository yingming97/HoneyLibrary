package pham.hien.honeylibrary.ViewModel.Main

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Utils.launch

class SachViewModel : ViewModel() {

    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()
    val mListSachThuHoiLiveData = MutableLiveData<ArrayList<Sach>>()
    val mListAllSachLiveData = MutableLiveData<ArrayList<Sach>>()

    fun getListSach() {
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                SachDAO().getListSachChuaThuHoi {
                    mListSachLiveData.value = it
                }
                SachDAO().getListSachDaThuHoi {
                    mListSachThuHoiLiveData.value = it
                }
            },
            onPostExecute = { })
    }

    fun getListAllSach() {
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                SachDAO().getListAllSach {
                    mListAllSachLiveData.value = it
                }

            },
            onPostExecute = { })
    }
}
