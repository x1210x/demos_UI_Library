package com.getbase.floatingactionbutton.sample;

import com.balysv.materialmenu.MaterialMenuDrawable;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import kr.pe.ssun.mylibrary.BaseActivity;

public class MainActivity extends BaseActivity {
  private MaterialMenuDrawable materialMenu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setToolbarTitle("FloatingActionButton");
    setToolbarIconState(IconState.ARROW);
    setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finishProperly();
      }
    });

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction()
            .add(R.id.content, new FloatingActionButtonFragment())
            .commit();
  }
}
