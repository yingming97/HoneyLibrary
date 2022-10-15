package pham.hien.honeylibrary.ViewModel.Main


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.TheLoaiDAO
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.Utils.launch

class TheLoaiViewModel : ViewModel() {

    val mListTheLoaiLiveData = MutableLiveData<ArrayList<TheLoai>>()

    fun getListTheLoai(){
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                TheLoaiDAO().getListTheLoai{
                    mListTheLoaiLiveData.value = it
                }
            },
            onPostExecute = {
            }
        )
    }
}
