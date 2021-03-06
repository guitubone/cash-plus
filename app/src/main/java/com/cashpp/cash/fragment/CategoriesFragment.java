package com.cashpp.cash.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashpp.cash.R;
import com.cashpp.cash.activity.MainActivity;


public class CategoriesFragment extends BaseFragment {
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public CategoriesFragment () {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setTitle(R.string.categories);
        View rootView = inflater.inflate(R.layout.fragment_categories,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.categoriestabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("categoriescategoriesfragment").setIndicator(getString(R.string.categories_categories)),
                CategoriesCategoriesFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("categoriesgraphicsfragment").setIndicator(getString(R.string.categories_graphics)),
                CategoriesGraphicsFragment.class, null);


        /*Mudar a cor das string das tabs*/
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

            final TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);

            // Look for the title view to ensure this is an indicator and not a divider.(I didn't know, it would return divider too, so I was getting an NPE)
            if (tv == null)
                continue;
            else
                tv.setTextColor(getResources().getColor(R.color.black));
        }

        return rootView;
    }

}
