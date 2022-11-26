package nesty.anzhy.matkonim.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nesty.anzhy.matkonim.data.database.RecipesDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Named("testDatabase")
    @Provides
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            RecipesDatabase::class.java
        ).allowMainThreadQueries()
            .build()

}