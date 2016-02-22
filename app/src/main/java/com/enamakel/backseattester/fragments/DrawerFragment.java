package com.enamakel.backseattester.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.activities.base.DrawerActivity;
import com.enamakel.backseattester.util.AlertDialogBuilder;

import javax.inject.Inject;


public class DrawerFragment extends BaseFragment {
    @Inject AlertDialogBuilder alertDialogBuilder;
    TextView drawerAccount;
    View drawerLogout;
    View drawerUser;


    final SharedPreferences.OnSharedPreferenceChangeListener mLoginListener
            = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (getActivity() == null) return;

            if (TextUtils.equals(key, getActivity().getString(R.string.pref_username)))
                setUsername();
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(mLoginListener);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_drawer, container, false);
//        drawerAccount = (TextView) view.findViewById(R.id.drawer_account);
//        drawerAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Account[] accounts = AccountManager.get(getActivity())
//                        .getAccountsByType(BuildConfig.APPLICATION_ID);
//                // no accounts, ask to login or re-login
////                if (accounts.length == 0)
////                    startActivity(new Intent(getActivity(), LoginActivity.class));
////                else // has accounts, show account chooser regardless of login status
////                    AppUtils.showAccountChooser(getActivity(), alertDialogBuilder,
////                            accounts);
//            }
//        });
//
//        drawerLogout = view.findViewById(R.id.drawer_logout);
//        drawerLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialogBuilder
//                        .init(getActivity())
//                        .setMessage(R.string.logout_confirm)
//                        .setNegativeButton(android.R.string.cancel, null)
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Preferences.setUsername(getActivity(), null);
//                            }
//                        })
//                        .show();
//            }
//        });
//
//        drawerUser = view.findViewById(R.id.drawer_user);

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
        setUsername();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(mLoginListener);
    }


    void navigate(Class<? extends Activity> activityClass) {
        navigate(activityClass, null);
    }


    void navigate(Class<? extends Activity> activityClass, Bundle extras) {
        ((DrawerActivity) getActivity()).navigate(activityClass, extras);
    }


    void setUsername() {
//        if (getView() == null) return;
//
//        String username = Preferences.getUsername(getActivity());
//        if (!TextUtils.isEmpty(username)) {
//            drawerAccount.setText(username);
//            drawerLogout.setVisibility(View.VISIBLE);
//            drawerUser.setVisibility(View.VISIBLE);
//        } else {
//            drawerAccount.setText(R.string.login);
//            drawerLogout.setVisibility(View.GONE);
//            drawerUser.setVisibility(View.GONE);
//        }
    }
}

