package com.jcsdev.killbloat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import com.jcsdev.killbloat.activities.BackedUpApps;
import com.jcsdev.killbloat.activities.Blacklist;
import com.jcsdev.killbloat.enums.Pref;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class FileManager
{
  public static final String[] EXTENSIONS = { ".apk", ".odex", ".deodex" };
  public static final String FOLDER = "killbloat";
  public static final String SYSTEM_APP_DIR = "/system/app/";
  private static ProgressDialog dialog;
  private static boolean doBackup = true;
  private static Context staticContext;
  private static boolean uninstalling = false;
  
  public static void backupApp(App paramApp)
  {
    backupApp(paramApp, null);
  }
  
  public static void backupApp(final App paramApp, Context paramContext)
  {
    if (paramContext != null) {
      staticContext = paramContext;
    }
    if (getExternalDir() == null)
    {
      if (paramContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
      return;
    }
    if ((!uninstalling) && (paramContext != null) && ((dialog == null) || (!dialog.isShowing())))
    {
      dialog = ProgressDialog.show(staticContext, "", "", true);
      dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          Toaster.showToast(FileManager.this, paramApp.getLabel() + " " + FileManager.this.getText(2130968615));
        }
      });
    }
    new AsyncBackupApp(null).execute(new App[] { paramApp });
  }
  
  public static void deleteBackup(App paramApp)
  {
    deleteBackup(paramApp, null);
  }
  
  public static void deleteBackup(App paramApp, Context paramContext)
  {
    String str = getExternalDir();
    if (str == null) {
      if (paramContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
    }
    do
    {
      return;
      deleteFolder(new File(str + paramApp.getPackageName()));
    } while (paramContext == null);
    Toaster.showToast(paramContext, paramApp.getLabel() + "'s " + paramContext.getText(2130968632));
  }
  
  public static void deleteFolder(File paramFile)
  {
    File[] arrayOfFile;
    int i;
    if (paramFile.isDirectory())
    {
      arrayOfFile = paramFile.listFiles();
      i = arrayOfFile.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        paramFile.delete();
        return;
      }
      deleteFolder(arrayOfFile[j]);
    }
  }
  
  public static void disableApp(App paramApp)
  {
    disableApp(paramApp, null);
  }
  
  public static void disableApp(App paramApp, Context paramContext)
  {
    String str1 = "/system/app/" + paramApp.getFileName();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = EXTENSIONS;
    int i = arrayOfString.length;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        executeFileCommands(localArrayList);
        if (paramContext != null) {
          Toaster.showToast(paramContext, paramApp.getLabel() + " " + paramContext.getText(2130968628));
        }
        return;
      }
      String str2 = arrayOfString[j];
      localArrayList.add("mv \"" + str1 + str2 + "\" \"" + str1 + str2 + "_\"");
    }
  }
  
  public static void disableApps(Context paramContext)
  {
    Iterator localIterator = PrefManager.getCsvList(paramContext, Pref.BLACKLIST).iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        Toaster.showToast(paramContext, 2130968627);
        return;
      }
      disableApp((App)localIterator.next());
    }
  }
  
  public static void enableApp(App paramApp)
  {
    enableApp(paramApp, null);
  }
  
  public static void enableApp(App paramApp, Context paramContext)
  {
    int i = 0;
    String str1 = "/system/app/" + paramApp.getFileName();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = EXTENSIONS;
    int j = arrayOfString.length;
    for (;;)
    {
      if (i >= j)
      {
        executeFileCommands(localArrayList);
        if (paramContext == null) {}
      }
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        paramApp.setLabel(localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(paramApp.getPackageName(), 0)).toString());
        label86:
        Toaster.showToast(paramContext, paramApp.getLabel() + " " + paramContext.getText(2130968642));
        return;
        String str2 = arrayOfString[i];
        localArrayList.add("mv \"" + str1 + str2 + "_\" \"" + str1 + str2 + "\"");
        i++;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        break label86;
      }
    }
  }
  
  public static void enableApps(Context paramContext)
  {
    LinkedList localLinkedList = getDisabledApps(paramContext);
    Iterator localIterator = localLinkedList.iterator();
    StringBuilder localStringBuilder;
    if (!localIterator.hasNext())
    {
      localStringBuilder = new StringBuilder(String.valueOf(localLinkedList.size())).append(" ");
      if (localLinkedList.size() != 1) {
        break label85;
      }
    }
    label85:
    for (int i = 2130968641;; i = 2130968640)
    {
      Toaster.showToast(paramContext, paramContext.getText(i));
      return;
      enableApp((App)localIterator.next());
      break;
    }
  }
  
  private static void executeFileCommands(ArrayList<String> paramArrayList)
  {
    paramArrayList.add(0, "mount -o rw,remount -t yaffs2 /dev/block/mtdblock3 /system");
    paramArrayList.add(0, "mount ext2 /dev/block/mmcblk3p3 /system");
    paramArrayList.add("mount -o ro,remount -t yaffs2 /dev/block/mtdblock3 /system");
    paramArrayList.add("mount ext2 /dev/block/mmcblk3p3 /system ro remount");
    Root.execute(paramArrayList);
  }
  
  public static LinkedList<App> getBackups(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    LinkedList localLinkedList1 = PrefManager.getCsvList(paramContext, Pref.BLACKLIST);
    int i = 0;
    LinkedList localLinkedList2 = new LinkedList();
    File[] arrayOfFile = new File(getExternalDir()).listFiles();
    int j = arrayOfFile.length;
    int k = 0;
    if (k >= j) {
      return localLinkedList2;
    }
    File localFile = arrayOfFile[k];
    Object localObject1 = null;
    String[] arrayOfString = localFile.list();
    if (arrayOfString == null) {}
    int n;
    label93:
    String str2;
    do
    {
      do
      {
        k++;
        break;
        int m = arrayOfString.length;
        n = 0;
        if (n < m) {
          break label270;
        }
      } while (localObject1 == null);
      str2 = getPackageName(localFile + "/" + (String)localObject1, localPackageManager);
    } while (str2 == null);
    Iterator localIterator = localLinkedList1.iterator();
    label148:
    boolean bool = localIterator.hasNext();
    Object localObject2 = null;
    label165:
    int i1;
    int i2;
    int i3;
    if (!bool)
    {
      if (localObject2 == null) {
        localObject2 = new App(((String)localObject1).replaceAll(".apk_", ".apk"), str2, (String)localObject1, new File(localFile + "/" + (String)localObject1).length(), paramContext.getResources().getDrawable(17301651));
      }
      i1 = 0;
      i2 = 0;
      i3 = i;
    }
    for (;;)
    {
      if (i2 == i3)
      {
        localLinkedList2.add(i1, localObject2);
        i++;
        break;
        label270:
        String str1 = arrayOfString[n];
        if ((str1.endsWith(".apk")) || (str1.endsWith(".apk_"))) {
          localObject1 = str1;
        }
        n++;
        break label93;
        App localApp = (App)localIterator.next();
        if (!localApp.getPackageName().equals(str2)) {
          break label148;
        }
        localObject2 = localApp;
        break label165;
      }
      i1 = i2 + (i3 - i2) / 2;
      if (((App)localObject2).getLabel().compareToIgnoreCase(((App)localLinkedList2.get(i1)).getLabel()) < 0)
      {
        i3 = i1;
      }
      else
      {
        i1++;
        i2 = i1;
      }
    }
  }
  
  public static LinkedList<App> getDisabledApps(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    LinkedList localLinkedList1 = PrefManager.getCsvList(paramContext, Pref.BLACKLIST);
    int i = 0;
    LinkedList localLinkedList2 = new LinkedList();
    String[] arrayOfString = new File("/system/app/").list();
    int j = arrayOfString.length;
    int k = 0;
    if (k >= j) {
      return localLinkedList2;
    }
    String str1 = arrayOfString[k];
    if (!str1.endsWith(".apk_")) {}
    String str2;
    do
    {
      k++;
      break;
      str2 = getPackageName("/system/app/" + str1, localPackageManager);
    } while (str2 == null);
    Iterator localIterator = localLinkedList1.iterator();
    label114:
    boolean bool = localIterator.hasNext();
    Object localObject = null;
    label131:
    int m;
    int n;
    int i1;
    if (!bool)
    {
      if (localObject == null)
      {
        String str3 = str1.replaceAll("\\.apk_$", ".apk");
        localObject = new App(str3, str2, str3, new File("/system/app/" + str1).length(), paramContext.getResources().getDrawable(17301651));
      }
      m = 0;
      n = 0;
      i1 = i;
    }
    for (;;)
    {
      if (n == i1)
      {
        localLinkedList2.add(m, localObject);
        i++;
        break;
        App localApp = (App)localIterator.next();
        if (!localApp.getPackageName().equals(str2)) {
          break label114;
        }
        localObject = localApp;
        break label131;
      }
      m = n + (i1 - n) / 2;
      if (((App)localObject).getLabel().compareToIgnoreCase(((App)localLinkedList2.get(m)).getLabel()) < 0)
      {
        i1 = m;
      }
      else
      {
        m++;
        n = m;
      }
    }
  }
  
  public static String getExternalDir()
  {
    try
    {
      File localFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath().replace("/0", "/legacy") + File.separator + "killbloat");
      if ((!localFile.exists()) && (!localFile.mkdir())) {
        return null;
      }
      String str = localFile + "/";
      return str;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static String getPackageName(String paramString, Context paramContext)
  {
    return getPackageName(paramString, paramContext.getPackageManager());
  }
  
  public static String getPackageName(String paramString, PackageManager paramPackageManager)
  {
    PackageInfo localPackageInfo = paramPackageManager.getPackageArchiveInfo(paramString, 0);
    if (localPackageInfo == null) {
      return null;
    }
    return localPackageInfo.packageName;
  }
  
  public static void installApp(App paramApp)
  {
    installApp(paramApp, null);
  }
  
  public static void installApp(App paramApp, Context paramContext)
  {
    staticContext = paramContext;
    if (getExternalDir() == null)
    {
      if (staticContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
      return;
    }
    if (paramContext != null)
    {
      dialog = ProgressDialog.show(paramContext, "", "", true);
      dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          if (FileManager.staticContext != null) {
            Toaster.showToast(FileManager.staticContext, FileManager.this.getLabel() + " " + FileManager.staticContext.getText(2130968646));
          }
        }
      });
    }
    new AsyncInstallApp(null).execute(new App[] { paramApp });
  }
  
  public static void installApps(Context paramContext)
  {
    staticContext = paramContext;
    if (getExternalDir() == null)
    {
      if (staticContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
      return;
    }
    LinkedList localLinkedList = getBackups(paramContext);
    dialog = ProgressDialog.show(staticContext, "", "", true);
    dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        Toaster.showToast(FileManager.staticContext, FileManager.staticContext.getText(2130968648));
      }
    });
    new AsyncInstallApp(null).execute((App[])localLinkedList.toArray(new App[localLinkedList.size()]));
  }
  
  public static boolean isBackedUp(App paramApp)
  {
    String str = getExternalDir();
    if (str == null) {
      return false;
    }
    return new File(str + paramApp.getPackageName()).exists();
  }
  
  public static boolean isDisabled(App paramApp)
  {
    return new File("/system/app/" + paramApp.getFileName() + ".apk_").exists();
  }
  
  public static boolean isInstalled(App paramApp, Context paramContext)
  {
    try
    {
      paramContext.getPackageManager().getApplicationInfo(paramApp.getPackageName(), 0);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  private static void rcopy(File paramFile1, File paramFile2, ArrayList<String> paramArrayList)
  {
    if (paramFile1.isDirectory())
    {
      paramArrayList.add("mkdir \"" + paramFile2 + "\"");
      Iterator localIterator = Root.ls(paramFile1.getAbsolutePath()).iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return;
        }
        String str = (String)localIterator.next();
        rcopy(new File(paramFile1 + "/" + str), new File(paramFile2 + "/" + str), paramArrayList);
      }
    }
    paramArrayList.add("cat \"" + paramFile1 + "\" > \"" + paramFile2 + "\"");
  }
  
  public static void syncBackupApp(App paramApp)
  {
    String str1 = "/system/app/" + paramApp.getFileName().replaceAll("/", "");
    String str2 = paramApp.getPackageName();
    File localFile1 = new File(getExternalDir() + str2);
    String str3 = localFile1 + "/" + paramApp.getFileName().replaceAll("/", "");
    if ((localFile1.exists()) && ((new File(str1 + ".apk").exists()) || (new File(str1 + ".apk_").exists()))) {
      deleteBackup(paramApp);
    }
    localFile1.mkdir();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = EXTENSIONS;
    int i = arrayOfString.length;
    int j = 0;
    if (j >= i)
    {
      rcopy(new File("/data/data/" + str2), new File(localFile1 + "/" + str2), localArrayList);
      executeFileCommands(localArrayList);
      return;
    }
    String str4 = arrayOfString[j];
    File localFile2 = new File(str1 + str4);
    if (localFile2.exists()) {
      rcopy(localFile2, new File(str3 + str4), localArrayList);
    }
    for (;;)
    {
      j++;
      break;
      File localFile3 = new File(str1 + str4 + "_");
      if (localFile3.exists()) {
        rcopy(localFile3, new File(str3 + str4 + "_"), localArrayList);
      }
    }
  }
  
  public static void uninstallApp(App paramApp, boolean paramBoolean)
  {
    uninstallApp(paramApp, paramBoolean, null);
  }
  
  public static void uninstallApp(final App paramApp, boolean paramBoolean, Context paramContext)
  {
    doBackup = paramBoolean;
    staticContext = paramContext;
    if ((paramBoolean) && (getExternalDir() == null))
    {
      if (staticContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
      return;
    }
    if (paramContext != null)
    {
      dialog = ProgressDialog.show(paramContext, "", "", true);
      dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          Context localContext1 = FileManager.this;
          StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramApp.getLabel())).append(" ");
          Context localContext2 = FileManager.this;
          if (FileManager.doBackup) {}
          for (int i = 2130968619;; i = 2130968618)
          {
            Toaster.showToast(localContext1, localContext2.getText(i));
            return;
          }
        }
      });
    }
    new AsyncUninstallApp(null).execute(new App[] { paramApp });
  }
  
  public static void uninstallApps(boolean paramBoolean, Activity paramActivity)
  {
    doBackup = paramBoolean;
    staticContext = paramActivity;
    if ((paramBoolean) && (getExternalDir() == null))
    {
      if (staticContext != null) {
        Toaster.showToast(staticContext, 2130968657);
      }
      return;
    }
    LinkedList localLinkedList = PrefManager.getCsvList(staticContext, Pref.BLACKLIST);
    dialog = ProgressDialog.show(staticContext, "", "", true);
    dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        int i;
        Context localContext3;
        Context localContext4;
        if (FileManager.staticContext != null)
        {
          Context localContext1 = FileManager.staticContext;
          Context localContext2 = FileManager.staticContext;
          if (!FileManager.doBackup) {
            break label78;
          }
          i = 2130968623;
          Toaster.showToast(localContext1, localContext2.getText(i));
          localContext3 = FileManager.staticContext;
          localContext4 = FileManager.staticContext;
          if (!FileManager.doBackup) {
            break label85;
          }
        }
        label78:
        label85:
        for (Object localObject = BackedUpApps.class;; localObject = Blacklist.class)
        {
          localContext3.startActivity(new Intent(localContext4, (Class)localObject));
          FileManager.this.finish();
          return;
          i = 2130968622;
          break;
        }
      }
    });
    new AsyncUninstallApp(null).execute((App[])localLinkedList.toArray(new App[localLinkedList.size()]));
  }
  
  private static class AsyncBackupApp
    extends AsyncTask<App, App, Void>
  {
    protected Void doInBackground(App... paramVarArgs)
    {
      int i = paramVarArgs.length;
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return null;
        }
        App localApp = paramVarArgs[j];
        publishProgress(new App[] { localApp });
        FileManager.syncBackupApp(localApp);
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      if ((FileManager.dialog != null) && (!FileManager.uninstalling)) {}
      try
      {
        FileManager.dialog.setCancelable(true);
        FileManager.dialog.hide();
        FileManager.dialog.dismiss();
        FileManager.dialog.cancel();
        if (FileManager.staticContext != null) {
          FileManager.dialog.setMessage(FileManager.staticContext.getText(2130968671));
        }
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Toaster.showToast(FileManager.staticContext, 2130968674);
      }
    }
    
    protected void onProgressUpdate(App... paramVarArgs)
    {
      super.onProgressUpdate(paramVarArgs);
      if ((FileManager.dialog != null) && (FileManager.staticContext != null)) {
        FileManager.dialog.setMessage(FileManager.staticContext.getText(2130968624) + " " + paramVarArgs[0].getLabel());
      }
    }
  }
  
  private static class AsyncInstallApp
    extends AsyncTask<App, App, Void>
  {
    protected Void doInBackground(App... paramVarArgs)
    {
      int i = paramVarArgs.length;
      int j = 0;
      if (j >= i) {
        return null;
      }
      App localApp = paramVarArgs[j];
      publishProgress(new App[] { localApp });
      String str1 = "/system/app/" + localApp.getFileName().replaceAll("/", "");
      String str2 = localApp.getPackageName();
      File localFile1 = new File(FileManager.getExternalDir() + str2);
      String str3 = localFile1 + "/" + localApp.getFileName().replaceAll("/", "");
      ArrayList localArrayList = new ArrayList();
      String[] arrayOfString1 = FileManager.EXTENSIONS;
      int k = arrayOfString1.length;
      int m = 0;
      label155:
      String[] arrayOfString2;
      int n;
      if (m >= k)
      {
        arrayOfString2 = FileManager.EXTENSIONS;
        n = arrayOfString2.length;
      }
      for (int i1 = 0;; i1++)
      {
        if (i1 >= n)
        {
          localArrayList.add("pm install \"" + str1 + ".apk\"");
          FileManager.rcopy(new File(localFile1 + "/" + str2), new File("/data/data/" + str2), localArrayList);
          FileManager.executeFileCommands(localArrayList);
          j++;
          break;
          String str4 = arrayOfString1[m];
          File localFile2 = new File(str3 + str4);
          if (localFile2.exists())
          {
            FileManager.rcopy(localFile2, new File(str1 + str4), localArrayList);
            localArrayList.add("chmod 644 \"" + str1 + str4 + "\"");
          }
          m++;
          break label155;
        }
        String str5 = arrayOfString2[i1];
        File localFile3 = new File(str3 + str5 + "_");
        if (localFile3.exists())
        {
          FileManager.rcopy(localFile3, new File(str1 + str5), localArrayList);
          localArrayList.add("chmod 644 \"" + str1 + str5 + "\"");
        }
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      if (FileManager.dialog != null) {}
      try
      {
        FileManager.dialog.setCancelable(true);
        FileManager.dialog.hide();
        FileManager.dialog.dismiss();
        FileManager.dialog.cancel();
        if (FileManager.staticContext != null) {
          FileManager.dialog.setMessage(FileManager.staticContext.getText(2130968670));
        }
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Toaster.showToast(FileManager.staticContext, 2130968673);
      }
    }
    
    protected void onProgressUpdate(App... paramVarArgs)
    {
      super.onProgressUpdate(paramVarArgs);
      if ((FileManager.dialog != null) && (FileManager.staticContext != null)) {
        FileManager.dialog.setMessage(FileManager.staticContext.getText(2130968647) + " " + paramVarArgs[0].getLabel());
      }
    }
  }
  
  private static class AsyncUninstallApp
    extends AsyncTask<App, App, Void>
  {
    protected Void doInBackground(App... paramVarArgs)
    {
      FileManager.uninstalling = true;
      int i = paramVarArgs.length;
      App localApp;
      for (int j = 0;; j++)
      {
        if (j >= i)
        {
          FileManager.uninstalling = false;
          return null;
        }
        localApp = paramVarArgs[j];
        publishProgress(new App[] { localApp });
        if ((FileManager.staticContext == null) || (FileManager.isInstalled(localApp, FileManager.staticContext)) || (FileManager.isDisabled(localApp))) {
          break;
        }
      }
      if (FileManager.doBackup) {
        FileManager.syncBackupApp(localApp);
      }
      ArrayList localArrayList = new ArrayList();
      String str1 = "/system/app/" + localApp.getFileName().replaceAll("/", "");
      String[] arrayOfString = FileManager.EXTENSIONS;
      int k = arrayOfString.length;
      for (int m = 0;; m++)
      {
        if (m >= k)
        {
          localArrayList.add("pm uninstall " + localApp.getPackageName());
          FileManager.executeFileCommands(localArrayList);
          break;
        }
        String str2 = arrayOfString[m];
        localArrayList.add("rm \"" + str1 + str2 + "\"");
        localArrayList.add("rm \"" + str1 + str2 + "_\"");
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      if (FileManager.dialog != null) {}
      try
      {
        FileManager.dialog.setCancelable(true);
        FileManager.dialog.hide();
        FileManager.dialog.dismiss();
        FileManager.dialog.cancel();
        if (FileManager.staticContext != null) {
          FileManager.dialog.setMessage(FileManager.staticContext.getText(2130968669));
        }
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Toaster.showToast(FileManager.staticContext, 2130968672);
      }
    }
    
    protected void onProgressUpdate(App... paramVarArgs)
    {
      super.onProgressUpdate(paramVarArgs);
      ProgressDialog localProgressDialog;
      StringBuilder localStringBuilder;
      Context localContext;
      if ((FileManager.dialog != null) && (FileManager.staticContext != null))
      {
        localProgressDialog = FileManager.dialog;
        localStringBuilder = new StringBuilder();
        localContext = FileManager.staticContext;
        if (!FileManager.doBackup) {
          break label77;
        }
      }
      label77:
      for (int i = 2130968620;; i = 2130968621)
      {
        localProgressDialog.setMessage(localContext.getText(i) + " " + paramVarArgs[0].getLabel());
        return;
      }
    }
  }
}
