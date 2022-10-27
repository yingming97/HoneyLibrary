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
import pham.hien.honeylibrary.Model.Sach
import pham.hien.honeylibrary.Model.SachThue
import pham.hien.honeylibrary.R
import pham.yingming.honeylibrary.Dialog.FailDialog

class AdapterListSachThueSua(
    context: Context,
    listSachThue: ArrayList<SachThue>,
    private var tongSoLuong: Int,
    private val themSoluong: (SachThue) -> Unit,
    private val giamSoLuong: (SachThue) -> Unit,
    private val remove: (SachThue) -> Unit,
) :
    RecyclerView.Adapter<AdapterListSachThueSua.ViewItemSachThue>() {

    private val TAG = "YingMing"
    private var mContext: Context = context
    private var mListSachThue: ArrayList<SachThue> = listSachThue

    fun setTongSoLuong(tongSo: Int) {
        tongSoLuong = tongSo
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListSachThue(listSachThue: ArrayList<SachThue>) {
        mListSachThue = listSachThue
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemSachThue {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sach_phieu_muon_sua, parent, false)
        return ViewItemSachThue(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewItemSachThue, position: Int) {
        val sachThue = mListSachThue[position]
        Glide.with(mContext).load(sachThue.biaSach).placeholder(R.drawable.ic_book_default)
            .into(holder.imv_book)
        holder.tv_name.text = sachThue.tenSach
        holder.tv_so_luong.text = sachThue.soLuong.toString()
        Log.d(TAG, "tongSoLuong:$tongSoLuong ")
        holder.imv_add.setOnClickListener {
            if (tongSoLuong < 5) {
                tongSoLuong += 1
                sachThue.soLuong += 1
                holder.tv_so_luong.text = sachThue.soLuong.toString()
                themSoluong(sachThue)
            } else {
                FailDialog(mContext, "Lỗi", "Số lượng đã đạt tối đa").show()
            }
        }
        holder.imv_minus.setOnClickListener {
            if (sachThue.soLuong > 1) {
                tongSoLuong -= 1
                sachThue.soLuong -= 1
                holder.tv_so_luong.text = sachThue.soLuong.toString()
                giamSoLuong(sachThue)
            } else {
                FailDialog(mContext, "Lỗi", "Số lượng đã đạt tối thiểu").show()
            }
        }
        holder.imv_remove.setOnClickListener {
            remove(sachThue)
            mListSachThue.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mListSachThue.size
    }

    class ViewItemSachThue(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imv_book: ImageView
        val imv_minus: ImageView
        val imv_add: ImageView
        val imv_remove: ImageView
        val tv_name: TextView
        val tv_so_luong: TextView

        init {
            imv_book = itemView.findViewById(R.id.imv_book)
            imv_minus = itemView.findViewById(R.id.imv_minus)
            imv_remove = itemView.findViewById(R.id.imv_remove)
            imv_add = itemView.findViewById(R.id.imv_add)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong)
        }
    }
}