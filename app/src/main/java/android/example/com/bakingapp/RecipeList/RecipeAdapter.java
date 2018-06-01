package android.example.com.bakingapp.RecipeList;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.data.Recipe;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mircea.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipesList;
    private final Context mContext;
    private final RecipeClickListener listener;

    public RecipeAdapter(Context context, RecipeClickListener listener){
        recipesList = new ArrayList<>();
        mContext = context;
        this.listener = listener;
    }

    public void setRecipesList(List<Recipe> recipesList){
        this.recipesList.clear();
        this.recipesList.addAll(recipesList);
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.recipeTextView.setText(recipe.getName());

        if(!recipe.getImage().equals("")){

        }
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recipeTextView;


        public RecipeViewHolder(View view){
            super(view);
            recipeTextView = view.findViewById(R.id.recipe_title);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeItemClick(recipesList.get(getAdapterPosition()));
        }
    }

    public interface RecipeClickListener{
        void onRecipeItemClick(Recipe recipe);
    }
}