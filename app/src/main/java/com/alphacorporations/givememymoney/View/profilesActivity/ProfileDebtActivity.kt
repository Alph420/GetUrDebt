package com.alphacorporations.givememymoney.View.profilesActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant
import com.alphacorporations.givememymoney.Constant.DEBT_ID
import com.alphacorporations.givememymoney.Constant.DEVISE
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_DEBT_RESIZE
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_profile_debt.*


class ProfileDebtActivity : AppCompatActivity() {


    //GLOBAL VARIABLES
    var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
    private var db = Firebase.firestore
    private val TAG = "DocSnippets"
    lateinit var debt: Debt
    var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_debt)

        db = FirebaseFirestore.getInstance()

        getDebtFromFirebase()

        debt_avatar_profile!!.setOnClickListener { selectAvatar() }
        save_debt!!.setOnClickListener { save() }
    }

    fun getDebtFromFirebase() {
        val docRef = db.collection(FIREBASE_COLLECTION_ID).document(DEBT_ID)
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        initDebtProfile(Debt(
                                DEBT_ID,
                                document.data?.get("img").toString(),
                                document.data?.get("name").toString(),
                                document.data?.get("reason").toString(),
                                document.data?.get("date").toString(),
                                document.data?.get("amount").toString().toLong()
                        ))
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

    }

    /**
     *
     * @param debtFromFirebase Debt
     */
    private fun initDebtProfile(debtFromFirebase: Debt) {
        debt = debtFromFirebase
        setDebtImg()
        name_debt.setText(debt.name)
        amount_debt.setText(debt.amount.toString().plus(DEVISE))
        reason_debt.setText(debt.reason)
    }


    private fun setDebtImg() {
        /** IF IMG FROM FIREBASE EXIST DRAW IT ELSE DRAW A LITTLE PHOTO **/
        mStorageRef
                .child("debt_images/$DEBT_ID$FIREBASE_IMG_DEBT_RESIZE")
                .downloadUrl
                .addOnSuccessListener {
                    Glide
                            .with(this)
                            .load(it)
                            .transform(RoundedCornersTransformation(Constant.FIREBASE_IMG_RADIUS, Constant.FIREBASE_IMG_MARGIN))
                            .into(debt_avatar_profile)
                }
                .addOnFailureListener { debt_avatar_profile.setImageResource(R.drawable.ic_add_a_photo) }

    }


    private fun selectAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE)
    }

    /**
     *
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data!!.data
            Glide
                    .with(this)
                    .load(imageUri)
                    .transform(RoundedCornersTransformation(Constant.FIREBASE_IMG_RADIUS, Constant.FIREBASE_IMG_MARGIN))
                    .into(debt_avatar_profile)
            saveImgDebtOnFirebaseStorage()
        }
    }

    private fun save() {
        val data = hashMapOf(
                "name" to name_debt.text.toString(),
                "amount" to amount_debt.text.toString(),
                "reason" to reason_debt.text.toString(),
                "date" to debt.date,
                "isDebt" to true

        )

        db.collection(FIREBASE_COLLECTION_ID).document(DEBT_ID)
                .set(data)
                .addOnSuccessListener { finish() }
                .addOnFailureListener { e ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error adding document", e)
                    Toast.makeText(this, "Erreur dans l'enregistrement de la dette", Toast.LENGTH_LONG).show()
                }
        finish()
    }

    private fun saveImgDebtOnFirebaseStorage() {
        val ref: StorageReference = mStorageRef.child("debt_images/$DEBT_ID")
        imageUri?.let { ref.putFile(it) }
    }
}