package com.jcsdev.killbloat.activities;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.jcsdev.killbloat.App;
import com.jcsdev.killbloat.FileManager;
import com.jcsdev.killbloat.PrefManager;
import com.jcsdev.killbloat.Toaster;
import com.jcsdev.killbloat.enums.ListItemType;
import java.util.LinkedList;

public class DisabledApps
  extends ListBase
{
  LinkedList<App> appList;
  int tempSelection;
  ProgressDialog waitingDialog;
  
  private void onClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    final int i = this.adapter.getRealPosition(paramInt);
    final App localApp = (App)this.appList.get(i);
    this.tempSelection = -1;
    CharSequence[] arrayOfCharSequence = new CharSequence[4];
    arrayOfCharSequence[0] = getText(2130968639);
    arrayOfCharSequence[1] = getText(2130968614);
    arrayOfCharSequence[2] = getText(2130968616);
    arrayOfCharSequence[3] = getText(2130968617);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setTitle(localApp.getLabel()).setSingleChoiceItems(arrayOfCharSequence, -1, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        DisabledApps.this.tempSelection = paramAnonymousInt;
      }
    }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    }).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        int i = 1;
        switch (DisabledApps.this.tempSelection)
        {
        default: 
          i = 0;
        }
        for (;;)
        {
          if (i != 0) {}
          try
          {
            DisabledApps.this.appList.remove(i);
            DisabledApps.this.adapter.removeItem(i);
            return;
          }
          catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
          {
            Toaster.showToast(DisabledApps.this.context, 2130968675);
          }
          FileManager.enableApp(localApp, DisabledApps.this.context);
          continue;
          FileManager.backupApp(localApp, DisabledApps.this.context);
          i = 0;
          continue;
          FileManager.uninstallApp(localApp, true, DisabledApps.this.context);
          continue;
          FileManager.uninstallApp(localApp, false, DisabledApps.this.context);
        }
      }
    });
    localBuilder.show();
  }
  
  private void setView()
  {
    setContentView(2130903041);
    this.listView = getListView();
    Button localButton = (Button)findViewById(2131034116);
    localButton.setText(2130968636);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DisabledApps.this.promptEnableAll();
      }
    });
    setListAdapter(this.adapter);
    enableSearchbar();
    this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        DisabledApps.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
        return true;
      }
    });
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        DisabledApps.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
      }
    });
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(2130968633);
    this.waitingDialog = ProgressDialog.show(this.context, "", getString(2130968587), true);
    this.waitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        DisabledApps.this.setView();
      }
    });
    synchronized (PrefManager.csvLock)
    {
      new ListLoader(null).execute(new Void[0]);
      return;
    }
  }
  
  protected void promptEnableAll()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setTitle(2130968637).setMessage(2130968638).setPositiveButton(2130968639, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        FileManager.enableApps(DisabledApps.this.context);
        DisabledApps.this.adapter.empty();
        paramAnonymousDialogInterface.dismiss();
      }
    }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.show();
  }
  
  private class ListLoader
    extends AsyncTask<Void, Void, Void>
  {
    private ListLoader() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      DisabledApps.this.appList = FileManager.getDisabledApps(DisabledApps.this.context);
      int i = DisabledApps.this.appList.size();
      App[] arrayOfApp = (App[])DisabledApps.this.appList.toArray(new App[i]);
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return null;
        }
        App localApp = arrayOfApp[j];
        ListItemAdapter localListItemAdapter = DisabledApps.this.adapter;
        String str = localApp.getLabel();
        ListItemType localListItemType = ListItemType.APP;
        long l = j;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = localApp.getPackageName();
        arrayOfString[1] = localApp.getSizeConverted();
        Drawable[] arrayOfDrawable = new Drawable[1];
        arrayOfDrawable[0] = localApp.getIcon();
        localListItemAdapter.addItem(str, localListItemType, l, arrayOfString, arrayOfDrawable);
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      DisabledApps.this.waitingDialog.dismiss();
    }
  }
}
