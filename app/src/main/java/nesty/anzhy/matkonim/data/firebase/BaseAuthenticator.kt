package nesty.anzhy.matkonim.data.firebase

import com.google.firebase.auth.FirebaseUser

interface BaseAuthenticator {
    //this class will implement all the basic authentication api calls. Using this method of abstraction

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    fun signOut(): FirebaseUser?

    fun getUser(): FirebaseUser?

    suspend fun sendPasswordReset(email: String)
}
