package pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.moneyFormatter

class AdapterListSachThueChiTiet(
    context: Context,
    listSachThue: ArrayList<SachThue>,
) :
    RecyclerView.Adapter<AdapterListSachThueChiTiet.ViewItemSachThue>() {

    private val TAG = "YingMing"
    private var mContext: Context = context
    private var mListSachThue: ArrayList<SachThue> = listSachThue

    @SuppressLint("NotifyDataSetChanged")
    fun setListSachThue(listSachThue: ArrayList<SachThue>) {
        mListSachThue = listSachThue
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemSachThue {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sach_phieu_muon_chi_tiet, parent, false)
        return ViewItemSachThue(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemSachThue, position: Int) {
        val sachThue = mListSachThue[position]
        Glide.with(mContext).load(sachThue.biaSach).placeholder(R.drawable.ic_book_default)
            .into(holder.imv_book)
        holder.tv_name.text = sachThue.tenSach
        holder.tv_so_luong.text = "Số lượng: ${sachThue.soLuong}"
        holder.tv_gia_thue.text = "Giá thuê: ${moneyFormatter(sachThue.giaThue)}"
        holder.tv_gia_sach.text = "Giá sách: ${moneyFormatter(sachThue.giaSach)}"
    }

    override fun getItemCount(): Int {
        return mListSachThue.size
    }

    class ViewItemSachThue(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imv_book: ImageView
        val tv_name: TextView
        val tv_gia_thue: TextView
        val tv_so_luong: TextView
        val tv_gia_sach: TextView

        init {
            imv_book = itemView.findViewById(R.id.imv_book)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_gia_thue = itemView.findViewById(R.id.tv_gia_thue)
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong)
            tv_gia_sach = itemView.findViewById(R.id.tv_gia_sach)
        }
    }
}