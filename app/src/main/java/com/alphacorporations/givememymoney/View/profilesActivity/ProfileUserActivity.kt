package com.alphacorporations.givememymoney.View.profilesActivity

import android.R.attr.radius
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_MARGIN
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_RADIUS
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_RESIZE
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.startActivity.LoginActivity
import com.alphacorporations.givememymoney.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_profile_user.*


/**
Created by Alph4 le 19/07/2020.
Projet: Give Me My Money
 **/
class ProfileUserActivity : AppCompatActivity() {

    //GLOBAL VARIABLES
    var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
    private val db = Firebase.firestore
    private var colletions: CollectionReference = db.collection(FIREBASE_COLLECTION_ID)
    lateinit var userFirebaseID: String
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        userFirebaseID = FirebaseAuth.getInstance().currentUser!!.uid

        getUserData()

        save_user_profil.setOnClickListener { saveUserChange() }
        log_out_user_profil.setOnClickListener { logOut() }
        user_avatar.setOnClickListener { setAvatar() }

    }

    private fun getUserData() {
        val docRef = colletions.document("UserID")
        docRef.get()
                .addOnSuccessListener { document ->
                    initProfie(User(
                            document.data?.get("userAvatarName").toString(),
                            document.data?.get("Country").toString(),
                            document.data?.get("birthDate").toString(),
                            document.data?.get("email").toString(),
                            document.data?.get("pseudo").toString()
                    ))
                }
    }

    private fun saveUserChange() {
        saveImgOnFirebaseStorage()

        val data = hashMapOf(
                "userAvatarName" to imageUri.toString(),
                "pseudo" to user_name.text.toString(),
                "email" to user_email.text.toString(),
                "birthDate" to birthday_user.text.toString(),
                "Country" to user_country.text.toString()
        )

        db.collection(FIREBASE_COLLECTION_ID).document("UserID")
                .set(data)
                .addOnSuccessListener { finish() }
                .addOnFailureListener { e ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error adding document", e)
                    Toast.makeText(this, "Erreur dans l'enregistrement de la dette", Toast.LENGTH_LONG)
                }
    }

    fun saveImgOnFirebaseStorage() {
        val riversRef: StorageReference = mStorageRef.child("images/$FIREBASE_COLLECTION_ID")
        imageUri?.let { riversRef.putFile(it) }
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    fun initProfie(user: User) {
        setUserAvatar(user)
        user_name.setText(user.pseudo)
        user_email.setText(user.email)
        birthday_user.setText(user.birthDate)
        user_country.setText(user.country)
    }

    fun setUserAvatar(user: User) {
        if (user.userAvatarPath.equals("null")) user_avatar.setImageResource(R.drawable.ic_add_a_photo)
        else {
            mStorageRef.child("images/$FIREBASE_COLLECTION_ID$FIREBASE_IMG_RESIZE").downloadUrl.addOnSuccessListener { Glide.with(this).load(it).transform(RoundedCornersTransformation(FIREBASE_IMG_RADIUS, FIREBASE_IMG_MARGIN)).into(user_avatar) }
        }
    }

    fun setAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.data!!
                Glide.with(this).load(imageUri).transform(RoundedCornersTransformation(FIREBASE_IMG_RADIUS, FIREBASE_IMG_MARGIN)).into(user_avatar)
            }
        }
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