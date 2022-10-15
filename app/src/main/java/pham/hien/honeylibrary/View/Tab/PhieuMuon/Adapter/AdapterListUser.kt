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
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R

class AdapterListUser(
    context: Context,
    listUser: ArrayList<UserModel>,
    callback: ((UserModel) -> Unit)
) :
    RecyclerView.Adapter<AdapterListUser.ViewItemDocGia>() {

    private val TAG ="YingMing"
    private var mContext: Context = context
    private var mListUser: ArrayList<UserModel> = listUser
    private var call = callback

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(listUser: ArrayList<UserModel>) {
        mListUser = listUser
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemDocGia {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doc_gia, parent, false)
        return ViewItemDocGia(view)
    }

    override fun onBindViewHolder(holder: ViewItemDocGia, position: Int) {
        Log.d(TAG, "setListUser: ${mListUser.size}")
        val user = mListUser[position]
        Glide.with(mContext).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(holder.imvAvatar)
        holder.tv_name.text = user.name
        holder.tv_sdt.text = user.sdt
        holder.layout_item_doc_gia.setOnClickListener {
            call.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return mListUser.size
    }

    class ViewItemDocGia(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvAvatar: ImageView
        val tv_name: TextView
        val tv_sdt: TextView
        val layout_item_doc_gia: RelativeLayout

        init {
            imvAvatar = itemView.findViewById(R.id.imv_avatar)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_sdt = itemView.findViewById(R.id.tv_sdt)
            layout_item_doc_gia = itemView.findViewById(R.id.layout_item_doc_gia)
        }
    }
}