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

package org.djodjo.acaptcha;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.djodjo.acaptcha.R;
import org.djodjo.acaptcha.tools.ShakeEventListener;


public class ShakeItCaptcha extends CaptchaFragment {

    SensorManager sensorManager;
    ShakeEventListener shakeEventListener;
    int requiredMinorShakes = 3;


    @Override
    protected View generateCaptchaView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shakeit, container, false);
    }

    @Override
    protected void refreshCaptchaView() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ShakeDetector initialization
        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        shakeEventListener = new ShakeEventListener();
        shakeEventListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            @Override
            public void onShake(int minorCount, int majorCount) {
                if(requiredMinorShakes<=minorCount) captchaOK();
            }

            @Override
            public void onCounterReset() {
                captchaWrong();
            }
        });

        sensorManager.registerListener(shakeEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDetach() {
        sensorManager.unregisterListener(shakeEventListener);
        super.onDetach();
    }
}
