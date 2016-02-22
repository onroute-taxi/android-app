package com.enamakel.backseattester.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.enamakel.backseattester.App;
import com.enamakel.backseattester.util.Injectable;
import com.enamakel.backseattester.util.MenuTintDelegate;


/**
 * Base fragment which performs injection using parent's activity object graphs if any
 */
public abstract class BaseFragment extends Fragment {
    protected final MenuTintDelegate menuTintDelegate = new MenuTintDelegate();
    boolean isAttached;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof Injectable) ((Injectable) getActivity()).inject(this);
        menuTintDelegate.onActivityCreated(getActivity());
    }


    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (isAttached()) { // TODO http://b.android.com/80783
            createOptionsMenu(menu, inflater);
            menuTintDelegate.onOptionsMenuCreated(menu);
        }
    }


    @Override
    public final void onPrepareOptionsMenu(Menu menu) {
        // TODO http://b.android.com/80783
        if (isAttached()) prepareOptionsMenu(menu);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getRefWatcher(getActivity()).watch(this);
    }


    public boolean isAttached() {
        return isAttached;
    }


    protected void createOptionsMenu(Menu menu, MenuInflater inflater) {
        // override to create options menu
    }


    protected void prepareOptionsMenu(Menu menu) {
        // override to prepare options menu
    }
}