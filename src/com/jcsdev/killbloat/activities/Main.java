package com.jcsdev.killbloat.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.jcsdev.killbloat.Root;
import com.jcsdev.killbloat.enums.ListItemType;

public class Main
  extends ListBase
{
  private void checkRoot()
  {
    if (!Root.hasRoot())
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
      localBuilder.setTitle(2130968577).setMessage(2130968578).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          Main.this.finish();
        }
      }).setNeutralButton(2130968583, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          try
          {
            Main.this.checkRoot();
            return;
          }
          catch (StackOverflowError localStackOverflowError)
          {
            Main.this.finish();
          }
        }
      }).setPositiveButton(2130968584, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          Main.this.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("http://www.addictivetips.com/mobile/how-to-root-your-android-phone-device/")));
          Main.this.finish();
        }
      });
      localBuilder.create().show();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.inflater = ((LayoutInflater)getSystemService("layout_inflater"));
    this.adapter = new ListItemAdapter(this.inflater);
    this.listView = getListView();
    this.listView.addHeaderView(getLayoutInflater().inflate(2130903040, null), null, false);
    this.adapter.addItem(getString(2130968586), ListItemType.SEPARATOR, -1L, null);
    ListItemAdapter localListItemAdapter1 = this.adapter;
    String str1 = getString(2130968599);
    ListItemType localListItemType1 = ListItemType.CATEGORY;
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = getString(2130968600);
    localListItemAdapter1.addItem(str1, localListItemType1, 0L, arrayOfString1);
    ListItemAdapter localListItemAdapter2 = this.adapter;
    String str2 = getString(2130968607);
    ListItemType localListItemType2 = ListItemType.CATEGORY;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = getString(2130968608);
    localListItemAdapter2.addItem(str2, localListItemType2, 1L, arrayOfString2);
    ListItemAdapter localListItemAdapter3 = this.adapter;
    String str3 = getString(2130968625);
    ListItemType localListItemType3 = ListItemType.CATEGORY;
    String[] arrayOfString3 = new String[1];
    arrayOfString3[0] = getString(2130968626);
    localListItemAdapter3.addItem(str3, localListItemType3, 2L, arrayOfString3);
    ListItemAdapter localListItemAdapter4 = this.adapter;
    String str4 = getString(2130968633);
    ListItemType localListItemType4 = ListItemType.CATEGORY;
    String[] arrayOfString4 = new String[1];
    arrayOfString4[0] = getString(2130968634);
    localListItemAdapter4.addItem(str4, localListItemType4, 3L, arrayOfString4);
    ListItemAdapter localListItemAdapter5 = this.adapter;
    String str5 = getString(2130968643);
    ListItemType localListItemType5 = ListItemType.CATEGORY;
    String[] arrayOfString5 = new String[1];
    arrayOfString5[0] = getString(2130968644);
    localListItemAdapter5.addItem(str5, localListItemType5, 4L, arrayOfString5);
    ListItemAdapter localListItemAdapter6 = this.adapter;
    String str6 = getString(2130968588);
    ListItemType localListItemType6 = ListItemType.RATE;
    String[] arrayOfString6 = new String[1];
    arrayOfString6[0] = getString(2130968589);
    localListItemAdapter6.addItem(str6, localListItemType6, 5L, arrayOfString6);
    setListAdapter(this.adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Main.this.tempListPos = (paramAnonymousInt - 1);
        Main.this.tempExtra = ((TextView)paramAnonymousView.findViewById(2131034119));
        switch ((int)paramAnonymousLong)
        {
        default: 
          return;
        case 0: 
          Main.this.startActivity(new Intent(Main.this.context, GeneralSettings.class));
          return;
        case 1: 
          Main.this.startActivity(new Intent(Main.this.context, SystemApps.class));
          return;
        case 2: 
          Main.this.startActivity(new Intent(Main.this.context, Blacklist.class));
          return;
        case 3: 
          Main.this.startActivity(new Intent(Main.this.context, DisabledApps.class));
          return;
        case 4: 
          Main.this.startActivity(new Intent(Main.this.context, BackedUpApps.class));
          return;
        }
        Main.this.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("market://details?id=com.jcsdev.killbloat")));
      }
    });
    checkRoot();
  }
}
