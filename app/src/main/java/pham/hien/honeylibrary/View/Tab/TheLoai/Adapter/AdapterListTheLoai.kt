package pham.hien.honeylibrary.View.Tab.TheLoai.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import pham.hien.honeylibrary.View.Tab.TheLoai.Dialog.SuaTheLoaiDialog
import pham.hien.honeylibrary.Model.TheLoai
import pham.hien.honeylibrary.R

class AdapterListTheLoai(
    context: Context,
    listTheLoai: ArrayList<TheLoai>,
    click: ((TheLoai)-> Unit)
): RecyclerView.Adapter<AdapterListTheLoai.ViewItemTheLoai>(), Filterable{

    private val mContext = context
    private var mListTheLoai:ArrayList<TheLoai> = listTheLoai
    private val onClickTheLoai= click
    private var mListTheLoaiOld: ArrayList<TheLoai> = listTheLoai

    fun setList(list: ArrayList<TheLoai>){
        mListTheLoai = list
        mListTheLoaiOld = list
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemTheLoai {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_the_loai, parent, false)
        return ViewItemTheLoai(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewItemTheLoai, position: Int) {
        val theLoai = mListTheLoai[position]

        holder.layoutItemTheLoai.setOnClickListener{
            onClickTheLoai(theLoai)
        }
        holder.tvTenTheLoai.text = theLoai.tenTheLoai
        holder.tvSttTheLoai.text = theLoai.maTheLoai.toString()
        Log.d("ccccc", "old: ${mListTheLoaiOld.toString()}")

    }

    override fun getItemCount(): Int {
        return mListTheLoai.size
    }

    class ViewItemTheLoai(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvSttTheLoai: TextView
        val tvTenTheLoai: TextView
        val layoutItemTheLoai: RelativeLayout

        init {
            tvSttTheLoai = itemView.findViewById(R.id.tv_stt_the_loai)
            tvTenTheLoai = itemView.findViewById(R.id.tv_ten_the_loai)
            layoutItemTheLoai = itemView.findViewById(R.id.layout_item_the_loai)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch = constraint.toString()
                if (strSearch.isEmpty()) {
                    mListTheLoai = mListTheLoaiOld
                } else {
                    var list = ArrayList<TheLoai>()
                    for (theLoai in mListTheLoaiOld) {
                        if (theLoai.tenTheLoai.lowercase().contains(strSearch.toString().lowercase())) {
                            list.add(theLoai)
                        }
                    }
                    mListTheLoai = list
                    Log.d("ccccc", "performFiltering: ${mListTheLoai.toString()}")
                }
                val filterResults = FilterResults()
                filterResults.values = mListTheLoai
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mListTheLoai = results?.values as ArrayList<TheLoai>
                notifyDataSetChanged()
            }
        }
    }
}