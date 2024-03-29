package nesty.anzhy.matkonim.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nesty.anzhy.matkonim.data.database.LocalDataSource
import nesty.anzhy.matkonim.data.database.LocalDataSourceInterface
import nesty.anzhy.matkonim.data.database.RecipesDao
import nesty.anzhy.matkonim.data.firebase.AuthRepository
import nesty.anzhy.matkonim.data.firebase.BaseAuthRepository
import nesty.anzhy.matkonim.data.firebase.BaseAuthenticator
import nesty.anzhy.matkonim.data.firebase.FirebaseAuthenticator
import nesty.anzhy.matkonim.data.network.FoodRecipesApi
import nesty.anzhy.matkonim.data.network.RemoteDataSource
import nesty.anzhy.matkonim.data.network.RemoteDataSourceInterface

import nesty.anzhy.matkonim.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**All of our application dependencies shall be provided here*/

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
       okHttpClient: OkHttpClient,
       gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticator() : BaseAuthenticator {
        return  FirebaseAuthenticator()
    }

    //this just takes the same idea as the authenticator. If we create another repository class
    //we can simply just swap here
    @Singleton
    @Provides
    fun provideRepository() : BaseAuthRepository {
        return AuthRepository(provideAuthenticator())
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        foodRecipesApi: FoodRecipesApi
    ) :  RemoteDataSourceInterface {
        return RemoteDataSource(foodRecipesApi)
    }


    @Singleton
    @Provides
    fun provideLocalDataSourceInterface(
        recipesDao: RecipesDao
    ): LocalDataSourceInterface {
        return LocalDataSource(recipesDao)
    }
}