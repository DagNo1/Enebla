package tg.dagno2.enebla

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import tg.dagno2.enebla.databinding.ProfileBinding
import java.io.File

class Profile : Fragment(R.layout.profile) {
//    private lateinit var user: User
    private lateinit var binding: ProfileBinding
    private lateinit var database : DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    var loaded = false
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }
    private var storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
    private var image: Uri = Uri.parse("android.resource://tg.dagno2.enebla//${R.drawable.logo}")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileBinding.bind(view)
        database = FirebaseDatabase.getInstance().getReference("Users")
        if (!loaded) getUser()
        binding.editProfilePic.setOnClickListener {
            pickImage()
        }
    }
    private fun getUser() {
        try {
            val uid = auth.currentUser?.uid.toString()
            database.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)!!
                    binding.profileName.text = "${user.firstName} ${user.lastName}"
                    getUserProfilePicture()
                }
                override fun onCancelled(error: DatabaseError) {
                    hideProgressBar()
                    Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
                }
            })
        } catch(e: Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    private fun getUserProfilePicture(){
        storageReference = FirebaseStorage.getInstance().reference.child("Users/${auth.currentUser?.uid.toString()}")
        val localFile = File.createTempFile("tempFile","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilePicture.setImageBitmap(bitmap)
            binding.profilePicture.rotation += 90
            loaded = true
            hideProgressBar()
        }.addOnFailureListener{
            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            hideProgressBar()
        }
    }

    private fun pickImage() {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/'"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE){
            image = data?.data!!
            binding.profilePicture.setImageURI(image)
            uploadImage()
        }
    }
    private fun uploadImage(){
        showProgressBar()
        storageReference.putFile(image).addOnSuccessListener {
            Toast.makeText(context,"Profile Picture Updated",Toast.LENGTH_SHORT).show()
            hideProgressBar()
        }
    }
    private fun hideProgressBar(){
        binding.progressProfile.isVisible = false
        binding.profileName.isVisible = true
    }
    private fun showProgressBar(){
        binding.progressProfile.isVisible = true
        binding.profileName.isVisible = false
    }
}