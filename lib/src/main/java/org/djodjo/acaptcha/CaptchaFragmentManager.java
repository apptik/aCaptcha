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
import android.app.FragmentManager;
import android.os.Bundle;

import org.djodjo.acaptcha.builders.CaptchaFragmentBuilder;
import org.djodjo.acaptcha.builders.ShakeItBuilder;

public class CaptchaFragmentManager {



    /**
     *
     * @param container the placeholder where to put the captcha fragment
     * @return
     */
    public static void newCaptcha(Activity activity, int container, CaptchaFragment captchaFragment) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction().replace(container, captchaFragment).commit();
    }

    /**
     *
     * @param container the placeholder where to put the captcha fragment
     * @param viewId the id of the view which captcha fragment will enable after success
     * @return
     */
    public static void newShakeItCaptcha(Activity activity, int container, int viewId) {
       newCaptcha(activity, container, new ShakeItBuilder().onView(viewId).build() );
    }

}
