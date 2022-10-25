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
    callback: ((UserModel) -> Unit),
) :
    RecyclerView.Adapter<AdapterListUser.ViewItemDocGia>() {

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
        val user = mListUser[position]
        Glide.with(mContext).load(user.avatar).placeholder(R.drawable.ic_avatar_default)
            .into(holder.imvAvatar)
        holder.tvName.text = user.name
        holder.tvNumber.text = "SĐT: ${user.sdt}"
        holder.itemView.setOnClickListener {
            call.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        Log.d("BXT", "getItemCount ${mListUser.size}")
        return mListUser.size
    }

    class ViewItemDocGia(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imvAvatar: ImageView
        val tvName: TextView
        val tvNumber: TextView

        init {
            imvAvatar = itemView.findViewById(R.id.imv_user_avatar)
            tvName = itemView.findViewById(R.id.tv_name_reader)
            tvNumber = itemView.findViewById(R.id.tv_phone_number)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListDocgia(listUser: ArrayList<UserModel>){
        mListUser = listUser
        notifyDataSetChanged()
    }
}