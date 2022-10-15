package pham.hien.honeylibrary.View.Tab.PhieuMuon.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.PhieuMuon
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.moneyFormatter

class AdapterListPhieuDaTra(
    context: Context,
    listPhieuMuon: ArrayList<PhieuMuon>,
    callback: ((PhieuMuon) -> Unit),
) :
    RecyclerView.Adapter<AdapterListPhieuDaTra.ViewItemPhieuMuon>() {

    private val TAG = "YingMing"
    private var mContext: Context = context
    private var mListPhieuMuon: ArrayList<PhieuMuon> = listPhieuMuon
    private var call = callback

    @SuppressLint("NotifyDataSetChanged")
    fun setListPhieuMuon(listPhieuMuon: ArrayList<PhieuMuon>) {
        mListPhieuMuon = listPhieuMuon
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemPhieuMuon {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phieu_da_tra, parent, false)
        return ViewItemPhieuMuon(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemPhieuMuon, position: Int) {
        val phieuMuon = mListPhieuMuon[position]
        Glide.with(mContext).load(phieuMuon.photoDocGia)
            .placeholder(R.drawable.ic_user_photo_default).into(holder.imv_avatar)
        holder.tv_ma_phieu.text = "Mã: ${phieuMuon.maPhieuMuon}"
        holder.tv_name.text = phieuMuon.tenDocGia
        holder.tv_thanh_tien.text = "Thành tiền: ${moneyFormatter(phieuMuon.tongTien)}"
        holder.tv_so_luong.text = "Số lượng: ${phieuMuon.soLuong}"
        holder.tv_trang_thai.text = "Trạng thái: ${phieuMuon.trangThai}"
        holder.layout_item_phieu.setOnClickListener {
            call(phieuMuon)
        }
    }

    override fun getItemCount(): Int {
        return mListPhieuMuon.size
    }

    class ViewItemPhieuMuon(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imv_avatar: ImageView
        val tv_ma_phieu: TextView
        val tv_name: TextView
        val tv_thanh_tien: TextView
        val tv_so_luong: TextView
        val tv_trang_thai: TextView
        val layout_item_phieu: RelativeLayout

        init {
            imv_avatar = itemView.findViewById(R.id.imv_avatar)
            tv_ma_phieu = itemView.findViewById(R.id.tv_ma_phieu)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_thanh_tien = itemView.findViewById(R.id.tv_thanh_tien)
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong)
            tv_trang_thai = itemView.findViewById(R.id.tv_trang_thai)
            layout_item_phieu = itemView.findViewById(R.id.layout_item_phieu)
        }
    }
}