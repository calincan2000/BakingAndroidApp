package android.example.com.bakingapp.RecipeDetail;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.data.Ingredients;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mircea.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>  {
    private List<Ingredients> ingredientsList;
    private final Context mContext;

    public IngredientsAdapter(Context context){
        ingredientsList = new ArrayList<>();
        mContext = context;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList){
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredients_item, parent, false);
        return new IngredientsViewHolder(view);
    }

     @Override
     public void onBindViewHolder(IngredientsViewHolder holder, int position) {
         Ingredients ingredients = ingredientsList.get(position);
         holder.quantityTextView.setText(String.valueOf(ingredients.getQuantity()));
         holder.measureTextView.setText(ingredients.getMeasure());
         holder.ingredientsTextView.setText(ingredients.getIngredient());
     }

    @Override
    public int getItemCount() {
        return ingredientsList == null ? 0 : ingredientsList.size();
    }

        public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView quantityTextView;
        public TextView measureTextView;
        public TextView ingredientsTextView;

        public IngredientsViewHolder(View view){
            super(view);
            quantityTextView = view.findViewById(R.id.quantity_tv);
            measureTextView = view.findViewById(R.id.measure_tv);
            ingredientsTextView = view.findViewById(R.id.ingredient_tv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}