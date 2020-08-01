package com.alphacorporations.givememymoney.View.profilesActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.AddDebtActivity
import com.alphacorporations.givememymoney.View.startActivity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile_user.*
import java.io.File


/**
Created by Alph4 le 19/07/2020.
Projet: Give Me My Money
 **/
class ProfileUserActivity : AppCompatActivity() {

    lateinit var mStorageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        mStorageRef = FirebaseStorage.getInstance().getReference()

        val user = FirebaseAuth.getInstance().currentUser

        initProfie(user)

        log_out_user_profil.setOnClickListener { logOut() }
        user_avatar.setOnClickListener { setAvatar() }

    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    fun initProfie(user: FirebaseUser?) {
        user_avatar.setImageResource(R.drawable.ic_add_a_photo)
        user_name.setText(user?.displayName.toString())
        user_email.setText(user?.email)
        birthday_user.setText("")
        user_country.setText("")
    }

    fun setAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, ""), AddDebtActivity.SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var imageUri = data!!.data
            if (imageUri != null) imageUri.path?.let { saveImgOnFirebaseStorage(it) }
            user_avatar!!.setImageURI(imageUri)
        }
    }

    fun saveImgOnFirebaseStorage(imageUri: String) {

        val file: Uri = Uri.fromFile(File(imageUri))
        val riversRef: StorageReference = mStorageRef.child("images/")

        riversRef.putFile(file)
                .addOnSuccessListener { taskSnapshot -> // Get a URL to the uploaded content
                    val downloadUrl: Uri? = taskSnapshot.uploadSessionUri
                    println("SUCCESS")

                }
                .addOnFailureListener {
                    // Handle unsuccessful uploads
                    println("FAILED")
                }
    }

    companion object {
        const val SELECT_PICTURE = 1
    }


    override fun onResume() {
        super.onResume()
        println("Resume")
    }

    override fun onStart() {
        super.onStart()
        println("Start")
    }
}