package pham.hien.honeylibrary.View.Tab.Home.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.R

class SachMuonNhieuAdapter(
    private val mContext: Context,
    private var lisSach: ArrayList<Sach>,
) : RecyclerView.Adapter<SachMuonNhieuAdapter.HomeViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<Sach>) {
        this.lisSach = list
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_book: ImageView = itemView.findViewById(R.id.img_vp2)
        val tv_ten_sach: TextView = itemView.findViewById(R.id.tv_ten_sach)
        val tv_gioi_thieu: TextView = itemView.findViewById(R.id.tv_gioi_thieu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_flipper_layout, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val sach = lisSach[position]
        Glide.with(mContext).load(sach.anhBia).placeholder(R.drawable.ic_book_default)
            .into(holder.img_book)
        holder.tv_ten_sach.text = sach.tenSach
        holder.tv_gioi_thieu.text = sach.gioiThieu
    }
    override fun getItemCount(): Int {
        return lisSach.size
    }
}