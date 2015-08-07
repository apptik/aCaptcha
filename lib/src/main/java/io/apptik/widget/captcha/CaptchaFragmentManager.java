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

package io.apptik.widget.captcha;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import io.apptik.widget.captcha.builders.ShakeItBuilder;

public class CaptchaFragmentManager {



    /**
     *
     * @param container the placeholder where to put the captcha fragment
     * @return
     */
    public static void newCaptcha(FragmentActivity activity, int container, CaptchaFragment captchaFragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(container, captchaFragment).commit();
    }

    /**
     *
     * @param container the placeholder where to put the captcha fragment
     * @param viewId the id of the view which captcha fragment will enable after success
     * @return
     */
    public static void newShakeItCaptcha(FragmentActivity activity, int container, int viewId) {
       newCaptcha(activity, container, new ShakeItBuilder().onView(viewId).build() );
    }

}
