package tg.dagno2.enebla

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import tg.dagno2.enebla.databinding.FriendsBinding

class Friends : Fragment(R.layout.friends) {
    private lateinit var binding: FriendsBinding
    private lateinit var database : DatabaseReference
    private lateinit var users: ArrayList<User>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FriendsBinding.bind(view)
        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.setHasFixedSize(true)
        users = arrayListOf<User>()
        getUsersData()
    }

    private fun getUsersData() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(userSnapShot in snapshot.children){
                        val u = userSnapShot.getValue(User::class.java)
                        users.add(u!!)
                    }
                    binding.rcView.adapter = FriendListAdapter(users)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}