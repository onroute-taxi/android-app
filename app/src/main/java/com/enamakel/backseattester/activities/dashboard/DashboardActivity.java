package com.enamakel.backseattester.activities.dashboard;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.AdcolonyVideoActivity;
import com.enamakel.backseattester.activities.AdcolonyVideoActivity_;
import com.enamakel.backseattester.activities.base.BaseDashboardActivity;
import com.enamakel.backseattester.activities.dashboard.core.BottomListAdapter;
import com.enamakel.backseattester.activities.dashboard.core.DashboardTile;
import com.enamakel.backseattester.data.models.media.MediaModel;
import com.enamakel.backseattester.views.gridview.OnGridItemClickListener;
import com.enamakel.backseattester.views.gridview.grid.ScrollableGrid;
import com.enamakel.backseattester.views.gridview.items.ImageGridItem;
import com.enamakel.backseattester.views.gridview.list.CompoundView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_dashboard)
public class DashboardActivity extends BaseDashboardActivity implements OnGridItemClickListener,
        BottomListAdapter.OnBottomItemClickListener {
    private ScrollableGrid mScrollableGrid;

    private static final String COLUMN_ONE_ID = "column one id";
    private static final String COLUMN_TWO_ID = "column two id";
    private static final String COLUMN_THREE_ID = "column three id";

    //    @ViewById Toolbar toolbar;
    @ViewById CompoundView compoundView;
    RecyclerView recyclerView;

    MediaModel mediaModel;


    @AfterViews
    protected void afterViews() {
//        initializeToolbar(R.id.toolbar);

        assert compoundView != null;

        // obtain a link to the ScrollableGrid
        mScrollableGrid = CompoundView.getScrollableGrid();

        // obtain a link to the RecyclerView
        recyclerView = CompoundView.getRecyclerView();

        // create an adapter instance and set it to the recycler view
        BottomListAdapter adapter = new BottomListAdapter(createTestObject());
        adapter.setOnBottomItemClickListener(this);
        recyclerView.setAdapter(adapter);

        if (mScrollableGrid != null) {
            // this is how you can add how many column you want
            mScrollableGrid.addNewColumn(COLUMN_ONE_ID, 2);
            mScrollableGrid.addNewColumn(COLUMN_TWO_ID, 3);
            mScrollableGrid.addNewColumn(COLUMN_THREE_ID, 2);

            // this is a listener which will be called when an item is clicked
            mScrollableGrid.setOnGridItemClickListener(this);

             /*this is how you can add items to any column you've added
             you can add a layout resource
             last parameter is layout_weight, you can specify 1 and then all items will be with same size
             the item with more then one will be bigger then the others*/

            // TODO: remove the hardcoded data and work with the server

            // Everybody loves Raymond
            MediaModel raymondMedia = new MediaModel();
            raymondMedia.setTitle("Everybody loves raymond");
            raymondMedia.setDescription("The comical everyday life of a " +
                    "successful sports columnist and his dysfunctional family.");
            raymondMedia.setVideoPath("/sdcard/raymond-s1e1.avi");
            raymondMedia.setImagePath("file:///android_asset/images/raymond.jpg");

            // Mind your language
            MediaModel ninenineMedia = new MediaModel();
            ninenineMedia.setTitle("Mind your language");
            ninenineMedia.setDescription("Mind Your Language is a British comedy television series that premiered on ITV in 1977. Produced by London Weekend Television and directed by Stuart Allen.");
            ninenineMedia.setVideoPath("/sdcard/mind.mp4");
            ninenineMedia.setImagePath("file:///android_asset/images/mind.jpg");

            // Friends
            MediaModel friendsMedia = new MediaModel();
            friendsMedia.setTitle("Friends S1:E5");
            friendsMedia.setDescription("Eager to spend time with Rachel, Ross pretends his building's washroom is rat-infested so he can join her at the laundromat. Chandler points out this could be a 'date' and the first time she'll see his underwear so it shouldn't be dirty! Rachel, the spoiled 'laundry virgin' feels managing this domestic chore is a real step to independence, but despite Ross's good advice she leaves a red sock in the machine. The real accomplishment comes where she has to stand up as no-nonsense-New Yorker against a rude, aggressive woman who invents rules to pretend it's not Rachel turn to do her laundry. Joey realizes he regrets dumping foxy Angela when he learns she is dating Bob. He proposes a double-date, then needs a girl stat. Monica agrees to be Joey's date but when she sees hunky Bob and realizes he's not Angela's brother she starts to enjoy the evening. Chandler drinks too much espresso while desperately trying to break up with neurotic Janice.");
            friendsMedia.setVideoPath("/sdcard/friends.mp4");
            friendsMedia.setImagePath("file:///android_asset/images/friends.jpg");

            // Wizards of Waverly place
            MediaModel wizardsMedia = new MediaModel();
            wizardsMedia.setTitle("Wizards of Waverly place");
            wizardsMedia.setDescription("The Russo family own a restaurant and live in New York. However, the father is a wizard while the children are in training. The child who masters the powers shall be given the family wand.\n");
            wizardsMedia.setVideoPath("/sdcard/wizards.mp4");
            wizardsMedia.setImagePath("file:///android_asset/images/wizards.jpg");

            // Mad Men
            MediaModel madMenMedia = new MediaModel();
            madMenMedia.setTitle("Mad Men");
            madMenMedia.setDescription("Donald Draper, the Creative Director at the Sterling Cooper ad agency in New York, tries to maintain a balance between his exceptional professional life and wavering personal life in the 1960s.");
            madMenMedia.setVideoPath("/sdcard/mad.mp4");
            madMenMedia.setImagePath("file:///android_asset/images/mad.jpg");

            // Key & Peele
            MediaModel keypeeleMedia = new MediaModel();
            keypeeleMedia.setTitle("Key & Peele");
            keypeeleMedia.setDescription("Keegan-Michael Key and Jordan Peele, two comics, use sketches and live segments to perform their jokes.");
            keypeeleMedia.setVideoPath("/sdcard/key.mp4");
            keypeeleMedia.setImagePath("file:///android_asset/images/key.jpg");


            mScrollableGrid.addViewToColumn(COLUMN_ONE_ID, "map item",
                    new ImageGridItem(this, madMenMedia), 1);
            mScrollableGrid.addViewToColumn(COLUMN_ONE_ID, "map item two",
                    new ImageGridItem(this, friendsMedia), 1);

            // or you can add a view to the column
            mScrollableGrid.addViewToColumn(COLUMN_TWO_ID, "image item",
                    new ImageGridItem(this, raymondMedia), 1);

            /*// Create a new buttons layout
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
            });*/

            mScrollableGrid.addViewToColumn(COLUMN_TWO_ID, "button item",
                    new ImageGridItem(this, ninenineMedia), 1);
//            mScrollableGrid.addViewToColumn(COLUMN_TWO_ID, "image item two",
//                    new ImageGridItem(this, balikaMedia), 1);

            mScrollableGrid.addViewToColumn(COLUMN_THREE_ID, "image item three", new ImageGridItem(this, wizardsMedia), 1);
            mScrollableGrid.addViewToColumn(COLUMN_THREE_ID, "image item four", new ImageGridItem(this, keypeeleMedia), 1);

//            Button button = new Button(this);
//            button.setText("View more...");
//            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            mScrollableGrid.addViewToColumn(COLUMN_THREE_ID, "button item in column", button, 1);
        }
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
        if (item instanceof ImageGridItem) {
            ImageGridItem imageGridItem = (ImageGridItem) item;

            if (imageGridItem.getMediaModel() != null) {
                Intent intent = new Intent(this, AdcolonyVideoActivity_.class);
                intent.putExtra(AdcolonyVideoActivity.EXTRA_MEDIA_MODEL, imageGridItem.getMediaModel());
                startActivity(intent);
            }
        }
    }


    @Override
    public void onBottomItemClickListener(DashboardTile object) {
        Toast.makeText(this, "On bottom item clicked", Toast.LENGTH_SHORT).show();
    }
}
