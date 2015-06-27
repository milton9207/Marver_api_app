package com.example.myapplication2.app;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity implements ApiMarvelDownloadFragment.OnDownloadFinishedListener {
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private ApiMarvelDownloadFragment mMarvelDownloadFragment;
    private String apiDataRaw;

    static final String TAG_API_DATA = "api_data";
    private static final String TAG_API_DWN_FRAGMENT = "api_marvel_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        installApiMarvelDownloadFragment();

    }


    public void installMainFragment()
    {
        mMainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_API_DATA, apiDataRaw);
        mMainFragment.setArguments(bundle);

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,mMainFragment).commit();
    }

    public void installApiMarvelDownloadFragment()
    {
        mMarvelDownloadFragment = new ApiMarvelDownloadFragment();

        mFragmentManager.beginTransaction()
                .add(mMarvelDownloadFragment,TAG_API_DWN_FRAGMENT)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void notifyDataDownloaded(String data) {

        apiDataRaw = data;
        installMainFragment();

    }


}
