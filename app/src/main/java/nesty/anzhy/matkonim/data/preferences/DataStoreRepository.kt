package nesty.anzhy.matkonim.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_DIET_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_MEAL_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_DIET_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_NAME
import java.io.IOException
import javax.inject.Inject

//we will use this class instead Shared preferences. to save information about checked chips.
//data store is running on a background thread while  shared preferences on main thread.
private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

//we use retainedScope because datastore repository will be used inside recipes view model
@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    //here we will define all our keys, which we're going to use for our data store preferences
    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    //function for saving or storing our data inside data store preference
    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        //here we're going to use datastore to save values
        dataStore.edit { preferences->
            //inside []we need to specify preferences key.. one by one.
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline:Boolean){
        dataStore.edit { preferences->
            preferences[PreferenceKeys.backOnline] }
    }

    //providing reading value setBackOnline.
    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exp->
            if(exp is IOException){
                //if there is an exception we're going to emit empty preferences
                emit(emptyPreferences())
            } else{
                throw exp
            }
        }
        .map { preferences->
            val backOnline = preferences[PreferenceKeys.backOnline]?: false
            backOnline
        }

    //read information from data store
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exp->
            if(exp is IOException){
                //if there is an exception we're going to emit empty preferences
                emit(emptyPreferences())
            } else{
                throw exp
            }
        }
        .map { preferences->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType]?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId]?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType]?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId]?: 0

            MealAndDietType(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
        }

}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int,
)