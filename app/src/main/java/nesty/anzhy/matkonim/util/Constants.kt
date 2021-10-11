package nesty.anzhy.matkonim.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

class Constants {

    companion object{
        const val API_KEY = "32c27d995c3f4bfd819eaeab50fce371"
        //constant for ingredient image
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        const val BASE_URL = "https://api.spoonacular.com/"

        const val RECIPE_RESULT_KEY = "recipeBundle"

        //API query keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room db
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        const val FAVORITES_RECIPES_TABLE = "favorites_recipes_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten_free"

        const val PREFERENCES_NAME = "matkonim_preferences"

        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"

        const val PREFERENCES_BACK_ONLINE = "backOnline"



        //our collections in cloud firestore:
        const val USERS: String = "users"
        const val LOGGED_IN_USERNAME: String = "logged_in_username"


        const val EXTRA_USER_DETAILS: String = "extra_user_details"
        const val USER_PROFILE_IMAGE: String = "User_Profile_Image"


        const val MALE: String = "Male"
        const val FEMALE: String = "Female"
        const val FIRST_NAME: String = "firstName"
        const val LAST_NAME: String = "lastName"
        const val MOBILE: String = "mobile"
        const val GENDER: String = "gender"
        const val IMAGE: String = "image"
        const val COMPLETE_PROFILE: String = "profileCompleted"




        const val READ_STORAGE_PERMISSION_CODE: Int = 2;
        const val PICK_IMAGE_REQUEST_CODE = 1

        const val MATKONIM_PREFERENCES: String = "MatkonimPref"


        fun getFileExtension(activity: Activity, uri: Uri?): String? {

            return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
        }


        fun showImageChooser(activity: Activity) {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
        }


    }
}