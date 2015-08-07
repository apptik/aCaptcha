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

package io.apptik.widget.captcha.builders;


import io.apptik.widget.captcha.CaptchaFragment;
import io.apptik.widget.captcha.fragments.SlideItCaptcha;

public class SlideItBuilder extends CaptchaFragmentBuilder {

    public SlideItBuilder() {
        fragment =  new SlideItCaptcha();
    }

    @Override
    public CaptchaFragment build() {
        if(!args.containsKey(CaptchaFragment.ARG_GONE_ON_SUCCESS))
            this.removeCaptchaFragmentOnSuccess(true);
        return super.build();
    }
}
