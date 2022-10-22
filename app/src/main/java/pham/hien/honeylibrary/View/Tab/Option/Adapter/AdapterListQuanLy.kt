package pham.hien.honeylibrary.View.Tab.Option.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant

class AdapterListQuanLy(
    context: Context,
    listQuanly: ArrayList<UserModel>,
    Call: ((UserModel) -> Unit),

    ) : RecyclerView.Adapter<AdapterListQuanLy.ViewItemQuanLy>() {
    private var mContext: Context = context
    private var mListQuanLy: ArrayList<UserModel> = listQuanly
    private var call = Call

    @SuppressLint("NotifyDataSetChanged")
    fun setListQuanLy(listQuanLy: ArrayList<UserModel>) {
        mListQuanLy = listQuanLy
        notifyDataSetChanged()
    }

    class ViewItemQuanLy(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvAvatar: ImageView
        val tvName: TextView
        val tvSdt: TextView
        val tv_quyen: TextView
        val layout_item_nhanvien: RelativeLayout

        init {
            imvAvatar = itemView.findViewById(R.id.image_Nhanvien)
            tvName = itemView.findViewById(R.id.tv_tenNhanvien)
            tvSdt = itemView.findViewById(R.id.tv_sdtNhanvien)
            tv_quyen = itemView.findViewById(R.id.tv_quyen)
            layout_item_nhanvien = itemView.findViewById(R.id.layout_item_nhanvien)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemQuanLy {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_nhanvien, parent, false)
        return ViewItemQuanLy(view)
    }

    override fun onBindViewHolder(holder: ViewItemQuanLy, position: Int) {
        val user = mListQuanLy[position]
        Glide.with(mContext).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(holder.imvAvatar)
        holder.tvName.text = user.name
        holder.tvSdt.text = user.sdt
        when (user.type) {
            Constant.QUYEN.THU_THU -> holder.tv_quyen.text = "Thủ thư"
            Constant.QUYEN.ADMIN -> holder.tv_quyen.text = "Quản lý"
        }
        holder.layout_item_nhanvien.setOnClickListener {
            call.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return mListQuanLy.size
    }

}