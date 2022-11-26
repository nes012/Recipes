package nesty.anzhy.matkonim.data.repository

import nesty.anzhy.matkonim.data.database.LocalDataSourceInterface
import nesty.anzhy.matkonim.data.firebase.BaseAuthRepository
import nesty.anzhy.matkonim.data.network.RemoteDataSourceInterface


class FakeRepository(
    remoteDataSource: RemoteDataSourceInterface,
    localDataSource: LocalDataSourceInterface,
    firebaseRepository: BaseAuthRepository
) : Repository(remoteDataSource, localDataSource, firebaseRepository)
