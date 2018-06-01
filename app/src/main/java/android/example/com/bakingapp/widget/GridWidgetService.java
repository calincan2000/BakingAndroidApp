package android.example.com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.R;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import static android.example.com.bakingapp.widget.BakingWidgetProvider.ingredientsList;

/**
 * mircea
 * 29.05.2018
 **/
public class GridWidgetService extends RemoteViewsService {

    List<String> remoteViewingredientsList;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    private class GridRemoteViewsFactory implements RemoteViewsFactory {
        Context mContext=null;
        public GridRemoteViewsFactory(Context context, Intent intent) {
            mContext=context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteViewingredientsList = ingredientsList;

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.widget_grid_view_item, remoteViewingredientsList.get(position));

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
