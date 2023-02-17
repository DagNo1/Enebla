package tg.dagno2.enebla

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendListAdapter(private val users: ArrayList<User>):
    RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent,false)
        return FriendViewHolder(itemView)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentItem = users[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName
        holder.phone.text = currentItem.phoneNumber
    }
    class FriendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val firstName = itemView.findViewById<TextView>(R.id.first_name)
        val lastName = itemView.findViewById<TextView>(R.id.last_name)
        val phone = itemView.findViewById<TextView>(R.id.phone_number)
    }
}