package com.jcsdev.killbloat;

import android.graphics.drawable.Drawable;

public class FullApp
  extends App
{
  Drawable fullIcon;
  
  public FullApp(String paramString1, String paramString2, String paramString3, long paramLong, Drawable paramDrawable)
  {
    setLabel(paramString1);
    setPackageName(paramString2);
    setFileName(paramString3);
    setSize(paramLong);
    setIcon(paramDrawable);
  }
  
  public Drawable getIcon()
  {
    return this.fullIcon;
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    this.fullIcon = paramDrawable;
  }
}

