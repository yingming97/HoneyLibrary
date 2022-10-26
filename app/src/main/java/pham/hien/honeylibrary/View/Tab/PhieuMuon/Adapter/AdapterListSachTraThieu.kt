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
import pham.yingming.honeylibrary.Dialog.FailDialog

class AdapterListSachTraThieu(
    context: Context,
    listSachThue: ArrayList<SachThue>,
    private val themSoLuong: (Int, Int) -> Unit,
    private val giamSoLuong: (Int, Int) -> Unit,
) :
    RecyclerView.Adapter<AdapterListSachTraThieu.ViewItemSachThue>() {

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
            .inflate(R.layout.item_sach_tra_thieu, parent, false)
        return ViewItemSachThue(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemSachThue, position: Int) {
        val sachThue = mListSachThue[position]
        var soLuong = 0
        Glide.with(mContext).load(sachThue.biaSach).placeholder(R.drawable.ic_book_default)
            .into(holder.imv_book)
        holder.tv_name.text = sachThue.tenSach
        holder.tv_so_luong.text = "0"
        holder.imv_add.setOnClickListener {
            Log.d(TAG, "soLuong: ${sachThue.soLuong}")
            if (soLuong < sachThue.soLuong) {
                soLuong += 1
                holder.tv_so_luong.text = soLuong.toString()
                themSoLuong(sachThue.maSach, soLuong)
            } else {
                FailDialog(mContext, "Lỗi", "Nhiều hơn sách đã mượn").show()
            }
        }
        holder.imv_minus.setOnClickListener {
            Log.d(TAG, "soLuong: ${sachThue.soLuong}")
            if (soLuong > 0) {
                soLuong -= 1
                holder.tv_so_luong.text = soLuong.toString()
                giamSoLuong(sachThue.maSach, soLuong)
            } else {
                FailDialog(mContext, "Lỗi", "Số lượng không được nhỏ hơn 0").show()
            }
        }
    }

    override fun getItemCount(): Int {
        return mListSachThue.size
    }

    class ViewItemSachThue(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imv_book: ImageView
        val imv_minus: ImageView
        val imv_add: ImageView
        val tv_name: TextView
        val tv_so_luong: TextView

        init {
            imv_book = itemView.findViewById(R.id.imv_book)
            imv_minus = itemView.findViewById(R.id.imv_minus)
            imv_add = itemView.findViewById(R.id.imv_add)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong)
        }
    }
}