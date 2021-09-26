package nesty.anzhy.matkonim.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import nesty.anzhy.matkonim.models.FoodRecipe
import nesty.anzhy.matkonim.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0;
}