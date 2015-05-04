package com.jcsdev.killbloat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Root
{
  public static boolean execute(ArrayList<String> paramArrayList)
  {
    boolean bool = true;
    if (paramArrayList != null) {}
    for (;;)
    {
      try
      {
        if (paramArrayList.size() > 0)
        {
          ProcessBuilder localProcessBuilder = new ProcessBuilder(new String[] { "su" });
          localProcessBuilder.redirectErrorStream(true);
          localProcess = localProcessBuilder.start();
          localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
          paramArrayList.add("exit");
          localIterator = paramArrayList.iterator();
          if (localIterator.hasNext()) {
            continue;
          }
          BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
          String str = localBufferedReader.readLine();
          if (str != null) {
            continue;
          }
        }
      }
      catch (IOException localIOException)
      {
        Process localProcess;
        DataOutputStream localDataOutputStream;
        Iterator localIterator;
        int i;
        localIOException.printStackTrace();
        return false;
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
        return false;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      try
      {
        i = localProcess.waitFor();
        if (255 == i) {
          bool = false;
        }
        return bool;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        return bool;
      }
      localDataOutputStream.writeBytes((String)localIterator.next() + "\n");
      localDataOutputStream.flush();
    }
    return false;
  }
  
  public static boolean hasRoot()
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("su");
      DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
      DataInputStream localDataInputStream = new DataInputStream(localProcess.getInputStream());
      boolean bool1 = false;
      if (localDataOutputStream != null)
      {
        bool1 = false;
        if (localDataInputStream != null)
        {
          localDataOutputStream.writeBytes("id\n");
          localDataOutputStream.flush();
          String str = localDataInputStream.readLine();
          int i;
          if (str == null)
          {
            bool1 = false;
            i = 0;
          }
          while (i != 0)
          {
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            return bool1;
            boolean bool2 = str.contains("uid=0");
            if (bool2)
            {
              bool1 = true;
              i = 1;
            }
            else
            {
              i = 1;
              bool1 = false;
            }
          }
        }
      }
      return bool1;
    }
    catch (Exception localException)
    {
      bool1 = false;
    }
  }
  
  public static ArrayList<String> ls(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      try
      {
        ProcessBuilder localProcessBuilder = new ProcessBuilder(new String[] { "su" });
        localProcessBuilder.redirectErrorStream(true);
        localProcess = localProcessBuilder.start();
        DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
        localDataOutputStream.writeBytes("ls \"" + paramString + "\"\n");
        localDataOutputStream.flush();
        localDataOutputStream.writeBytes("exit\n");
        localDataOutputStream.flush();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        str = localBufferedReader.readLine();
        if (str != null) {}
      }
      catch (IOException localIOException)
      {
        Process localProcess;
        String str;
        localIOException.printStackTrace();
        return localArrayList;
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
        return localArrayList;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      try
      {
        localProcess.waitFor();
        return localArrayList;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        return localArrayList;
      }
      if (!str.equals("")) {
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
}

