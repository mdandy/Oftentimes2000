package edu.gatech.oftentimes2000;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Oftentimes2000 extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
