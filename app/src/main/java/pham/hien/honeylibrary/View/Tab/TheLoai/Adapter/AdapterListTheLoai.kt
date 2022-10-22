package pham.hien.honeylibrary.View.Tab.TheLoai.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant
import pham.hien.honeylibrary.Utils.SharedPrefUtils

class AdapterListTheLoai(
    context: Context,
    listTheLoai: ArrayList<TheLoai>,
    click: ((TheLoai) -> Unit),
) : RecyclerView.Adapter<AdapterListTheLoai.ViewItemTheLoai>() {

    private val mContext = context
    private var mListTheLoai: ArrayList<TheLoai> = listTheLoai
    private val onClickTheLoai = click

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<TheLoai>) {
        mListTheLoai = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemTheLoai {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_the_loai, parent, false)
        return ViewItemTheLoai(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemTheLoai, position: Int) {
        val theLoai = mListTheLoai[position]
        val user = SharedPrefUtils.getUserData(mContext)
        holder.layoutItemTheLoai.setOnClickListener {
            if (user?.type == Constant.QUYEN.ADMIN || user?.type == Constant.QUYEN.DOC_GIA) {
                onClickTheLoai(theLoai)
            } else {
                return@setOnClickListener
            }
        }
        holder.tvTenTheLoai.text = theLoai.tenTheLoai
        holder.tvSttTheLoai.text = theLoai.maTheLoai.toString()

    }

    override fun getItemCount(): Int {
        return mListTheLoai.size
    }

    class ViewItemTheLoai(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSttTheLoai: TextView
        val tvTenTheLoai: TextView
        val layoutItemTheLoai: RelativeLayout

        init {
            tvSttTheLoai = itemView.findViewById(R.id.tv_stt_the_loai)
            tvTenTheLoai = itemView.findViewById(R.id.tv_ten_the_loai)
            layoutItemTheLoai = itemView.findViewById(R.id.layout_item_the_loai)
        }
    }
}