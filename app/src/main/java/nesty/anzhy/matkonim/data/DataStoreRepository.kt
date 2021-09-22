package nesty.anzhy.matkonim.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_DIET_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.DEFAULT_MEAL_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_DIET_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import nesty.anzhy.matkonim.util.Constants.Companion.PREFERENCES_NAME
import java.io.IOException
import javax.inject.Inject

//we will you this class instead Shared preferences. to save information about checked chips.
//data store is running on background thread and on main thread like shared preferences.

//we use retainedScope because datastore repository will be used inside recipes view model
@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    //here we will define all our keys, which we're going to use for our data store preferences
    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)

    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(PREFERENCES_NAME)

    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        //here we're going to use datastore to save values
        dataStore.edit { preferences->
            //inside []we need to specify preferences key.. one by one.
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    //for read information from data store
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