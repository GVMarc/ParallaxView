# ParallaxView

Android custom view for parallax effect

## How to use it

#### In layout:

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipChildren="false">

        <ImageView
            android:id="@+id/background"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:scaleType="center"
            android:src="@drawable/img_background" />

        <your.package.views.ParallaxView
            android:id="@+id/parallax_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/img_parallax" />

    </FrameLayout>
#### In Activity:

    private ParallaxView mParallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...

        mParallaxView = (ParallaxView) findViewById(R.id.parallax_view);
        mParallaxView.init();
    }

    @Override
    protected void onResume() {
       mParallaxView.registerSensorListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mParallaxView.unregisterSensorListener();
        super.onPause();
    }

----
## To create different layers of parallax views

#### In layout:

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipChildren="false">

        <ImageView
            android:id="@+id/background"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:scaleType="center"
            android:src="@drawable/img_background" />

        <your.package.views.ParallaxView
            android:id="@+id/parallax_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/img_parallax" />
            
        <your.package.views.ParallaxView
            android:id="@+id/parallax_view_2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/img_parallax_2" />
            
        <your.package.views.ParallaxView
            android:id="@+id/parallax_view_3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/img_parallax_3" />

    </FrameLayout>
#### In Activity:

    private ParallaxView mParallaxView;
    private ParallaxView mParallaxView2;
    private ParallaxView mParallaxView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...

        mParallaxView = (ParallaxView) findViewById(R.id.parallax_view);
        mParallaxView2 = (ParallaxView) findViewById(R.id.parallax_view_2);
        mParallaxView3 = (ParallaxView) findViewById(R.id.parallax_view_3);
    
        initParallaxViews();
    }

    private void initParallaxViews(){
        mParallaxView.init();
        mParallaxView2.init();
        mParallaxView2.setMinimumMovedPixelsToUpdate(ParallaxView.DEFAULT_MIN_MOVED_PIXELS * 2);
        mParallaxView2.setMovementMultiplier(ParallaxView.DEFAULT_MOVEMENT_MULTIPLIER * 2);
        mParallaxView3.init();
        mParallaxView3.setMinimumMovedPixelsToUpdate(ParallaxView.DEFAULT_MIN_MOVED_PIXELS * 3);
        mParallaxView3.setMovementMultiplier(ParallaxView.DEFAULT_MOVEMENT_MULTIPLIER * 3);
    }

    @Override
    protected void onResume() {
        mParallaxView.registerSensorListener();
        mParallaxView2.registerSensorListener();
        mParallaxView3.registerSensorListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mParallaxView.unregisterSensorListener();
        mParallaxView2.unregisterSensorListener();
        mParallaxView3.unregisterSensorListener();
        super.onPause();
    }
