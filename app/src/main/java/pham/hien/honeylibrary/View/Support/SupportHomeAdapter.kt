package pham.hien.honeylibrary.View.Support

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.SharedPrefUtils

class SupportHomeAdapter(
    arrUser: List<UserModel>,
    var context: Context, callback: (UserModel) -> Unit
) :
    RecyclerView.Adapter<SupportHomeAdapter.ViewHolder>() {

    private var arrUsers = arrUser
    private var call = callback

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView

        init {
            tvName = itemView.findViewById(R.id.tv_name)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = arrUsers[position]
        holder.tvName.text = "${user.name}\n\n${user.email}"

        holder.itemView.setOnClickListener {
            call.invoke(user)
        }
        if (user.email == SharedPrefUtils.getUserData(context)?.email) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }

    }

    override fun getItemCount(): Int {
        return arrUsers.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchLoad(mListUser: List<UserModel>){
        this.arrUsers = mListUser
        notifyDataSetChanged()

    }
}