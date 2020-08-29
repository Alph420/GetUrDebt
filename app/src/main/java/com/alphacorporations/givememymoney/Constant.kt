package com.alphacorporations.givememymoney

object Constant {

    /** For img from device **/
    const val SELECT_PICTURE = 1

    /** For img resize extension from firebase **/
    const val FIREBASE_IMG_USER_RESIZE = "_800x800"
    const val FIREBASE_IMG_DEBT_RESIZE = "_800x800"
    const val FIREBASE_IMG_RADIUS = 20
    const val FIREBASE_IMG_MARGIN = 0

    /** For the user id to set fireStore ID COLLECTIONS **/
    lateinit var FIREBASE_COLLECTION_ID: String

    /** For Notifications **/
    //TODO
    val KEY_NOTIFICATION_ID: String? = "KEY_NOTIFICATION_ID"
    private const val KEY_REPLY = "KEY_REPLY"
    private const val REPLY_ACTION = "REPLY_ACTION"
    const val CHANNEL_ID = "CHANNEL_ID"
    const val NOTIFICATION_ID = 10


    /** id of debt clicked to open on DebtProfil **/
    lateinit var DEBT_ID:String
    var DEVISE:Char = '€'
}