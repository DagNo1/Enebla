package tg.dagno2.enebla.firstTimer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import tg.dagno2.enebla.R

class SlideAdapter(private var images: List<Int>, private var header: List<String>): RecyclerView.Adapter<SlideAdapter.Pager2ViewHolder>() {
    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage: ImageView = itemView.findViewById(R.id.slide_image)
        val itemHeader: TextView = itemView.findViewById(R.id.on_boarding_header_text)
        //        val itemText: TextView = itemView.findViewById(R.id.on_boarding_body_text)
        init {
            itemImage.setOnClickListener { v:View ->
                val position = adapterPosition
                Toast.makeText(itemView.context,"You clicked",Toast.LENGTH_LONG).show()

            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlideAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slide_item,parent,false))
    }

    override fun getItemCount(): Int {
        return images.size
    }
    override fun onBindViewHolder(holder: SlideAdapter.Pager2ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
        holder.itemHeader.text = header[position]
    }
}