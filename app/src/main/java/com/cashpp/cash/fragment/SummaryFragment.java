package com.cashpp.cash.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashpp.cash.R;
import com.cashpp.cash.activity.MainActivity;


public class SummaryFragment extends BaseFragment {
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public SummaryFragment () {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setTitle(R.string.summary);
        View rootView = inflater.inflate(R.layout.fragment_summary,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.summarytabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("summaryentriesfragment").setIndicator(getString(R.string.summary_entries)),
                SummaryEntriesFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("summarygraphicsfragment").setIndicator(getString(R.string.summary_graphics)),
                SummaryGraphicsFragment.class, null);

        return rootView;
    }

}
