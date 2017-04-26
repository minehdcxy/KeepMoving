package com.example.raytine.keepmoving.home;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.raytine.keepmoving.R;



/**
 * Created by raytine on 2017/4/17.
 */

public class HomeActivity extends ActionBarActivity {
    private SliderLayout sliderLayout;
    private Toolbar toolbar;
    private int[] imgurl = new int[]{R.drawable.image_one,
            R.drawable.image_two, R.drawable.image_three};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

    }

    private void initViews() {
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        for (int url : imgurl) {
            TextSliderView customSliderView = new TextSliderView(this);
            customSliderView
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.addSlider(customSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(2000);
        toolbar = (Toolbar) findViewById(R.id.home_view_toolbar);


    }
}
