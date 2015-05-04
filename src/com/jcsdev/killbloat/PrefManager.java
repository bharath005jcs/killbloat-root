package com.jcsdev.killbloat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import com.android.vending.licensing.util.Base64;
import com.android.vending.licensing.util.Base64DecoderException;
import com.jcsdev.killbloat.enums.Pref;
import java.io.Serializable;
import java.util.LinkedList;

public class PrefManager
{
  public static final String PREF_NAME = "Settings";
  public static Object csvLock = new Object();
  private static SharedPreferences settings;
  
  public static void addCsv(Context paramContext, Pref paramPref, String paramString, Serializable paramSerializable)
  {
    new AsyncTask()
    {
      protected Long doInBackground(Object... paramAnonymousVarArgs)
      {
        for (int i = 0;; i++) {
          synchronized (PrefManager.csvLock)
          {
            Context localContext = (Context)paramAnonymousVarArgs[0];
            Pref localPref = (Pref)paramAnonymousVarArgs[1];
            String str1 = (String)paramAnonymousVarArgs[2];
            Serializable localSerializable = (Serializable)paramAnonymousVarArgs[3];
            String str2 = Base64.encode(str1.getBytes());
            String str3 = str2 + ":" + Serializer.encrypt(localSerializable);
            String[] arrayOfString = PrefManager.getPrefString(localContext, localPref).split(",");
            int j = arrayOfString.length;
            if (i >= j)
            {
              PrefManager.setPrefString(localContext, localPref, str3);
              return null;
            }
            String str4 = arrayOfString[i];
            int k = str4.indexOf(":");
            if ((k != -1) && (!str2.equals(str4.substring(0, k)))) {
              str3 = str3 + "," + str4;
            }
          }
        }
      }
    }.execute(new Object[] { paramContext, paramPref, paramString, paramSerializable });
  }
  
  public static LinkedList<String> getCsvKeys(Context paramContext, Pref paramPref)
  {
    int i = 0;
    LinkedList localLinkedList = new LinkedList();
    String[] arrayOfString = getPrefString(paramContext, paramPref).split(",");
    int j = arrayOfString.length;
    if (i >= j) {
      return localLinkedList;
    }
    String str = arrayOfString[i];
    int k = str.indexOf(":");
    if (k == -1) {}
    for (;;)
    {
      i++;
      break;
      try
      {
        localLinkedList.add(new String(Base64.decode(str.substring(0, k))));
      }
      catch (Base64DecoderException localBase64DecoderException) {}
    }
  }
  
  public static <T> LinkedList<T> getCsvList(Context paramContext, Pref paramPref)
  {
    LinkedList localLinkedList = new LinkedList();
    String[] arrayOfString = getPrefString(paramContext, paramPref).split(",");
    int i = arrayOfString.length;
    int j = 0;
    if (j >= i) {
      return localLinkedList;
    }
    String str = arrayOfString[j];
    int k = str.indexOf(":");
    if (k == -1) {}
    for (;;)
    {
      j++;
      break;
      localLinkedList.add(Serializer.decrypt(str.substring(k + 1, str.length())));
    }
  }
  
  public static boolean getPrefBoolean(Context paramContext, Pref paramPref)
  {
    initSettings(paramContext);
    return settings.getBoolean(paramPref.getKey(), paramPref.getDefaultBoolean());
  }
  
  public static int getPrefInt(Context paramContext, Pref paramPref)
  {
    initSettings(paramContext);
    return settings.getInt(paramPref.getKey(), paramPref.getDefaultInt());
  }
  
  public static String getPrefString(Context paramContext, Pref paramPref)
  {
    initSettings(paramContext);
    return settings.getString(paramPref.getKey(), paramPref.getDefaultString());
  }
  
  private static void initSettings(Context paramContext)
  {
    if (settings == null) {
      settings = paramContext.getSharedPreferences("Settings", 0);
    }
  }
  
  public static void removeCsv(Context paramContext, Pref paramPref, String paramString)
  {
    new AsyncTask()
    {
      protected Long doInBackground(Object... paramAnonymousVarArgs)
      {
        for (;;)
        {
          int j;
          synchronized (PrefManager.csvLock)
          {
            Context localContext = (Context)paramAnonymousVarArgs[0];
            Pref localPref = (Pref)paramAnonymousVarArgs[1];
            String str1 = Base64.encode(((String)paramAnonymousVarArgs[2]).getBytes());
            String str2 = "";
            String[] arrayOfString = PrefManager.getPrefString(localContext, localPref).split(",");
            int i = arrayOfString.length;
            j = 0;
            if (j >= i)
            {
              PrefManager.setPrefString(localContext, localPref, str2);
              return null;
            }
            String str3 = arrayOfString[j];
            int k = str3.indexOf(":");
            if ((k != -1) && (!str1.equals(str3.substring(0, k))))
            {
              StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str2));
              if (str2.equals("")) {
                break label182;
              }
              str4 = ",";
              str2 = str4 + str3;
            }
          }
          j++;
          continue;
          label182:
          String str4 = "";
        }
      }
    }.execute(new Object[] { paramContext, paramPref, paramString });
  }
  
  public static void setPrefBoolean(Context paramContext, Pref paramPref, boolean paramBoolean)
  {
    initSettings(paramContext);
    SharedPreferences.Editor localEditor = settings.edit();
    localEditor.putBoolean(paramPref.getKey(), paramBoolean);
    localEditor.commit();
  }
  
  public static void setPrefInt(Context paramContext, Pref paramPref, int paramInt)
  {
    initSettings(paramContext);
    SharedPreferences.Editor localEditor = settings.edit();
    localEditor.putInt(paramPref.getKey(), paramInt);
    localEditor.commit();
  }
  
  public static void setPrefString(Context paramContext, Pref paramPref, String paramString)
  {
    initSettings(paramContext);
    SharedPreferences.Editor localEditor = settings.edit();
    localEditor.putString(paramPref.getKey(), paramString);
    localEditor.commit();
  }
}
