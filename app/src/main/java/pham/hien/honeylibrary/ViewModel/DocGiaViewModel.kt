package pham.hien.honeylibrary.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.Utils.launch

class DocGiaViewModel : ViewModel(){
    var mListDocGiaLiveData = MutableLiveData<ArrayList<UserModel>>()

    fun getListDocGia() {
        viewModelScope.launch(
            onPreExecute = {},
            doInBackground = {
                UserDAO().getListUser {
                    mListDocGiaLiveData.value = it
                }
            },
            onPostExecute = {}
        )
    }
}