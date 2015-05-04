package com.jcsdev.killbloat;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class App
  implements Serializable
{
  private static final long serialVersionUID = 8450556904515906572L;
  private String fileName;
  private byte[] icon;
  private String label;
  private String packageName;
  private long size;
  
  public App() {}
  
  public App(String paramString1, String paramString2, String paramString3, long paramLong, Drawable paramDrawable)
  {
    setLabel(paramString1);
    setPackageName(paramString2);
    setFileName(paramString3);
    setSize(paramLong);
    setIcon(paramDrawable);
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public Drawable getIcon()
  {
    return new BitmapDrawable(BitmapFactory.decodeByteArray(this.icon, 0, this.icon.length));
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public String getPackageName()
  {
    return this.packageName;
  }
  
  public long getSize()
  {
    return this.size;
  }
  
  public String getSizeConverted()
  {
    if (this.size < 1024) {
      return this.size + " B";
    }
    int i = (int)(Math.log(this.size) / Math.log(1024));
    char c = "KMGTPE".charAt(i - 1);
    return Math.round(10.0D * (this.size / Math.pow(1024, i))) / 10.0D + " " + c + "B";
  }
  
  public void setFileName(String paramString)
  {
    if (paramString.endsWith(".apk")) {
      paramString = paramString.substring(0, -4 + paramString.length());
    }
    if (paramString.endsWith(".apk_")) {
      paramString = paramString.substring(0, -5 + paramString.length());
    }
    int i = paramString.lastIndexOf('/');
    if (i == -1) {
      i = 0;
    }
    this.fileName = paramString.substring(i, paramString.length());
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    Bitmap localBitmap = ((BitmapDrawable)paramDrawable).getBitmap();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    this.icon = localByteArrayOutputStream.toByteArray();
  }
  
  public void setLabel(String paramString)
  {
    this.label = paramString;
  }
  
  public void setPackageName(String paramString)
  {
    this.packageName = paramString;
  }
  
  public void setSize(long paramLong)
  {
    this.size = paramLong;
  }
}

