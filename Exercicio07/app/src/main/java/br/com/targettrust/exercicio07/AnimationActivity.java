package br.com.targettrust.exercicio07;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AnimationActivity extends Activity {
    ListView lv;
    ArrayAdapter<ItemDetail> aAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        lv = (ListView) findViewById(R.id.listView);

        List<ItemDetail> itemList = this.createItems(50);

        // Load animation
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_anim);

        aAdp = new ArrayAdapter<ItemDetail>(this, android.R.layout.simple_list_item_1, itemList);
        lv.setAdapter(aAdp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position,
                                    long id) {

                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                        view.setHasTransientState(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ItemDetail item = aAdp.getItem(position);
                        aAdp.remove(item);
                        view.setHasTransientState(false);
                    }
                });
                view.startAnimation(anim);
            }
        });

        lv.setOnScrollListener(scrollListener);

    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        boolean isScrolling = false;
        boolean isLoading = false;
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState != 0)
                isScrolling = true;
            else {
                isScrolling = false;
                aAdp.notifyDataSetChanged();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if(lastInScreen == totalItemCount && !isLoading){
                //loadMoreItems(totalItemCount - 1);
                isLoading = true;
            }
        }
    };


    private List<ItemDetail> createItems(int size) {
        List<ItemDetail> result = new ArrayList<ItemDetail>();
        for (int i=0; i < size; i++) {
            ItemDetail item = new ItemDetail(i, "Item " + i);
            result.add(item);
        }

        return result;
    }


}
