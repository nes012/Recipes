package nesty.anzhy.matkonim.data.firebase

import com.google.firebase.auth.FirebaseUser

class FakeBaseAuthRepository: BaseAuthRepository {
    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun signOut(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        TODO("Not yet implemented")
    }
}