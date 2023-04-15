package tg.dagno2.enebla.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import tg.dagno2.enebla.CommonFunctions
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//import com.google.firebase.storage.FirebaseStorage
import tg.dagno2.enebla.R
import tg.dagno2.enebla.databinding.ProfileBinding
import tg.dagno2.enebla.entities.User
import tg.dagno2.enebla.login_signup.LogIn
import java.io.File

class Profile : Fragment(R.layout.profile) {
    private lateinit var binding: ProfileBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val auth = FirebaseAuth.getInstance()
    private var image: Uri = Uri.parse("android.resource://tg.dagno2.enebla//${R.drawable.logo}")
    private var storageReference = FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
    var loaded = false

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileBinding.bind(view)
//        if (!loaded) getUser()
//        binding.editProfilePic.setOnClickListener {
//            pickImage()
//        }
        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LogIn::class.java))
        }
    }

//    private fun getUser() {
//        try {
//            val uid = auth.currentUser?.uid.toString()
//            database.child(uid).addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val user = snapshot.getValue(User::class.java)!!
//                    binding.profileName.text = user.userName
//                    getUserProfilePicture()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//                }
//            })
//        } catch (e: Exception) {
//            CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getUserProfilePicture() {
//        storageReference =
//            FirebaseStorage.getInstance().reference.child("Users/${auth.currentUser?.uid.toString()}")
//        val localFile = File.createTempFile("tempFile", "jpg")
//        storageReference.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            binding.profilePicture.setImageBitmap(bitmap)
//            binding.profilePicture.rotation += 90
//            loaded = true
//            CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//        }.addOnFailureListener {
//            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//            CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//        }
//    }
//
//    private fun pickImage() {
//        try {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/'"
//            startActivityForResult(intent, IMAGE_REQUEST_CODE)
//        } catch (e: Exception) {
//            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == IMAGE_REQUEST_CODE) {
//            image = data?.data!!
//            binding.profilePicture.setImageURI(image)
//            uploadImage()
//        }
//    }
//
//    private fun uploadImage() {
//        CommonFunctions.showProgressBar(binding.progressProfile, binding.profileName)
//        storageReference.putFile(image).addOnSuccessListener {
//            Toast.makeText(context, "Profile Picture Updated", Toast.LENGTH_SHORT).show()
//            CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//        }
//    }

}