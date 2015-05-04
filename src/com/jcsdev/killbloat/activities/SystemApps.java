killbloat com.jcsdev.killbloat.activities;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.killbloatManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import com.jcsdev.nobloat.App;
import com.jcsdev.nobloat.FileManager;
import com.jcsdev.nobloat.FullApp;
import com.jcsdev.nobloat.PrefManager;
import com.jcsdev.nobloat.Toaster;
import com.jcsdev.nobloat.enums.ListItemType;
import com.jcsdev.nobloat.enums.Pref;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SystemApps
  extends ListBase
{
  LinkedList<FullApp> appList;
  int tempSelection;
  ProgressDialog waitingDialog;
  
  private void setView()
  {
    setContentView(2130903041);
    this.listView = getListView();
    Button localButton = (Button)findViewById(2131034116);
    if (PrefManager.getPrefBoolean(this.context, Pref.DELETE)) {}
    for (int i = 2130968611;; i = 2130968610)
    {
      localButton.setText(i);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          SystemApps.this.promptRemoveAll();
        }
      });
      setListAdapter(this.adapter);
      enableSearchbar();
      this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          int i = SystemApps.this.adapter.getRealPosition(paramAnonymousInt);
          CheckBox localCheckBox = (CheckBox)paramAnonymousView.findViewById(2131034122);
          FullApp localFullApp = (FullApp)SystemApps.this.appList.get(i);
          if (localCheckBox.isChecked())
          {
            localCheckBox.setChecked(false);
            SystemApps.this.adapter.setExtra(i, 2, "false");
            PrefManager.removeCsv(SystemApps.this.context, Pref.BLACKLIST, localFullApp.getkillbloatName());
            return;
          }
          localCheckBox.setChecked(true);
          SystemApps.this.adapter.setExtra(i, 2, "true");
          PrefManager.addCsv(SystemApps.this.context, Pref.BLACKLIST, localFullApp.getkillbloatName(), new App(localFullApp.getLabel(), localFullApp.getkillbloatName(), localFullApp.getFileName(), localFullApp.getSize(), localFullApp.getIcon()));
        }
      });
      this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
      {
        public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          final int i = SystemApps.this.adapter.getRealPosition(paramAnonymousInt);
          final FullApp localFullApp = (FullApp)SystemApps.this.appList.get(i);
          SystemApps.this.tempSelection = -1;
          CharSequence[] arrayOfCharSequence = new CharSequence[4];
          arrayOfCharSequence[0] = SystemApps.this.getText(2130968597);
          arrayOfCharSequence[1] = SystemApps.this.getText(2130968614);
          arrayOfCharSequence[2] = SystemApps.this.getText(2130968616);
          arrayOfCharSequence[3] = SystemApps.this.getText(2130968617);
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(SystemApps.this.context);
          localBuilder.setTitle(localFullApp.getLabel()).setSingleChoiceItems(arrayOfCharSequence, -1, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              SystemApps.this.tempSelection = paramAnonymous2Int;
            }
          }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              paramAnonymous2DialogInterface.dismiss();
            }
          }).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              int i = 1;
              switch (SystemApps.this.tempSelection)
              {
              default: 
                i = 0;
              }
              for (;;)
              {
                if (i != 0) {}
                try
                {
                  SystemApps.this.appList.remove(i);
                  SystemApps.this.adapter.removeItem(i);
                  return;
                }
                catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
                {
                  Toaster.showToast(SystemApps.this.context, 2130968675);
                }
                FileManager.disableApp(localFullApp, SystemApps.this.context);
                continue;
                FileManager.backupApp(localFullApp, SystemApps.this.context);
                i = 0;
                continue;
                FileManager.uninstallApp(localFullApp, true, SystemApps.this.context);
                continue;
                FileManager.uninstallApp(localFullApp, false, SystemApps.this.context);
              }
            }
          });
          localBuilder.show();
          return true;
        }
      });
      return;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(2130968607);
    this.waitingDialog = ProgressDialog.show(this.context, "", getString(2130968587), true);
    this.waitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        SystemApps.this.setView();
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
      killbloatManager localkillbloatManager = SystemApps.this.getkillbloatManager();
      List localList = localkillbloatManager.getInstalledApplications(0);
      LinkedList localLinkedList = PrefManager.getCsvKeys(SystemApps.this.context, Pref.BLACKLIST);
      int i = 0;
      SystemApps.this.appList = new LinkedList();
      Iterator localIterator1 = localList.iterator();
      int n;
      Iterator localIterator2;
      ApplicationInfo localApplicationInfo;
      do
      {
        if (!localIterator1.hasNext())
        {
          n = 0;
          localIterator2 = SystemApps.this.appList.iterator();
          if (localIterator2.hasNext()) {
            break;
          }
          return null;
        }
        localApplicationInfo = (ApplicationInfo)localIterator1.next();
      } while (((0x1 & localApplicationInfo.flags) == 0) || (!localApplicationInfo.sourceDir.startsWith("/system/app/")));
      FullApp localFullApp1 = new FullApp(localApplicationInfo.loadLabel(localkillbloatManager).toString(), localApplicationInfo.killbloatName, localApplicationInfo.sourceDir, new File(localApplicationInfo.sourceDir).length(), localApplicationInfo.loadIcon(localkillbloatManager));
      int j = 0;
      int k = 0;
      int m = i;
      for (;;)
      {
        if (k == m)
        {
          SystemApps.this.appList.add(j, localFullApp1);
          i++;
          break;
        }
        j = k + (m - k) / 2;
        if (localFullApp1.getLabel().compareToIgnoreCase(((FullApp)SystemApps.this.appList.get(j)).getLabel()) < 0)
        {
          m = j;
        }
        else
        {
          j++;
          k = j;
        }
      }
      FullApp localFullApp2 = (FullApp)localIterator2.next();
      ListItemAdapter localListItemAdapter = SystemApps.this.adapter;
      String str1 = localFullApp2.getLabel();
      ListItemType localListItemType = ListItemType.SYSTEM_APP;
      long l = n;
      String[] arrayOfString = new String[3];
      arrayOfString[0] = localFullApp2.getkillbloatName();
      arrayOfString[1] = localFullApp2.getSizeConverted();
      if (localLinkedList.contains(localFullApp2.getkillbloatName())) {}
      for (String str2 = "true";; str2 = "false")
      {
        arrayOfString[2] = str2;
        Drawable[] arrayOfDrawable = new Drawable[1];
        arrayOfDrawable[0] = localFullApp2.getIcon();
        localListItemAdapter.addItem(str1, localListItemType, l, arrayOfString, arrayOfDrawable);
        n++;
        break;
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      SystemApps.this.waitingDialog.dismiss();
    }
  }
}
