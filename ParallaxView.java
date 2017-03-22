import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import static android.content.Context.SENSOR_SERVICE;

public class ParallaxView extends AppCompatImageView implements SensorEventListener {

    private static final int DEFAULT_SENSOR_DELAY = SensorManager.SENSOR_DELAY_FASTEST;
    public static final int DEFAULT_MOVEMENT_MULTIPLIER = 3;
    public static final int DEFAULT_MIN_MOVED_PIXELS = 1;
    private static final float DEFAULT_MIN_SENSIBILITY = 0;

    private float mMovementMultiplier = DEFAULT_MOVEMENT_MULTIPLIER;
    private int mSensorDelay = DEFAULT_SENSOR_DELAY;
    private int mMinMovedPixelsToUpdate = DEFAULT_MIN_MOVED_PIXELS;
    private float mMinSensibility = DEFAULT_MIN_SENSIBILITY;

    private float mSensorX;
    private float mSensorY;
    private Float mFirstSensorX;
    private Float mFirstSensorY;
    private Float mPreviousSensorX;
    private Float mPreviousSensorY;

    private float mTranslationX = 0;
    private float mTranslationY = 0;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public enum SensorDelay {
        FASTEST,
        GAME,
        UI,
        NORMAL
    }

    public ParallaxView(Context context) {
        super(context);
    }

    public ParallaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        mSensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void setNewPosition() {
        int destinyX = (int) ((mFirstSensorX - mSensorX) * mMovementMultiplier);
        int destinyY = (int) ((mFirstSensorY - mSensorY) * mMovementMultiplier);

        calculateTranslationX(destinyX);
        calculateTranslationY(destinyY);
    }

    private void calculateTranslationX(int destinyX) {
        if (mTranslationX + mMinMovedPixelsToUpdate < destinyX)
            mTranslationX++;
        else if (mTranslationX - mMinMovedPixelsToUpdate > destinyX)
            mTranslationX--;
    }

    private void calculateTranslationY(int destinyY) {
        if (mTranslationY + mMinMovedPixelsToUpdate < destinyY)
            mTranslationY++;
        else if (mTranslationY - mMinMovedPixelsToUpdate > destinyY)
            mTranslationY--;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTranslationX(mTranslationX);
        setTranslationY(mTranslationY);
        invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSensorX = event.values[0];
            mSensorY = -event.values[1];

            manageSensorValues();
        }
    }

    private void manageSensorValues() {
        if (mFirstSensorX == null)
            setFirstSensorValues();

        if (mPreviousSensorX == null || isSensorValuesMovedEnough()) {
            setNewPosition();
            setPreviousSensorValues();
        }
    }

    private void setFirstSensorValues() {
        mFirstSensorX = mSensorX;
        mFirstSensorY = mSensorY;
    }

    private void setPreviousSensorValues() {
        mPreviousSensorX = mSensorX;
        mPreviousSensorY = mSensorY;
    }

    private boolean isSensorValuesMovedEnough() {
        return mSensorX > mPreviousSensorX + mMinSensibility ||
                mSensorX < mPreviousSensorX - mMinSensibility ||
                mSensorY > mPreviousSensorY + mMinSensibility ||
                mSensorY < mPreviousSensorX - mMinSensibility;
    }

    public void registerSensorListener() {
        mSensorManager.registerListener(this, mAccelerometer, mSensorDelay);
    }

    public void registerSensorListener(SensorDelay sensorDelay) {
        switch (sensorDelay) {
            case FASTEST:
                mSensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
                break;
            case GAME:
                mSensorDelay = SensorManager.SENSOR_DELAY_GAME;
                break;
            case UI:
                mSensorDelay = SensorManager.SENSOR_DELAY_UI;
                break;
            case NORMAL:
                mSensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
                break;
        }
        registerSensorListener();
    }

    public void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }

    public void setMovementMultiplier(float multiplier) {
        mMovementMultiplier = multiplier;
    }

    public void setMinimumMovedPixelsToUpdate(int minMovedPixelsToUpdate) {
        mMinMovedPixelsToUpdate = minMovedPixelsToUpdate;
    }

    public void setMinimumSensibility(int minSensibility) {
        mMinSensibility = minSensibility;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}