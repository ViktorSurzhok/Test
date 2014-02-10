
package com.example.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    private ArrayAdapter<String> listAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void refresh() {
        List<String> lst = Arrays.asList(Handler.Headlines);
        List<String> lst2 = Arrays.asList(Handler.Articles);
        Collections.shuffle(lst);
        Collections.shuffle(lst2);
        Handler.Headlines = lst.toArray(Handler.Headlines);
        Handler.Articles = lst2.toArray(Handler.Articles);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Handler.Headlines));
        HeadlinesFragment.arrayAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
    }

    public void onArticleSelected(int position) {

        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {

            articleFrag.updateArticleView(position);

        } else {

            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    public void setListAdapter(ArrayAdapter<String> listAdapter) {
        this.listAdapter = listAdapter;
    }
}