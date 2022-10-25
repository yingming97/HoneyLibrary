package pham.hien.honeylibrary.View.Support

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.Model.Message
import pham.hien.honeylibrary.R
import java.util.ArrayList

class MessAdapter(lsMess: ArrayList<Message>, mailSent: String) :
    RecyclerView.Adapter<MessAdapter.ViewHolder?>() {
    private val lsMess: ArrayList<Message>
    private val mailSent: String

    init {
        this.lsMess = lsMess
        this.mailSent = mailSent
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.send_mess, null))
        } else {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.re_mess, null))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messenger= lsMess[position]
        holder.tvMess.text = messenger.message
    }

    override fun getItemCount(): Int {
        return lsMess.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (lsMess[position].email == mailSent) {
            VIEW_TYPE_SEND
        } else {
            VIEW_TYPE_RECIEVED
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMess: TextView

        init {
            tvMess = itemView.findViewById(R.id.tv_mess)

        }
    }

    companion object {
        const val VIEW_TYPE_SEND = 1
        const val VIEW_TYPE_RECIEVED = 2
    }
}