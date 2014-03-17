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

package org.djodjo.acaptcha.builders;


import android.os.Bundle;

import org.djodjo.acaptcha.CaptchaFragment;

public abstract class CaptchaFragmentBuilder {

   protected Bundle args = new Bundle();
   protected CaptchaFragment fragment;

    public CaptchaFragmentBuilder onView(int viewId) {
        args.putInt(CaptchaFragment.ARG_VIEW_ID, viewId);
        return this;
    }

    public CaptchaFragmentBuilder withBlockingMethod(int blockingMethod) {
        args.putInt(CaptchaFragment.ARG_VIEW_BLOCKING_METHOD, blockingMethod);
        return this;
    }

    public CaptchaFragmentBuilder withCustomPrompt(String customPrompt) {
        args.putString(CaptchaFragment.ARG_CUSTOM_TEXT, customPrompt);
        return this;
    }

    public CaptchaFragmentBuilder withCustomLayout(int customLayout) {
        args.putInt(CaptchaFragment.ARG_CUSTOM_LAYOUT, customLayout);
        return this;
    }

    public CaptchaFragmentBuilder removeCaptchaFragmentOnSuccess(boolean goneOnSuccess) {
        args.putBoolean(CaptchaFragment.ARG_GONE_ON_SUCCESS, goneOnSuccess);
        return this;
    }

    public CaptchaFragment build() {
        fragment.setArguments(args);
        return fragment;
    }
}
