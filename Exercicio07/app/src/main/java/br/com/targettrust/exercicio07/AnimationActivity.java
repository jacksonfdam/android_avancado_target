package br.com.targettrust.exercicio07;

import android.app.Activity;
import android.os.Bundle;

public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ListView lv = (ListView) findViewById(R.id.listView);

        List<ItemDetail> itemList = createItems(50);

        // Load animation
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_anim);

        final ArrayAdapter<ItemDetail> aAdpt = new ArrayAdapter<ItemDetail>(this, android.R.layout.simple_list_item_1, itemList);
        lv.setAdapter(aAdpt);

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
                        ItemDetail item = aAdpt.getItem(position);
                        aAdpt.remove(item);
                        view.setHasTransientState(false);
                    }
                });
                view.startAnimation(anim);
            }
        });

        lv.setOnScrollListener(new OnScrollListener() {

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCoun) {
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState != 0)
                        lv.getAdapter()).isScrolling = true;
                    else {
                        lv.getAdapter()).isScrolling = false;
                        lv.getAdapter()).notifyDataSetChanged();
                    }
                }
            });

    }


    private List<ItemDetail> createItems(int size) {
        List<ItemDetail> result = new ArrayList<ItemDetail>();
        for (int i=0; i < size; i++) {
            ItemDetail item = new ItemDetail(i, "Item " + i);
            result.add(item);
        }

        return result;
    }


}
}