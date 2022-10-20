package pham.hien.honeylibrary.View.Tab.Option.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import okhttp3.Interceptor.Companion.invoke
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R

class AdapterListDocGia(
    context: Context,
    listDocGia: ArrayList<UserModel>,
    click: ((UserModel) -> Unit)

) : RecyclerView.Adapter<AdapterListDocGia.ViewItemDocGia>() {
    private var mContext: Context = context
    private var mListDocGia: ArrayList<UserModel> = listDocGia
    private var onClick = click

    @SuppressLint("NotifyDataSetChanged")
    fun setListDocGia(listDocGia: ArrayList<UserModel>) {
        mListDocGia = listDocGia
        notifyDataSetChanged()
    }

    class ViewItemDocGia(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvAvatar: ImageView
        val tvName: TextView
        val tvSdt: TextView
        val layout_itemDocgia: RelativeLayout

        init {
            imvAvatar = itemView.findViewById(R.id.imv_avatar)
            tvName = itemView.findViewById(R.id.tv_name)
            tvSdt = itemView.findViewById(R.id.tv_sdt)
            layout_itemDocgia = itemView.findViewById(R.id.layout_item_doc_gia)
        }
    }

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemDocGia {
    val view =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nhanvien, parent, false)
    return ViewItemDocGia(view)
}

    override fun onBindViewHolder(holder: ViewItemDocGia, position: Int) {
        val user = mListDocGia[position]
        Glide.with(mContext).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(holder.imvAvatar)
        holder.tvName.text = user.name
        holder.tvSdt.text = user.sdt
        holder.layout_itemDocgia.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount(): Int {
        return mListDocGia.size
    }

}