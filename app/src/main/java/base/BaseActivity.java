package base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.raytine.keepmoving.R;

/**
 * Created by raytine on 2017/4/10.
 */

abstract class BaseActivity extends Activity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);

    }
}
