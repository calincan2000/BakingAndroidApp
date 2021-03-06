package android.example.com.bakingapp.RecipeDetail;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.data.Steps;
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

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<Steps> stepsList;
    private final Context mContext;
    private final StepsListener listener;

    public StepsAdapter(Context context, StepsListener listener) {
        stepsList = new ArrayList<>();
        mContext = context;
        this.listener = listener;
    }

    public void setStepsList(List<Steps> stepsList){
        this.stepsList = stepsList;
        notifyDataSetChanged();
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.steps_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Steps steps = stepsList.get(position);
        holder.stepsID.setText(String.valueOf(" " + steps.getSteps_id()));
        holder.shortDescription.setText(" : " + steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsList == null ? 0 : stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView stepsID;
        public TextView shortDescription;

        public StepsViewHolder(View view) {
            super(view);
            stepsID = view.findViewById(R.id.steps_id);
            shortDescription = view.findViewById(R.id.short_descrip);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onStepsItemClick(stepsList.get(getAdapterPosition()));
        }
    }

    public interface StepsListener {
        void onStepsItemClick(Steps steps);
    }
}