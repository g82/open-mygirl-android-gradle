package com.seokceed.mygirl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ContentListAdapter());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static interface CardClickListener {

        public void onItemClick(View view, int position);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView mCardView;
        public TextView mTextView;
        public CardClickListener mCardClickListener;

        public MyViewHolder(View itemView, CardClickListener cardClickListener) {
            super(itemView);
            mCardView = (CardView) itemView;
            mTextView = (TextView) itemView.findViewById(R.id.tvTest);
            mCardClickListener = cardClickListener;
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCardClickListener.onItemClick(view, getPosition());
        }
    }

    private class ContentListAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contents, viewGroup, false);

            MyViewHolder vh = new MyViewHolder(v, new CardClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Intent i = new Intent(ContentListActivity.this, EditorActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    i.setData(getIntent().getData());
                    i.putExtra("content_idx", position);
                    startActivity(i);
                    finish();
                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

            myViewHolder.mTextView.setText("hohohoho");

        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }
}
