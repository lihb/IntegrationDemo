package com.lihb.integrationdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lihb.action.ApiManager;
import com.lihb.action.ServiceGenerator;
import com.lihb.adapter.ContributorAdapter;
import com.lihb.model.Contributor;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout = null;

    private ListView mListView = null;

    private ContributorAdapter mAdapter = null;

    private ProgressBar mProgressBar = null;

    private ArrayList<Contributor> mData = new ArrayList<Contributor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layoyt);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAdapter = new ContributorAdapter(this, mData);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);


        ServiceGenerator.createService(ApiManager.class)
                .contributors("square", "retrofit")
                .flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                    @Override
                    public Observable<Contributor> call(List<Contributor> contributors) {
                        return Observable.from(contributors);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Contributor>() {
                    @Override
                    public void onCompleted() {
                        Log.i("MainActivity", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("MainActivity", "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Contributor contributor) {
                        mListView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Log.i("MainActivity", contributor.login + " : " + contributor.avatar_url);
                        mData.add(contributor);
                        mAdapter.notifyDataSetChanged();

                    }
                });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                Contributor contributor = (Contributor) parent.getAdapter().getItem(position);
                ServiceGenerator.createService(ApiManager.class)
                        .followers(contributor.login)
                        .flatMap(new Func1<List<Contributor>, Observable<?>>() {
                            @Override
                            public Observable<?> call(List<Contributor> contributors) {
                                return Observable.from(contributors);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                mData.clear();
                            }

                            @Override
                            public void onCompleted() {
                                Log.i("MainActivity", "onCompleted");

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("MainActivity", "onError");
                            }

                            @Override
                            public void onNext(Object o) {
                                mListView.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                Log.i("MainActivity", ((Contributor) o).login + " : " + ((Contributor) o).avatar_url);
                                mData.add((Contributor) o);
                                mAdapter.notifyDataSetChanged();

                            }
                        });

            }
        });


    }



}
