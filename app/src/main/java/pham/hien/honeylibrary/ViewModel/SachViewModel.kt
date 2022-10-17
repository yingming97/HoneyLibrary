package pham.hien.honeylibrary.ViewModel.Main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import pham.hien.honeylibrary.FireBase.FireStore.SachDAO
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Utils.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class SachViewModel : ViewModel() {

    val mListSachLiveData = MutableLiveData<ArrayList<Sach>>()

    fun getListSach() {
        viewModelScope.launch(onPreExecute = {},
            doInBackground = {
                 SachDAO().getListSach {
                     mListSachLiveData.value = it
                 }
            },
            onPostExecute = {})
    }
}
