package com.vipin.assessortesta.Initials;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

   int mNoOfTabs;

   public PageAdapter(FragmentManager fm, int NumberOfTabs)
   {
       super(fm);
       this.mNoOfTabs = NumberOfTabs;

   }



    @Override
    public Fragment getItem(int Position) {
       switch (Position)
       {
           case 0:

               Upcoming upcoming = new Upcoming();
               return upcoming;

           case  1:
               Complete complete = new Complete();
               return complete;


           case  2:
               Overdue overdue = new Overdue();
               return overdue;

               default:
                   return null;

       }


    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
