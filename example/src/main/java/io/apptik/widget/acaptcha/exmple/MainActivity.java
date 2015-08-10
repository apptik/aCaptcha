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

package io.apptik.widget.acaptcha.exmple;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.apptik.widget.captcha.CaptchaFragment;
import io.apptik.widget.captcha.CaptchaFragmentManager;
import io.apptik.widget.captcha.builders.ShakeItBuilder;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int currentSelection=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.djodjo.acaptcha.example.R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(org.djodjo.acaptcha.example.R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                org.djodjo.acaptcha.example.R.id.navigation_drawer,
                (DrawerLayout) findViewById(org.djodjo.acaptcha.example.R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(org.djodjo.acaptcha.example.R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
    }

    public void onSectionAttached(int number) {
        currentSelection = number;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(org.djodjo.acaptcha.example.R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == org.djodjo.acaptcha.example.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(org.djodjo.acaptcha.example.R.layout.fragment_main, container, false);
            View btnLogin = rootView.findViewById(org.djodjo.acaptcha.example.R.id.btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Login OK", Toast.LENGTH_LONG).show();
                }
            });


            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    CaptchaFragmentManager.newCaptcha(getActivity(), org.djodjo.acaptcha.example.R.id.captcha_container,
                            new ShakeItBuilder().onView(org.djodjo.acaptcha.example.R.id.btn_login)
                                    .withBlockingMethod(CaptchaFragment.BLOCKING_METHOD_VISIBLE)
                                    .build()
                    ); break;
                case 1:
//                    CaptchaFragmentManager.newCaptcha(getActivity(), R.id.captcha_container,
//                            new SlideItBuilder()
//                                    .onView(R.id.btn_login)
//                                    .withBlockingMethod(CaptchaFragment.BLOCKING_METHOD_ENABLED)
//                                    .build()
//                    );
                    Toast.makeText(rootView.getContext(), "Coming Soon :)", Toast.LENGTH_LONG).show();
                    break;

                case 2:
//                    CaptchaFragmentManager.newCaptcha(getActivity(), R.id.captcha_container,
//                            new PointItBuilder()
//                                    .onView(R.id.btn_login)
//                                    .withBlockingMethod(CaptchaFragment.BLOCKING_METHOD_ONCLICK_CALLBACK)
//                                    .build()
//                    );
                    Toast.makeText(rootView.getContext(), "Coming Soon :)", Toast.LENGTH_LONG).show();
                    break;
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
