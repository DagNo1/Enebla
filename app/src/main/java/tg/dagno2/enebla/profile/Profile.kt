package tg.dagno2.enebla.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tg.dagno2.enebla.CommonFunctions
import tg.dagno2.enebla.R
import tg.dagno2.enebla.databinding.ProfileBinding
import tg.dagno2.enebla.entities.User
import tg.dagno2.enebla.login_signup.LogIn
import java.io.File

class Profile : Fragment(R.layout.profile) {
    private lateinit var binding: ProfileBinding
//    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private var database: DatabaseReference = FirebaseDatabase
        .getInstance("https://enebla-c65cd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
    private val auth = FirebaseAuth.getInstance()
//    private var image: Uri = Uri.parse("android.resource://tg.dagno2.enebla//${R.drawable.logo}")
    private var storageReference = FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
    var loaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileBinding.bind(view)
        if (!loaded) getUser()
        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LogIn::class.java))
        }
    }
//  Todo: Move this method to the setting activity
//    private fun pickImage() {
//        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
//            if (uri != null) {
//                image = uri
//                uploadImage()
//            } else {
////                Log.d("PhotoPicker", "No media selected")
//            }
//        }
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
    private  fun getUser() {
        CommonFunctions.showProgressBar(binding.progressProfile, binding.profileName)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    val uid = auth.currentUser?.uid.toString()
                    database.child(uid).get().addOnSuccessListener { dataSnapshot ->
                        if (dataSnapshot.exists()) {
                            val user = dataSnapshot.getValue(User::class.java)!!
                            binding.profileName.text = user.userName
                        } else {
                            Toast.makeText(activity, "Couldn't find profile picture.", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(activity, "Error reading data", Toast.LENGTH_LONG).show()
                    }
                    getUserProfilePicture()
                    CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun getUserProfilePicture() {
        storageReference =
            FirebaseStorage.getInstance().reference.child("Users/${auth.currentUser?.uid.toString()}")
        val localFile = File.createTempFile("tempFile", "jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilePicture.setImageBitmap(bitmap)
//            binding.profilePicture.rotation += 90
            loaded = true
        }.addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

//    private fun uploadImage() {
//        binding.profilePicture.setImageURI(image)
//        CommonFunctions.showProgressBar(binding.progressProfile, binding.profileName)
//        storageReference.putFile(image).addOnSuccessListener {
//            Toast.makeText(context, "Profile Picture Updated", Toast.LENGTH_SHORT).show()
//            CommonFunctions.hideProgressBar(binding.progressProfile, binding.profileName)
//        }
//    }

}