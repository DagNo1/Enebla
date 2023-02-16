package tg.dagno2.enebla

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import tg.dagno2.enebla.databinding.ProfileBinding

class Profile : Fragment(R.layout.profile) {
//    private lateinit var user: User
    private lateinit var binding: ProfileBinding
    private lateinit var database : DatabaseReference
    private val auth = FirebaseAuth.getInstance()
//    private val storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
//    private val image: Uri = Uri.parse("android.resource://tg.dagno2.enebla//${R.drawable.logo}")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileBinding.bind(view)
        database = FirebaseDatabase.getInstance().getReference("Users")
        getUser()
//        storageReference.putFile(image).addOnSuccessListener {
//            Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show()
//        }
    }
    private fun getUser() {
        try {
            val uid = auth.currentUser?.uid.toString()
            database.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)!!
                    binding.profileName.text = "${user.firstName} ${user.lastName}"
                    hideProgressBar()
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
                }
            })
        } catch(e: Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    private fun hideProgressBar(){
        binding.progressProfile.isVisible = false
        binding.profileName.isVisible = true
    }

}