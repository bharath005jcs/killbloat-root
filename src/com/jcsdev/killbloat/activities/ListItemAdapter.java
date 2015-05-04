package com.jcsdev.killbloat.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.jcsdev.killbloat.Toaster;
import com.jcsdev.killbloat.enums.ListItemType;
import java.util.ArrayList;

public class ListItemAdapter
  extends BaseAdapter
  implements Filterable
{
  private ListItemAdapterFilter adapterFilter = null;
  private ArrayList<Integer> filter = null;
  LayoutInflater inflater;
  private CharSequence lastConstraint = null;
  private ArrayList<Long> listDisabled = new ArrayList();
  private ArrayList<Drawable[]> listDrawables = new ArrayList();
  private ArrayList<String[]> listExtras = new ArrayList();
  private ArrayList<Long> listIds = new ArrayList();
  private ArrayList<String> listItems = new ArrayList();
  private ArrayList<ListItemType> listTypes = new ArrayList();
  
  public ListItemAdapter(LayoutInflater paramLayoutInflater)
  {
    this.inflater = paramLayoutInflater;
  }
  
  public void addItem(String paramString, ListItemType paramListItemType, long paramLong, String[] paramArrayOfString)
  {
    addItem(paramString, paramListItemType, paramLong, paramArrayOfString, null);
  }
  
  public void addItem(String paramString, ListItemType paramListItemType, long paramLong, String[] paramArrayOfString, Drawable[] paramArrayOfDrawable)
  {
    this.listItems.add(paramString);
    this.listTypes.add(paramListItemType);
    this.listIds.add(Long.valueOf(paramLong));
    this.listExtras.add(paramArrayOfString);
    this.listDrawables.add(paramArrayOfDrawable);
  }
  
  public boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public void disableItem(long paramLong)
  {
    this.listDisabled.add(Long.valueOf(paramLong));
  }
  
  public void empty()
  {
    for (;;)
    {
      if (this.listIds.size() <= 0) {
        return;
      }
      removeItem(0);
    }
  }
  
  public void enableItem(long paramLong)
  {
    this.listDisabled.remove(Long.valueOf(paramLong));
  }
  
  public int getCount()
  {
    if (this.filter != null) {
      return this.filter.size();
    }
    return this.listItems.size();
  }
  
  public Filter getFilter()
  {
    if (this.adapterFilter == null) {
      this.adapterFilter = new ListItemAdapterFilter(null);
    }
    return this.adapterFilter;
  }
  
  public Object getItem(int paramInt)
  {
    return this.listItems.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    try
    {
      long l = ((Long)this.listIds.get(paramInt)).longValue();
      return l;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      Toaster.showToast(this.inflater.getContext(), 2130968675);
    }
    return -1L;
  }
  
  public int getItemViewType(int paramInt)
  {
    return ((ListItemType)this.listTypes.get(paramInt)).ordinal();
  }
  
  public int getRealPosition(int paramInt)
  {
    if (this.filter == null) {
      return paramInt;
    }
    return ((Integer)this.filter.get(paramInt)).intValue();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = getRealPosition(paramInt);
    ListItemType localListItemType = ListItemType.findPosition(getItemViewType(i));
    String str = "";
    int j = $SWITCH_TABLE$com$jcsdev$killbloat$enums$ListItemType()[localListItemType.ordinal()];
    TextView localTextView1 = null;
    TextView localTextView2 = null;
    View localView = null;
    switch (j)
    {
    }
    for (;;)
    {
      if (localTextView2 != null) {
        localTextView2.setText((CharSequence)this.listItems.get(i));
      }
      if (localTextView1 != null) {
        localTextView1.setText(str);
      }
      return localView;
      localView = this.inflater.inflate(2130903049, null);
      localTextView2 = (TextView)localView.findViewById(2131034118);
      localTextView1 = null;
      continue;
      localView = this.inflater.inflate(2130903046, null);
      localTextView2 = (TextView)localView.findViewById(2131034118);
      localTextView1 = (TextView)localView.findViewById(2131034119);
      String[] arrayOfString6 = (String[])this.listExtras.get(i);
      if ((arrayOfString6 != null) && (arrayOfString6.length > 0))
      {
        str = arrayOfString6[0];
        if (arrayOfString6.length > 1) {
          if (arrayOfString6[1].equals("u"))
          {
            localTextView2.setTextColor(Color.rgb(230, 130, 40));
          }
          else if (arrayOfString6[1].equals("d"))
          {
            localTextView2.setTextColor(Color.rgb(230, 230, 40));
          }
          else if (arrayOfString6[1].equals("a"))
          {
            localTextView2.setTextColor(Color.rgb(150, 15, 230));
          }
          else if (arrayOfString6[1].equals("s"))
          {
            localTextView2.setTextColor(Color.rgb(200, 5, 5));
            continue;
            localView = this.inflater.inflate(2130903045, null);
            localTextView2 = (TextView)localView.findViewById(2131034118);
            CheckBox localCheckBox = (CheckBox)localView.findViewById(2131034122);
            localCheckBox.setChecked(((String[])this.listExtras.get(i))[0].equals("true"));
            String[] arrayOfString5 = (String[])this.listExtras.get(i);
            localTextView1 = (TextView)localView.findViewById(2131034119);
            if ((arrayOfString5 != null) && (arrayOfString5.length > 0) && (!arrayOfString5[1].equals(""))) {
              str = arrayOfString5[1];
            }
            if (!isEnabled(i))
            {
              localView.setBackgroundColor(-12303292);
              localCheckBox.setEnabled(false);
              continue;
              localView = this.inflater.inflate(2130903047, null);
              localTextView2 = (TextView)localView.findViewById(2131034118);
              String[] arrayOfString4 = (String[])this.listExtras.get(i);
              localTextView1 = (TextView)localView.findViewById(2131034119);
              if ((arrayOfString4 != null) && (arrayOfString4.length > 0) && (!arrayOfString4[0].equals("")))
              {
                str = arrayOfString4[0];
                continue;
                localView = this.inflater.inflate(2130903048, null);
                if (localView == null) {
                  localView = this.inflater.inflate(2130903043, null);
                }
                if (localView == null) {
                  localView = this.inflater.inflate(2130903044, null);
                }
                localTextView2 = (TextView)localView.findViewById(2131034118);
                String[] arrayOfString3 = (String[])this.listExtras.get(i);
                localTextView1 = (TextView)localView.findViewById(2131034119);
                if ((arrayOfString3 != null) && (arrayOfString3.length > 0) && (!arrayOfString3[0].equals("")))
                {
                  str = arrayOfString3[0];
                  continue;
                  localView = this.inflater.inflate(2130903050, null);
                  localTextView2 = (TextView)localView.findViewById(2131034118);
                  String[] arrayOfString2 = (String[])this.listExtras.get(i);
                  localTextView1 = (TextView)localView.findViewById(2131034119);
                  if ((arrayOfString2 != null) && (arrayOfString2.length > 0) && (!arrayOfString2[0].equals("")))
                  {
                    str = arrayOfString2[0];
                    if (arrayOfString2.length > 1)
                    {
                      ((TextView)localView.findViewById(2131034120)).setText(arrayOfString2[1]);
                      if (arrayOfString2.length > 2) {
                        ((CheckBox)localView.findViewById(2131034122)).setChecked(((String[])this.listExtras.get(i))[2].equals("true"));
                      }
                    }
                  }
                  ((ImageView)localView.findViewById(2131034117)).setImageDrawable(((Drawable[])this.listDrawables.get(i))[0]);
                  continue;
                  localView = this.inflater.inflate(2130903042, null);
                  localTextView2 = (TextView)localView.findViewById(2131034118);
                  String[] arrayOfString1 = (String[])this.listExtras.get(i);
                  localTextView1 = (TextView)localView.findViewById(2131034119);
                  if ((arrayOfString1 != null) && (arrayOfString1.length > 0) && (!arrayOfString1[0].equals("")))
                  {
                    str = arrayOfString1[0];
                    if (arrayOfString1.length > 1) {
                      ((TextView)localView.findViewById(2131034120)).setText(arrayOfString1[1]);
                    }
                  }
                  ((ImageView)localView.findViewById(2131034117)).setImageDrawable(((Drawable[])this.listDrawables.get(i))[0]);
                }
              }
            }
          }
        }
      }
    }
  }
  
  public int getViewTypeCount()
  {
    return ListItemType.values().length;
  }
  
  public boolean isEnabled(int paramInt)
  {
    return !this.listDisabled.contains(this.listIds.get(paramInt));
  }
  
  public void removeItem(int paramInt)
  {
    this.listItems.remove(paramInt);
    this.listTypes.remove(paramInt);
    this.listIds.remove(paramInt);
    this.listExtras.remove(paramInt);
    this.listDrawables.remove(paramInt);
    getFilter().filter(this.lastConstraint);
  }
  
  public void setExtra(int paramInt1, int paramInt2, String paramString)
  {
    ((String[])this.listExtras.get(paramInt1))[paramInt2] = paramString;
  }
  
  public void setExtra(int paramInt, String[] paramArrayOfString)
  {
    this.listExtras.set(paramInt, paramArrayOfString);
  }
  
  private class ListItemAdapterFilter
    extends Filter
  {
    private ListItemAdapterFilter() {}
    
    protected Filter.FilterResults performFiltering(CharSequence paramCharSequence)
    {
      ListItemAdapter.this.lastConstraint = paramCharSequence;
      if ((paramCharSequence == null) || (paramCharSequence.length() == 0)) {
        return null;
      }
      String str = paramCharSequence.toString().toLowerCase();
      Filter.FilterResults localFilterResults = new Filter.FilterResults();
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      int j = ListItemAdapter.this.listItems.size();
      if (i >= j)
      {
        localFilterResults.values = localArrayList;
        localFilterResults.count = localArrayList.size();
        return localFilterResults;
      }
      String[] arrayOfString;
      int m;
      label139:
      int n;
      if (!((String)ListItemAdapter.this.listItems.get(i)).toLowerCase().contains(str))
      {
        arrayOfString = (String[])ListItemAdapter.this.listExtras.get(i);
        int k = arrayOfString.length;
        m = 0;
        n = 0;
        if (m >= k) {
          label149:
          if (n != 0) {
            break label187;
          }
        }
      }
      for (;;)
      {
        i++;
        break;
        if (arrayOfString[m].toLowerCase().contains(str))
        {
          n = 1;
          break label149;
        }
        m++;
        break label139;
        label187:
        localArrayList.add(Integer.valueOf(i));
      }
    }
    
    protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
    {
      ListItemAdapter localListItemAdapter = ListItemAdapter.this;
      if (paramFilterResults == null) {}
      for (ArrayList localArrayList = null;; localArrayList = (ArrayList)paramFilterResults.values)
      {
        localListItemAdapter.filter = localArrayList;
        ListItemAdapter.this.notifyDataSetChanged();
        return;
      }
    }
  }
}
