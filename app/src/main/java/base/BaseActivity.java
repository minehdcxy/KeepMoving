package base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;


/**
 * Created by raytine on 2017/4/10.
 */

public abstract class BaseActivity extends Activity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected abstract int getLayout();
}
