package tg.dagno2.enebla

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import tg.dagno2.enebla.databinding.ProfileBinding

class Profile : Fragment(R.layout.profile) {
    private lateinit var binding: ProfileBinding

    private val auth = FirebaseAuth.getInstance()
    private val storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
    private val image: Uri = Uri.parse("android.resource://tg.dagno2.enebla//${R.drawable.ic_profile}")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileBinding.bind(view)
        storageReference.putFile(image).addOnSuccessListener {
            Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show()
        }
    }
}