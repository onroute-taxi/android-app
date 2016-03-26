package com.enamakel.backseattester.activities.dashboard;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.views.gridview.items.ButtonsGridItem;
import com.enamakel.backseattester.views.gridview.items.ImageGridItem;
import com.enamakel.backseattester.views.gridview.OnGridItemClickListener;
import com.enamakel.backseattester.views.gridview.grid.ScrollableGrid;
import com.enamakel.backseattester.views.gridview.list.CompoundView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_dashboard)
public class DashboardActivity extends AppCompatActivity implements OnGridItemClickListener,
        BottomListAdapter.OnBottomItemClickListener {

    private ScrollableGrid scrollableGrid;

    private static final String COLUMN_ONE_ID = "column one id";
    private static final String COLUMN_TWO_ID = "column two id";
    private static final String COLUMN_THREE_ID = "column three id";

    @ViewById Toolbar toolbar;
    @ViewById CompoundView compoundView;

    @AfterViews
    protected void afterViews() {
        // Get toolbar from layout
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);

        assert compoundView != null;
        // obtain a link to the ScrollableGrid
        scrollableGrid = compoundView.getScrollableGrid();
        // obtain a link to the RecyclerView
        RecyclerView recyclerView = compoundView.getRecyclerView();

        // create an adapter instance and set it to the recycler view
        BottomListAdapter adapter = new BottomListAdapter(createTestObject());
        adapter.setOnBottomItemClickListener(this);
        recyclerView.setAdapter(adapter);

        if (scrollableGrid != null) {
            // this is how you can add how many column you want
            scrollableGrid.addNewColumn(COLUMN_ONE_ID, 2);
            scrollableGrid.addNewColumn(COLUMN_TWO_ID, 3);
            scrollableGrid.addNewColumn(COLUMN_THREE_ID, 2);
            // this is a listener which will be called when an item is clicked
            scrollableGrid.setOnGridItemClickListener(this);

            // this is how you can add items to any column you've added
            // you can add a layout resource
            // last parameter is layout_weight, you can specify 1 and then all items will be with same size
            // the item with more then one will be bigger then the others
            scrollableGrid.addViewToColumn(COLUMN_ONE_ID, "map item", getImageGridItem(), 1);
            scrollableGrid.addViewToColumn(COLUMN_ONE_ID, "map item two", getImageGridItem(), 1);

            // or you can add a view to the column
            scrollableGrid.addViewToColumn(COLUMN_TWO_ID, "image item", getImageGridItem(), 1);

            // Create a new buttons layout
            ButtonsGridItem buttonsGridItem = new ButtonsGridItem(this);

            // Customize left button
            buttonsGridItem.setLeftButtonImage(R.drawable.ic_favorite_white_24dp);
            buttonsGridItem.setLeftButtonTitle("Explore Movies");
            buttonsGridItem.setLeftButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "On left button click", Toast.LENGTH_SHORT).show();
                }
            });

            // customize right button
            buttonsGridItem.setRightButtonImage(R.drawable.ic_favorite_white_24dp);
            buttonsGridItem.setRightButtonTitle("Explore Movies");
            buttonsGridItem.setRightButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "On right button click", Toast.LENGTH_SHORT).show();
                }
            });

            scrollableGrid.addViewToColumn(COLUMN_TWO_ID, "button item", buttonsGridItem, 1);
            scrollableGrid.addViewToColumn(COLUMN_TWO_ID, "image item two", getImageGridItem(), 1);

            scrollableGrid.addViewToColumn(COLUMN_THREE_ID, "image item three", getImageGridItem(), 2);
            scrollableGrid.addViewToColumn(COLUMN_THREE_ID, "image item four", getImageGridItem(), 2);

            Button button = new Button(this);
            button.setText("View more...");
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scrollableGrid.addViewToColumn(COLUMN_THREE_ID, "button item in column", button, 1);
        }
    }


    public ImageGridItem getImageGridItem() {
        // this is the item from your image which you can customize how your needs are
        ImageGridItem imageGridItem = new ImageGridItem(this);
        imageGridItem.setBackgroundImage(R.drawable.image_place_holder);
        imageGridItem.setIconImage(R.drawable.ic_favorite_white_24dp);
        imageGridItem.setTitle("This is title from code");
        imageGridItem.setDescription("This is the despcription from code for this item");
        return imageGridItem;
    }


    private ArrayList<DashboardTile> createTestObject() {
        ArrayList<DashboardTile> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new DashboardTile(R.drawable.image_place_holder));
        }
        return objects;
    }


    @Override
    public void onGridItemClick(View item, String itemId, String columnId) {
        // like this you can remove an item from column
        scrollableGrid.removeItemFromColumn(columnId, itemId);
    }


    @Override
    public void onBottomItemClickListener(DashboardTile object) {
        Toast.makeText(this, "On bottom item clicked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_menu:
                // TODO: 2/9/16 perform search action
                Toast.makeText(DashboardActivity.this, getString(R.string.action_example), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
