package com.alphacorporations.givememymoney.View.profilesActivity

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alphacorporations.givememymoney.Constant.CHANNEL_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_MARGIN
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_RADIUS
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_USER_RESIZE
import com.alphacorporations.givememymoney.Constant.NOTIFICATION_ID
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.Utils.CountryComparator
import com.alphacorporations.givememymoney.View.startActivity.LoginActivity
import com.alphacorporations.givememymoney.model.Country
import com.alphacorporations.givememymoney.model.User
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_profile_user.*
import java.util.*


/**
Created by Alph4 le 19/07/2020.
Projet: Give Me My Money
 **/
class ProfileUserActivity : AppCompatActivity() {

    //GLOBAL VARIABLES
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
    private val db = Firebase.firestore
    private var colletions: CollectionReference = db.collection(FIREBASE_COLLECTION_ID)
    private lateinit var userFirebaseID: String
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        adsConfig()
        userFirebaseID = FirebaseAuth.getInstance().currentUser!!.uid

        getUserData()

        save_user_profil.setOnClickListener { saveUserChange() }
        log_out_user_profil.setOnClickListener { logOut() }
        user_avatar.setOnClickListener { setAvatar() }
        user_country.setOnClickListener { changeCountry() }

    }

    private fun getUserData() {
        colletions.document("UserID").get()
                .addOnSuccessListener { document ->
                    initProfile(User(
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
                    Toast.makeText(this, "Erreur dans l'enregistrement de la dette", Toast.LENGTH_LONG).show()
                }
    }

    private fun saveImgOnFirebaseStorage() {
        val ref: StorageReference = mStorageRef.child("images/$FIREBASE_COLLECTION_ID")
        imageUri?.let { ref.putFile(it) }
    }


    private fun initProfile(user: User) {
        setUserAvatar(user)
        user_name.setText(user.pseudo)
        user_email.text = user.email
        birthday_user.setText(user.birthDate)
        user_country.setText(user.country)
    }

    //region AvatarUserPart
    private fun setUserAvatar(user: User) {
        if (user.userAvatarPath.equals("null")) user_avatar.setImageResource(R.drawable.ic_add_a_photo)
        else {
            mStorageRef
                    .child("images/$FIREBASE_COLLECTION_ID$FIREBASE_IMG_USER_RESIZE")
                    .downloadUrl
                    .addOnSuccessListener {
                        Glide
                                .with(this)
                                .load(it)
                                .transform(RoundedCornersTransformation(FIREBASE_IMG_RADIUS, FIREBASE_IMG_MARGIN))
                                .into(user_avatar)
                    }
        }
    }

    private fun setAvatar() {
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
    //endregion


    //region Change User Data
    fun changeCountry() {

        // setup the alert builder
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Choisi ton pays")


        // A collection to store our country object

        // A collection to store our country object
        val countries = mutableListOf<Country>()

        // Get ISO countries, create Country object and
        // store in the collection.

        // Get ISO countries, create Country object and
        // store in the collection.
        val isoCountries = Locale.getISOCountries()
        for (country in isoCountries) {
            val locale = Locale("en", country)
            val iso = locale.isO3Country
            val code = locale.country
            val name = locale.displayCountry
            if ("" != iso && "" != code && "" != name) {
                countries.add(Country(iso, code, name))
            }
        }

        // Sort the country by their name and then display the content
        // of countries collection object.

        // Sort the country by their name and then display the content
        // of countries collection object.
        Collections.sort(countries, CountryComparator())

        for (country in countries) {
           println(country)
        }

    }


    fun changeEmail() {
        //TODO User can update there email adress
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Notification channel name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = "Notification channel description"
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            val notificationManager = getSystemService(NotificationManager::class.java)
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel)
        }


        val notificationManager = NotificationManagerCompat.from(this)

        val notifBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("WATATATATATATATATATATATATATA")
                .setContentText("Contenu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // notificationId est un identificateur unique par notification qu'il vous faut définir

        // notificationId est un identificateur unique par notification qu'il vous faut définir
        notificationManager.notify(NOTIFICATION_ID, notifBuilder.build())
    }

    //endregion

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun adsConfig() {
        MobileAds.initialize(this) { }
        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
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