package com.enamakel.backseattester.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.DrawerActivity;


public class DrawerFragment extends BaseFragment {
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_drawer, container, false);
//        view.findViewById(R.id.drawer_list).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(ListActivity.class);
//            }
//        });
//
//        view.findViewById(R.id.drawer_new).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(NewActivity.class);
//            }
//        });
//
//
//        view.findViewById(R.id.drawer_settings).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(SettingsActivity.class);
//            }
//        });
//        view.findViewById(R.id.drawer_about).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(AboutActivity.class);
//            }
//        });
//        view.findViewById(R.id.drawer_favorite).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(FavoriteActivity.class);
//            }
//        });
//        view.findViewById(R.id.drawer_submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigate(SubmitActivity.class);
//            }
//        });
//        view.findViewById(R.id.drawer_user).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle extras = new Bundle();
//                extras.putString(UserActivity.EXTRA_USERNAME, Preferences.getUsername(getActivity()));
//                navigate(UserActivity.class, extras);
//            }
//        });
//        view.findViewById(R.id.drawer_feedback).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((DrawerActivity) getActivity()).showFeedback();
//            }
//        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    void navigate(Class<? extends Activity> activityClass) {
        navigate(activityClass, null);
    }


    void navigate(Class<? extends Activity> activityClass, Bundle extras) {
        ((DrawerActivity) getActivity()).navigate(activityClass, extras);
    }
}

