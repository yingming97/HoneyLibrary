package pham.hien.honeylibrary.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.DoanhThuDAO
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.DoanhThu
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Utils.launch
import java.util.*
import kotlin.collections.ArrayList

class ThongKeViewModel: ViewModel() {
    val mListDoanhThuLiveData = MutableLiveData<ArrayList<DoanhThu>>()
    val mListSachThieuLiveData = MutableLiveData<ArrayList<Sach>>()

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

    fun getListSachThieu(){
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                SachDAO().getListSachThieu {
                    mListSachThieuLiveData.value = it
                }
            },
            onPostExecute = { })
    }

}