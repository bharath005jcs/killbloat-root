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
import com.jcsdev.killbloat.enums.Pref;
import java.util.Iterator;
import java.util.LinkedList;

public class Blacklist
  extends ListBase
{
  boolean appBackedUp;
  boolean appDisabled;
  boolean appInstalled;
  LinkedList<App> appList;
  int tempSelection;
  ProgressDialog waitingDialog;
  
  private void onClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    final int i = this.adapter.getRealPosition(paramInt);
    final App localApp = (App)this.appList.get(i);
    this.tempSelection = -1;
    CharSequence[] arrayOfCharSequence;
    if (FileManager.isInstalled(localApp, this.context))
    {
      this.appInstalled = true;
      arrayOfCharSequence = new CharSequence[5];
      arrayOfCharSequence[0] = getText(2130968629);
      arrayOfCharSequence[1] = getText(2130968597);
      arrayOfCharSequence[2] = getText(2130968614);
      arrayOfCharSequence[3] = getText(2130968616);
      arrayOfCharSequence[4] = getText(2130968617);
    }
    for (;;)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
      localBuilder.setTitle(localApp.getLabel()).setSingleChoiceItems(arrayOfCharSequence, -1, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Blacklist.this.tempSelection = paramAnonymousInt;
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
          if (Blacklist.this.tempSelection == 0) {
            PrefManager.removeCsv(Blacklist.this.context, Pref.BLACKLIST, localApp.getPackageName());
          }
          do
          {
            try
            {
              Blacklist.this.appList.remove(i);
              Blacklist.this.adapter.removeItem(i);
              return;
            }
            catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
            {
              Toaster.showToast(Blacklist.this.context, 2130968675);
              return;
            }
            if (Blacklist.this.appInstalled)
            {
              switch (Blacklist.this.tempSelection)
              {
              default: 
                return;
              case 1: 
                FileManager.disableApp(localApp, Blacklist.this.context);
                return;
              case 2: 
                FileManager.backupApp(localApp, Blacklist.this.context);
                return;
              case 3: 
                FileManager.uninstallApp(localApp, true, Blacklist.this.context);
                return;
              }
              FileManager.uninstallApp(localApp, false, Blacklist.this.context);
              return;
            }
            if (Blacklist.this.appDisabled)
            {
              switch (Blacklist.this.tempSelection)
              {
              default: 
                return;
              case 1: 
                FileManager.enableApp(localApp, Blacklist.this.context);
                return;
              case 2: 
                FileManager.backupApp(localApp, Blacklist.this.context);
                return;
              case 3: 
                FileManager.uninstallApp(localApp, true, Blacklist.this.context);
                return;
              }
              FileManager.uninstallApp(localApp, false, Blacklist.this.context);
              return;
            }
          } while (!Blacklist.this.appBackedUp);
          switch (Blacklist.this.tempSelection)
          {
          default: 
            return;
          case 1: 
            FileManager.installApp(localApp, Blacklist.this.context);
            return;
          }
          FileManager.deleteBackup(localApp, Blacklist.this.context);
        }
      });
      localBuilder.show();
      return;
      this.appInstalled = false;
      if (FileManager.isDisabled(localApp))
      {
        this.appDisabled = true;
        arrayOfCharSequence = new CharSequence[5];
        arrayOfCharSequence[0] = getText(2130968629);
        arrayOfCharSequence[1] = getText(2130968639);
        arrayOfCharSequence[2] = getText(2130968614);
        arrayOfCharSequence[3] = getText(2130968616);
        arrayOfCharSequence[4] = getText(2130968617);
      }
      else
      {
        this.appDisabled = false;
        if (FileManager.isBackedUp(localApp))
        {
          this.appBackedUp = true;
          arrayOfCharSequence = new CharSequence[3];
          arrayOfCharSequence[0] = getText(2130968629);
          arrayOfCharSequence[1] = getText(2130968630);
          arrayOfCharSequence[2] = getText(2130968631);
        }
        else
        {
          this.appBackedUp = false;
          arrayOfCharSequence = new CharSequence[1];
          arrayOfCharSequence[0] = getText(2130968629);
        }
      }
    }
  }
  
  private void setView()
  {
    setContentView(2130903041);
    this.listView = getListView();
    Button localButton = (Button)findViewById(2131034116);
    if (PrefManager.getPrefBoolean(this.context, Pref.DELETE)) {}
    for (int i = 2130968613;; i = 2130968612)
    {
      localButton.setText(i);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Blacklist.this.promptRemoveAll();
        }
      });
      setListAdapter(this.adapter);
      enableSearchbar();
      this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
      {
        public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          Blacklist.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
          return true;
        }
      });
      this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          Blacklist.this.onClick(paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
        }
      });
      return;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(2130968625);
    this.waitingDialog = ProgressDialog.show(this.context, "", getString(2130968587), true);
    this.waitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        Blacklist.this.setView();
      }
    });
    synchronized (PrefManager.csvLock)
    {
      new ListLoader(null).execute(new Void[0]);
      return;
    }
  }
  
  private class ListLoader
    extends AsyncTask<Void, Void, Void>
  {
    private ListLoader() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      LinkedList localLinkedList = PrefManager.getCsvList(Blacklist.this.context, Pref.BLACKLIST);
      int i = 0;
      Blacklist.this.appList = new LinkedList();
      Iterator localIterator1 = localLinkedList.iterator();
      int n;
      Iterator localIterator2;
      if (!localIterator1.hasNext())
      {
        n = 0;
        localIterator2 = Blacklist.this.appList.iterator();
      }
      for (;;)
      {
        if (!localIterator2.hasNext())
        {
          return null;
          App localApp1 = (App)localIterator1.next();
          int j = 0;
          int k = 0;
          int m = i;
          for (;;)
          {
            if (k == m)
            {
              Blacklist.this.appList.add(j, localApp1);
              i++;
              break;
            }
            j = k + (m - k) / 2;
            if (localApp1.getLabel().compareToIgnoreCase(((App)Blacklist.this.appList.get(j)).getLabel()) < 0)
            {
              m = j;
            }
            else
            {
              j++;
              k = j;
            }
          }
        }
        App localApp2 = (App)localIterator2.next();
        ListItemAdapter localListItemAdapter = Blacklist.this.adapter;
        String str = localApp2.getLabel();
        ListItemType localListItemType = ListItemType.APP;
        long l = n;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = localApp2.getPackageName();
        arrayOfString[1] = localApp2.getSizeConverted();
        Drawable[] arrayOfDrawable = new Drawable[1];
        arrayOfDrawable[0] = localApp2.getIcon();
        localListItemAdapter.addItem(str, localListItemType, l, arrayOfString, arrayOfDrawable);
        n++;
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      Blacklist.this.waitingDialog.dismiss();
    }
  }
}
