package pham.hien.honeylibrary.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.launch

class NhanVienViewModel : ViewModel() {
    val nhanvienModel = MutableLiveData<ArrayList<UserModel>>()
    fun getAll() {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                UserDAO().getListUserNhanVien() {
                    nhanvienModel.value = it
                }
            },
            onPostExecute = {}
        )
    }
}