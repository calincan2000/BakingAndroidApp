package android.example.com.bakingapp.Service;

import android.example.com.bakingapp.data.Recipe;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Mircea.
 */

public class RecipeApi {
    public interface RecipesApi {
        @GET("/topher/2017/May/59121517_baking/baking.json")
        void getRecipes(Callback<List<Recipe>> callback);
    }
}
