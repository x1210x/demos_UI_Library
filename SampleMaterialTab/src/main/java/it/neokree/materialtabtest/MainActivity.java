package it.neokree.materialtabtest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import kr.pe.ssun.mylibrary.BaseActivity;

/**
 * Created by neokree on 30/12/14.
 */
public class MainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarTitle("MaterialTab");
        setToolbarIconState(IconState.ARROW);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishProperly();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, new MaterialTabFragment())
                .commit();
    }
}
