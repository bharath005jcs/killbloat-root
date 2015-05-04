package com.jcsdev.killbloat.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.jcsdev.killbloat.PrefManager;
import com.jcsdev.killbloat.enums.ListItemType;
import com.jcsdev.killbloat.enums.Pref;

public class GeneralSettings
  extends ListBase
{
  Pref pref;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.adapter.addItem(getString(2130968599), ListItemType.SEPARATOR, -1L, null);
    this.pref = Pref.DELETE;
    boolean bool = PrefManager.getPrefBoolean(this.context, this.pref);
    ListItemAdapter localListItemAdapter1 = this.adapter;
    String str1 = getString(2130968601);
    ListItemType localListItemType1 = ListItemType.CHECKBOX;
    long l1 = this.pref.ordinal();
    String[] arrayOfString1 = new String[2];
    String str2;
    int i;
    label100:
    int j;
    label150:
    ListItemAdapter localListItemAdapter2;
    String str3;
    ListItemType localListItemType2;
    long l2;
    String[] arrayOfString2;
    String str4;
    if (bool)
    {
      str2 = "true";
      arrayOfString1[0] = str2;
      if (!bool) {
        break label282;
      }
      i = 2130968602;
      arrayOfString1[1] = getString(i);
      localListItemAdapter1.addItem(str1, localListItemType1, l1, arrayOfString1);
      this.pref = Pref.BACKUP;
      if ((!bool) || (!PrefManager.getPrefBoolean(this.context, this.pref))) {
        break label289;
      }
      j = 1;
      localListItemAdapter2 = this.adapter;
      str3 = getString(2130968604);
      localListItemType2 = ListItemType.CHECKBOX;
      l2 = this.pref.ordinal();
      arrayOfString2 = new String[2];
      if (j == 0) {
        break label295;
      }
      str4 = "true";
      label194:
      arrayOfString2[0] = str4;
      if (j == 0) {
        break label302;
      }
    }
    label282:
    label289:
    label295:
    label302:
    for (int k = 2130968605;; k = 2130968606)
    {
      arrayOfString2[1] = getString(k);
      localListItemAdapter2.addItem(str3, localListItemType2, l2, arrayOfString2);
      setListAdapter(this.adapter);
      if (j == 0) {
        this.adapter.disableItem(Pref.BACKUP.ordinal());
      }
      this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          GeneralSettings.this.tempListPos = paramAnonymousInt;
          GeneralSettings.this.tempExtra = ((TextView)paramAnonymousView.findViewById(2131034119));
          Pref localPref = Pref.findPosition((int)paramAnonymousLong);
          CheckBox localCheckBox1 = (CheckBox)paramAnonymousView.findViewById(2131034122);
          if (localCheckBox1 == null)
          {
            $SWITCH_TABLE$com$jcsdev$killbloat$enums$Pref()[localPref.ordinal()];
            return;
          }
          String str1 = "";
          boolean bool1;
          ListItemAdapter localListItemAdapter2;
          int j;
          String[] arrayOfString2;
          if (localCheckBox1.isChecked())
          {
            bool1 = false;
            switch (localPref)
            {
            default: 
              GeneralSettings.this.tempExtra.setText(str1);
              localListItemAdapter2 = GeneralSettings.this.adapter;
              j = GeneralSettings.this.tempListPos;
              arrayOfString2 = new String[2];
              if (!bool1) {
                break;
              }
            }
          }
          for (String str4 = "true";; str4 = "false")
          {
            arrayOfString2[0] = str4;
            arrayOfString2[1] = str1;
            localListItemAdapter2.setExtra(j, arrayOfString2);
            localCheckBox1.setChecked(bool1);
            PrefManager.setPrefBoolean(GeneralSettings.this.context, localPref, bool1);
            return;
            bool1 = true;
            break;
            if (bool1) {}
            for (str1 = GeneralSettings.this.getString(2130968605);; str1 = GeneralSettings.this.getString(2130968606)) {
              break;
            }
            View localView = paramAnonymousAdapterView.getChildAt(1 + paramAnonymousAdapterView.indexOfChild(paramAnonymousView));
            CheckBox localCheckBox2 = null;
            if (localView != null) {
              localCheckBox2 = (CheckBox)localView.findViewById(2131034122);
            }
            String str2 = "";
            boolean bool2;
            label346:
            ListItemAdapter localListItemAdapter1;
            int i;
            String[] arrayOfString1;
            if (bool1)
            {
              str1 = GeneralSettings.this.getString(2130968602);
              GeneralSettings.this.adapter.enableItem(Pref.BACKUP.ordinal());
              bool2 = false;
              if (localView != null)
              {
                localView.setBackgroundColor(0);
                bool2 = PrefManager.getPrefBoolean(GeneralSettings.this.context, Pref.BACKUP);
                if (bool2) {
                  str2 = GeneralSettings.this.getString(2130968605);
                }
              }
              else
              {
                if (localView != null)
                {
                  localCheckBox2.setEnabled(bool1);
                  localCheckBox2.setChecked(bool2);
                  ((TextView)localView.findViewById(2131034119)).setText(str2);
                }
                localListItemAdapter1 = GeneralSettings.this.adapter;
                i = 1 + GeneralSettings.this.tempListPos;
                arrayOfString1 = new String[2];
                if (!bool2) {
                  break label513;
                }
              }
            }
            label513:
            for (String str3 = "true";; str3 = "false")
            {
              arrayOfString1[0] = str3;
              arrayOfString1[1] = str2;
              localListItemAdapter1.setExtra(i, arrayOfString1);
              break;
              str2 = GeneralSettings.this.getString(2130968606);
              break label346;
              str1 = GeneralSettings.this.getString(2130968603);
              GeneralSettings.this.adapter.disableItem(Pref.BACKUP.ordinal());
              bool2 = false;
              if (localView == null) {
                break label346;
              }
              localView.setBackgroundColor(-12303292);
              str2 = GeneralSettings.this.getString(2130968606);
              bool2 = false;
              break label346;
            }
          }
        }
      });
      return;
      str2 = "false";
      break;
      i = 2130968603;
      break label100;
      j = 0;
      break label150;
      str4 = "false";
      break label194;
    }
  }
}
