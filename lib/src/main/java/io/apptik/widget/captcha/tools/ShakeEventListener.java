/*
 * Copyright (C) 2014 Kalin Maldzhanski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.widget.captcha.tools;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class ShakeEventListener implements SensorEventListener {

    // The gForce that is necessary to register as shake. Must be greater than 1G (one earth gravity unit)
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.3F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int majorCount;
    private int minorCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int minorCount, int majorCount);
        public void onCounterReset();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

       try {
           if (mListener != null) {
               float x = event.values[0];
               float y = event.values[1];
               float z = event.values[2];

               float gX = x / SensorManager.GRAVITY_EARTH;
               float gY = y / SensorManager.GRAVITY_EARTH;
               float gZ = z / SensorManager.GRAVITY_EARTH;

               // gForce will be close to 1 when there is no movement.
               double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

               if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                   final long now = System.currentTimeMillis();
                   // ignore shake events too close to each other (500ms)


                   // reset the shake count after 3 seconds of no shakes
                   if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                       majorCount = 0;
                       mListener.onCounterReset();
                   }

                   if (mShakeTimestamp + SHAKE_SLOP_TIME_MS <= now) {
                       majorCount++;
                   }

                   mShakeTimestamp = now;
                   minorCount++;

                   mListener.onShake(minorCount, majorCount);
               }
           }
       } catch (Exception ex) {

       }
    }
}
