package pham.hien.honeylibrary.ViewModel

import android.annotation.SuppressLint
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SplashViewModel : ViewModel() {

    private var timer: Timer? = null

    val loadingSplashDefaultLiveData = MutableLiveData<Int>()

    fun loadProgressBarSplashDefault() {
        val handler = Handler()
        val progress = intArrayOf(0)

        @SuppressLint("SetTextI18n")
        val update = Runnable {
            if (progress[0] < 200) {
                progress[0]++
                loadingSplashDefaultLiveData.value = progress[0]
            } else {
                timer?.cancel()

            }
        }
        timer?.cancel()
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 100, 10)
    }

}