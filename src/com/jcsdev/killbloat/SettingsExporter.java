package com.jcsdev.killbloat;

import android.content.Context;
import android.os.Environment;
import com.tvkdevelopment.killbloat.enums.Pref;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class SettingsExporter
{
  private final String FOLDER = "killbloat/Settings";
  
  private File getDir()
  {
    return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "killbloat/Settings");
  }
  
  public boolean exportSettings(Context paramContext, CharSequence paramCharSequence)
  {
    String str1 = "";
    Pref[] arrayOfPref = Pref.values();
    int i = arrayOfPref.length;
    int j = 0;
    if (j >= i) {}
    try
    {
      File localFile = getDir();
      if (!localFile.exists()) {
        localFile.mkdir();
      }
      BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(new File(localFile.getAbsolutePath() + File.separator + paramCharSequence)));
      localBufferedWriter.write(str1);
      localBufferedWriter.close();
      Toaster.showToast(paramContext, paramContext.getString(2130968659));
      return true;
    }
    catch (Exception localException)
    {
      Pref localPref;
      Toaster.showToast(paramContext, 2130968657);
    }
    localPref = arrayOfPref[j];
    if (!localPref.getExport()) {}
    for (;;)
    {
      j++;
      break;
      StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
      String str2;
      if (!str1.equals(""))
      {
        str2 = "\n";
        label164:
        str1 = str2 + localPref.getKey() + ":";
      }
      switch (localPref.getDataType())
      {
      default: 
        break;
      case BOOLEAN: 
        StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str1));
        if (PrefManager.getPrefBoolean(paramContext, localPref)) {}
        for (String str3 = "1";; str3 = "0")
        {
          str1 = str3;
          break;
          str2 = "";
          break label164;
        }
      case INT: 
        str1 = str1 + PrefManager.getPrefInt(paramContext, localPref);
        break;
      case STRING: 
        str1 = str1 + PrefManager.getPrefString(paramContext, localPref);
      }
    }
    return false;
  }
  
  public CharSequence[] getExports()
  {
    int i = 0;
    LinkedList localLinkedList = new LinkedList();
    File[] arrayOfFile = getDir().listFiles();
    if (arrayOfFile == null) {
      return new CharSequence[0];
    }
    int j = arrayOfFile.length;
    for (;;)
    {
      if (i >= j) {
        return (CharSequence[])localLinkedList.toArray(new CharSequence[localLinkedList.size()]);
      }
      File localFile = arrayOfFile[i];
      if (localFile.isFile()) {
        localLinkedList.add(localFile.getName());
      }
      i++;
    }
  }
  
  public boolean importSettings(Context paramContext, CharSequence paramCharSequence)
  {
    for (;;)
    {
      Pref localPref;
      String str2;
      try
      {
        DataInputStream localDataInputStream = new DataInputStream(new FileInputStream(getDir() + File.separator + paramCharSequence));
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localDataInputStream));
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
        {
          localDataInputStream.close();
          Toaster.showToast(paramContext, 2130968658);
          return true;
        }
        int i = str1.indexOf(':');
        localPref = Pref.findKey(str1.substring(0, i));
        if ((localPref == null) || (!localPref.getExport())) {
          continue;
        }
        str2 = str1.substring(i + 1);
        switch (localPref.getDataType())
        {
        case BOOLEAN: 
          PrefManager.setPrefBoolean(paramContext, localPref, str2.equals("1"));
          break;
        case INT: 
          try
          {
            PrefManager.setPrefInt(paramContext, localPref, Integer.parseInt(str2));
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
      }
      catch (Exception localException)
      {
        Toaster.showToast(paramContext, 2130968657);
        return false;
      }
      continue;
      PrefManager.setPrefString(paramContext, localPref, str2);
    }
  }
  
  public void restoreDefault(Context paramContext)
  {
    Pref[] arrayOfPref = Pref.values();
    int i = arrayOfPref.length;
    int j = 0;
    if (j >= i)
    {
      Toaster.showToast(paramContext, 2130968667);
      return;
    }
    Pref localPref = arrayOfPref[j];
    if (!localPref.getExport()) {}
    for (;;)
    {
      j++;
      break;
      switch (localPref.getDataType())
      {
      default: 
        break;
      case BOOLEAN: 
        PrefManager.setPrefBoolean(paramContext, localPref, localPref.getDefaultBoolean());
        break;
      case INT: 
        PrefManager.setPrefInt(paramContext, localPref, localPref.getDefaultInt());
        break;
      case STRING: 
        PrefManager.setPrefString(paramContext, localPref, localPref.getDefaultString());
      }
    }
  }
}

