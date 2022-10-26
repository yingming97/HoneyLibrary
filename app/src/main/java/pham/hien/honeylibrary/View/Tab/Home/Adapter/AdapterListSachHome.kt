package pham.hien.honeylibrary.View.Tab.Home.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.R

class AdapterListSachHome(
    context: Context,
    listSach: ArrayList<Sach>,
    callback: ((Sach) -> Unit),
) :
    RecyclerView.Adapter<AdapterListSachHome.ViewItemSach>() {

    private val TAG = "YingMing"
    private var mContext: Context = context
    private var mListSach: ArrayList<Sach> = listSach
    private var call = callback

    @SuppressLint("NotifyDataSetChanged")
    fun setListSach(listSach: ArrayList<Sach>) {
        mListSach = listSach
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemSach {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sach_moi, parent, false)
        return ViewItemSach(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemSach, position: Int) {
        val sach = mListSach[position]
//        val animation = AnimationUtils.loadAnimation(mContext, R.anim.blink)
//        holder.imv_new.startAnimation(animation)
        Glide.with(mContext).load(sach.anhBia).placeholder(R.drawable.ic_book_default)
            .into(holder.imvBook)
        holder.tv_name.text = sach.tenSach
        holder.layoutItemSach.setOnClickListener {
            call(sach)
        }
    }

    override fun getItemCount(): Int {
        return mListSach.size
    }

    class ViewItemSach(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvBook: ImageView
        val imv_new: ImageView
        val tv_name: TextView
        val tv_gioi_thieu: TextView
        val layoutItemSach: RelativeLayout

        init {
            imvBook = itemView.findViewById(R.id.imv_book)
            imv_new = itemView.findViewById(R.id.imv_new)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_gioi_thieu = itemView.findViewById(R.id.tv_gioi_thieu)
            layoutItemSach = itemView.findViewById(R.id.layout_item_sach)
        }
    }
}