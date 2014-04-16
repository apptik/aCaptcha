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
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.djodjo.acaptcha.tools.ReflectionTools;


public abstract class CaptchaFragment extends Fragment {
    private static String TAG = CaptchaFragment.class.getSimpleName();

    public static final int BLOCKING_METHOD_VISIBLE = 1;
    public static final int BLOCKING_METHOD_ENABLED = 2;
    public static final int BLOCKING_METHOD_ONCLICK_CALLBACK = 3;

    public static final String ARG_VIEW_ID = "view_id";
    public static final String ARG_VIEW_BLOCKING_METHOD = "view_blocking_method";
    public static final String ARG_CUSTOM_TEXT = "custom_text";
    public static final String ARG_CUSTOM_TEXT_APPEARANCE = "custom_text_appearance";
    public static final String ARG_CUSTOM_IMAGE = "custom_image";
    public static final String ARG_CUSTOM_LAYOUT = "custom_layout";
    public static final String ARG_GONE_ON_SUCCESS = "gone_on_success";



    private View controlledView = null;
    private int blockingMethod = 1;
    private String customText = null;
    private int customImage = 0;
    private int customTextAppearance = 0;
    private int customLayout = 0;
    private boolean goneOnSuccess = false;



    private View.OnClickListener origOnClickListener;

    private CaptchaCallback mListener;

    private boolean captchaPass = false;

    public CaptchaFragment() {
        // Required empty public constructor
    }

    /**
     * Generates the initial captcha view
     * @return the View with captcha content
     */
    protected abstract View generateCaptchaView(LayoutInflater inflater, ViewGroup container,
                                                Bundle savedInstanceState);

    /**
     * Refresh captcha content for example in case of failure.
     */
    protected abstract void refreshCaptchaView();

    /**
     * Implementations call this method when captcha is passed
     */
    protected void captchaOK() {
        captchaPass = true;
        if (mListener != null) {
            mListener.onSuccess();
        }
        if(controlledView != null) {
            switch (blockingMethod) {
                case BLOCKING_METHOD_ENABLED: controlledView.setEnabled(true); break;
                case BLOCKING_METHOD_VISIBLE: controlledView.setVisibility(View.VISIBLE); break;
                case BLOCKING_METHOD_ONCLICK_CALLBACK: controlledView.setOnClickListener(origOnClickListener); break;
            }
        }

        if(goneOnSuccess) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            //if addToBackStack
            //getActivity().getFragmentManager().popBackStack();
        }
    }

    /**
     * Implementations call this method when captcha is NOT passed
     */
    protected void captchaWrong() {
        captchaPass = false;
        if (mListener != null) {
            mListener.onError();
        }
        refreshCaptchaView();
    }

    public boolean isItPass() {
        return captchaPass;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            controlledView = getActivity().findViewById(getArguments().getInt(ARG_VIEW_ID));
            blockingMethod = getArguments().getInt(ARG_VIEW_BLOCKING_METHOD, BLOCKING_METHOD_VISIBLE);
            customText = getArguments().getString(ARG_CUSTOM_TEXT, null);
            customTextAppearance = getArguments().getInt(ARG_CUSTOM_TEXT_APPEARANCE, 0);
            customImage = getArguments().getInt(ARG_CUSTOM_IMAGE, 0);
            customLayout = getArguments().getInt(ARG_CUSTOM_LAYOUT, 0);
            goneOnSuccess = getArguments().getBoolean(ARG_GONE_ON_SUCCESS, false);
        }

        if(controlledView != null) {
            switch (blockingMethod) {
                case BLOCKING_METHOD_ENABLED: controlledView.setEnabled(false); break;
                case BLOCKING_METHOD_VISIBLE: controlledView.setVisibility(View.GONE); break;
                case BLOCKING_METHOD_ONCLICK_CALLBACK:
                    View.OnClickListener onClickListener =  ReflectionTools.getOnClickListener(controlledView);
                    if(onClickListener == null || !(onClickListener instanceof CaptchaOnClickListener)) {
                        origOnClickListener = onClickListener;
                        controlledView.setOnClickListener(new CaptchaOnClickListener());
                    }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = null;
        if(customLayout == 0) {
            rootView = generateCaptchaView(inflater, container, savedInstanceState);
            TextView captchaPrompt = (TextView) rootView.findViewById(R.id.txt_captcha_prompt);
            ImageView captchaImage = (ImageView) rootView.findViewById(R.id.img_captcha);
            if (customText != null && captchaPrompt != null)
                captchaPrompt.setText(customText);
            if (customTextAppearance != 0 && captchaPrompt != null)
                captchaPrompt.setTextAppearance(getActivity(), customTextAppearance);
            if(customImage!=0) {
                captchaImage.setImageDrawable(getResources().getDrawable(customImage));
            }
        } else {
            rootView = inflater.inflate(customLayout, container, false);
        }
        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CaptchaCallback) activity;
        } catch (ClassCastException e) {
            Log.i(TAG, activity.toString()
                    + " does not implement CaptchaCallback so no callback can be send.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected class CaptchaOnClickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(((TextView)getActivity().findViewById(R.id.txt_captcha_prompt)).getText())
                    .setTitle(R.string.captcha_error);
            builder.create().show();
        }

    }
}
