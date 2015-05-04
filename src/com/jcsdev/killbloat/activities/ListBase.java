package com.jcsdev.killbloat.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import com.jcsdev.killbloat.FileManager;
import com.jcsdev.killbloat.PrefManager;
import com.jcsdev.killbloat.SettingsExporter;
import com.jcsdev.killbloat.Toaster;
import com.jcsdev.killbloat.enums.Pref;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListBase
  extends ListActivity
{
  protected ListItemAdapter adapter;
  protected Context context = this;
  protected LayoutInflater inflater;
  protected ListView listView;
  private Menu menu;
  private EditText searchbar = null;
  protected CharSequence[] tempExportList;
  protected TextView tempExtra;
  protected int tempListPos;
  
  protected boolean closeSearchbar()
  {
    if (this.searchbar == null) {}
    View localView;
    do
    {
      return false;
      localView = (View)this.searchbar.getParent();
    } while (localView.getVisibility() == 8);
    localView.setVisibility(8);
    this.searchbar.setText("");
    return true;
  }
  
  protected void enableSearchbar()
  {
    this.searchbar = ((EditText)findViewById(2131034114));
    this.searchbar.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        ListBase.this.adapter.getFilter().filter(paramAnonymousEditable);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
    if (this.menu != null) {
      this.menu.add(1, 5, 5, getString(2130968676)).setIcon(2130837511);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.inflater = ((LayoutInflater)getSystemService("layout_inflater"));
    this.adapter = new ListItemAdapter(this.inflater);
    this.adapter.disableItem(-1L);
    this.listView = getListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    this.menu = paramMenu;
    paramMenu.add(0, 2, 2, getString(2130968652)).setIcon(2130837510);
    paramMenu.add(0, 3, 3, getString(2130968653)).setIcon(2130837509);
    paramMenu.add(0, 4, 4, getString(2130968665)).setIcon(2130837508);
    if (this.searchbar != null) {
      paramMenu.add(1, 5, 5, getString(2130968676)).setIcon(2130837511);
    }
    return true;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
    case 4: 
      do
      {
        return super.onKeyDown(paramInt, paramKeyEvent);
      } while (!closeSearchbar());
      return true;
    }
    openSearchbar();
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return false;
      showExportDialog(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
      continue;
      showImportDialog();
      continue;
      this.tempExportList = new SettingsExporter().getExports();
      if (this.tempExportList.length > 0)
      {
        localBuilder.setTitle(getString(2130968666)).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
            new SettingsExporter().restoreDefault(ListBase.this.context);
            ListBase.this.finish();
            ListBase.this.startActivity(ListBase.this.getIntent());
          }
        }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        localBuilder.create().show();
      }
      else
      {
        Toaster.showToast(this, 2130968661);
        continue;
        openSearchbar();
      }
    }
  }
  
  protected void openSearchbar()
  {
    if (this.searchbar == null) {
      return;
    }
    ((View)this.searchbar.getParent()).setVisibility(0);
    this.searchbar.requestFocus();
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        ListBase.this.searchbar.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0F, 0.0F, 0));
        ListBase.this.searchbar.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0F, 0.0F, 0));
      }
    }, 200L);
  }
  
  protected void promptRemoveAll()
  {
    for (;;)
    {
      synchronized (PrefManager.csvLock)
      {
        final boolean bool = PrefManager.getPrefBoolean(this.context, Pref.DELETE);
        AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this.context);
        if (bool)
        {
          i = 2130968590;
          AlertDialog.Builder localBuilder2 = localBuilder1.setTitle(i);
          if (bool)
          {
            StringBuilder localStringBuilder = new StringBuilder(String.valueOf(getString(2130968592))).append("\n");
            if (PrefManager.getPrefBoolean(this.context, Pref.BACKUP))
            {
              str1 = getString(2130968594);
              str2 = str1;
              AlertDialog.Builder localBuilder3 = localBuilder2.setMessage(str2);
              if (!bool) {
                break label218;
              }
              j = 2130968596;
              localBuilder3.setPositiveButton(j, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                  if (bool)
                  {
                    paramAnonymousDialogInterface.dismiss();
                    FileManager.uninstallApps(PrefManager.getPrefBoolean(ListBase.this.context, Pref.BACKUP), jdField_this);
                    return;
                  }
                  paramAnonymousDialogInterface.dismiss();
                  FileManager.disableApps(ListBase.this.context);
                  ListBase.this.startActivity(new Intent(ListBase.this.context, DisabledApps.class));
                  ListBase.this.finish();
                }
              }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                  paramAnonymousDialogInterface.dismiss();
                }
              }).setNeutralButton(2130968598, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                  ListBase.this.startActivity(new Intent(ListBase.this.context, Blacklist.class));
                  paramAnonymousDialogInterface.dismiss();
                  ListBase.this.finish();
                }
              });
              localBuilder1.show();
              return;
            }
            String str1 = getString(2130968595);
            continue;
          }
          String str2 = getString(2130968593);
        }
      }
      int i = 2130968591;
      continue;
      label218:
      int j = 2130968597;
    }
  }
  
  protected void showExportDialog(CharSequence paramCharSequence)
  {
    final EditText localEditText = new EditText(this.context);
    localEditText.setText(paramCharSequence);
    localEditText.setSingleLine(true);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setTitle(getString(2130968662)).setView(localEditText).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Editable localEditable = localEditText.getText();
        paramAnonymousDialogInterface.dismiss();
        if (localEditable.length() == 0)
        {
          Toaster.showToast(ListBase.this.context, 2130968663);
          ListBase.this.showExportDialog(localEditable);
          return;
        }
        if (localEditable.toString().matches(".*[\\\\/:*?\"<>].*"))
        {
          Toaster.showToast(ListBase.this.context, 2130968664);
          ListBase.this.showExportDialog(localEditable);
          return;
        }
        new SettingsExporter().exportSettings(ListBase.this.context, localEditable);
      }
    }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.create().show();
  }
  
  protected void showImportDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    this.tempListPos = -1;
    this.tempExportList = new SettingsExporter().getExports();
    if (this.tempExportList.length > 0)
    {
      localBuilder.setTitle(getString(2130968660)).setSingleChoiceItems(this.tempExportList, -1, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ListBase.this.tempListPos = paramAnonymousInt;
        }
      }).setPositiveButton(2130968581, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          if (ListBase.this.tempListPos < 0)
          {
            Toaster.showToast(ListBase.this.context, 2130968668);
            ListBase.this.showImportDialog();
            return;
          }
          new SettingsExporter().importSettings(ListBase.this.context, ListBase.this.tempExportList[ListBase.this.tempListPos]);
          ListBase.this.finish();
          ListBase.this.startActivity(ListBase.this.getIntent());
        }
      }).setNegativeButton(2130968582, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localBuilder.create().show();
      return;
    }
    Toaster.showToast(this, 2130968661);
  }
}