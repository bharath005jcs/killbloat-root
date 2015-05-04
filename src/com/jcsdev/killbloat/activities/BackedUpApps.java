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

public class BackedUpApps
  extends ListBase
{
  LinkedList<App> appList;
  int tempSelection = -1;
  ProgressDialog waitingDialog;
  
  private void onClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    final int i = this.adapter.getRealPosition(paramInt);
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = getText(2130968645);
    arrayOfCharSequence[1] = getText(2130968631);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setSingleChoiceItems(arrayOfCharSequence, -1, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        BackedUpApps.this.tempSelection = paramAnonymousInt;
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
        paramAnonymousDialogInterface.dismiss();
        switch (BackedUpApps.this.tempSelection)
        {
        default: 
          return;
        case 0: 
          FileManager.installApp((App)BackedUpApps.this.appList.get(i), BackedUpApps.this.context);
          return;
        }
        FileManager.deleteBackup((App)BackedUpApps.this.appList.get(i), BackedUpApps.this.context);
        try
        {
          BackedUpApps.this.appList.remove(i);
          BackedUpApps.this.adapter.removeItem(i);
          return;
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
        {
          Toaster.showToast(BackedUpApps.this.context, 2130968675);
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
    localButton.setText(2130968649);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(BackedUpApps.this.context);
        localBuilder.setTitle(2130968650).setMessage(2130968651).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.dismiss();
          }
        }).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            FileManager.installApps(BackedUpApps.this.context);
            paramAnonymous2DialogInterface.dismiss();
          }
        });
        localBuilder.show();
      }
    });
    setListAdapter(this.adapter);
    enableSearchbar();
    this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        BackedUpApps.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
        return true;
      }
    });
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        BackedUpApps.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
      }
    });
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(2130968643);
    if (FileManager.getExternalDir() == null)
    {
      Toaster.showToast(this.context, 2130968657);
      return;
    }
    this.waitingDialog = ProgressDialog.show(this.context, "", getString(2130968587), true);
    this.waitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        BackedUpApps.this.setView();
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
        FileManager.enableApps(BackedUpApps.this.context);
        BackedUpApps.this.adapter.empty();
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
      BackedUpApps.this.appList = FileManager.getBackups(BackedUpApps.this.context);
      int i = BackedUpApps.this.appList.size();
      App[] arrayOfApp = (App[])BackedUpApps.this.appList.toArray(new App[i]);
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return null;
        }
        App localApp = arrayOfApp[j];
        ListItemAdapter localListItemAdapter = BackedUpApps.this.adapter;
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
      BackedUpApps.this.waitingDialog.dismiss();
    }
  }
}