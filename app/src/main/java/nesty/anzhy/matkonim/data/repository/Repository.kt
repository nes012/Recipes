package nesty.anzhy.matkonim.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import nesty.anzhy.matkonim.data.database.LocalDataSourceInterface
import nesty.anzhy.matkonim.data.firebase.BaseAuthRepository
import nesty.anzhy.matkonim.data.network.RemoteDataSourceInterface
import javax.inject.Inject

@ActivityRetainedScoped
open class Repository @Inject constructor(
    remoteDataSource: RemoteDataSourceInterface,
    localDataSource: LocalDataSourceInterface,
    firebaseRepository: BaseAuthRepository
) {
    val remote = remoteDataSource
    val local = localDataSource
    val firebase = firebaseRepository
}