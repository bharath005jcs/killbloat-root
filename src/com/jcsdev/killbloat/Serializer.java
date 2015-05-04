package com.jcsdev.killbloat;

import com.android.vending.licensing.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer
{
  public static Object decrypt(String paramString)
  {
    try
    {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(paramString)));
      Object localObject = localObjectInputStream.readObject();
      localObjectInputStream.close();
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static String encrypt(Serializable paramSerializable)
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
      localObjectOutputStream.writeObject(paramSerializable);
      localObjectOutputStream.close();
      String str = new String(Base64.encode(localByteArrayOutputStream.toByteArray()));
      return str;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
}

