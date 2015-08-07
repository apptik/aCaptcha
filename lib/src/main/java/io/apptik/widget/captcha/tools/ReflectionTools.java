/*
 * Copyright (C) 2015 AppTik Project
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


import android.view.View;

import java.lang.reflect.Field;

public class ReflectionTools {

    public static View.OnClickListener getOnClickListener(View v) {
        View.OnClickListener res = null;
        if(v == null) return null;
            try
            {
                Class cListInfo = Class.forName("android.view.View$ListenerInfo");
                if (cListInfo != null)
                {
                   Field clickListenerField = cListInfo.getDeclaredField("mOnClickListener");
                    if (clickListenerField != null)
                    {
                        clickListenerField.setAccessible(true);

                        Field fList = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
                        if (fList != null)
                        {
                            fList.setAccessible(true);
                            Object myLiObject =  fList.get(v);
                            if(myLiObject != null)
                                res = (View.OnClickListener) clickListenerField.get(myLiObject);
                        }
                    }
                }
            } catch (Exception ex)
            {
            }
        return res;
    }
}
