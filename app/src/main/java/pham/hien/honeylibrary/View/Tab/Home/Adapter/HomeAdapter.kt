package pham.hien.honeylibrary.View.Tab.Home.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import pham.hien.honeylibrary.Model.ViewFlipper
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.View.Custom.ShowMoreTextView

class HomeAdapter(private var arrViewFlipper: ArrayList<ViewFlipper>, private var viewPager2: ViewPager2)
    :RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.img_vp2)
        val textView: ShowMoreTextView = itemView.findViewById(R.id.tv_showText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_flipper_layout, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val viewFlip = arrViewFlipper[position]
        holder.imageView.setImageResource(viewFlip.image)
        holder.textView.text = viewFlip.title
        holder.textView.apply {
            setShowingLine(2)
            setShowLessTextColor(Color.GRAY)
            setShowMoreTextColor(Color.GRAY)
            addShowLessText("Ẩn bớt")
            addShowMoreText("Xem Thêm")
        }

        if(position == arrViewFlipper.size-1){
            viewPager2.post(runnable)
        }
    }

    private val runnable = Runnable{
        arrViewFlipper.addAll(arrViewFlipper)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrViewFlipper.size
    }
}