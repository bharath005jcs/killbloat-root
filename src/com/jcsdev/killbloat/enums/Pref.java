package com.jcsdev.killbloat.enums;

public enum Pref
{
  BLACKLIST("BLacklist", ""),  DELETE("Delete", true),  BACKUP("Backup", true);
  
  private DataType dataType;
  private boolean defaultBoolean = false;
  private int defaultInt = 0;
  private String defaultString = "";
  private boolean export = true;
  private String key;
  
  private Pref(String paramString, int paramInt)
  {
    this(paramString, paramInt, true);
  }
  
  private Pref(String paramString, int paramInt, boolean paramBoolean)
  {
    this.key = paramString;
    this.dataType = DataType.INT;
    this.export = paramBoolean;
    this.defaultInt = paramInt;
  }
  
  private Pref(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, true);
  }
  
  private Pref(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.key = paramString1;
    this.dataType = DataType.STRING;
    this.export = paramBoolean;
    this.defaultString = paramString2;
  }
  
  private Pref(String paramString, boolean paramBoolean)
  {
    this(paramString, paramBoolean, true);
  }
  
  private Pref(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.key = paramString;
    this.dataType = DataType.BOOLEAN;
    this.export = paramBoolean2;
    this.defaultBoolean = paramBoolean1;
  }
  
  public static Pref findKey(String paramString)
  {
    Pref[] arrayOfPref = values();
    int i = arrayOfPref.length;
    for (int j = 0;; j++)
    {
      Pref localPref;
      if (j >= i) {
        localPref = null;
      }
      do
      {
        return localPref;
        localPref = arrayOfPref[j];
      } while (localPref.getKey().equals(paramString));
    }
  }
  
  public static Pref findName(String paramString)
  {
    Pref[] arrayOfPref = values();
    int i = arrayOfPref.length;
    for (int j = 0;; j++)
    {
      Pref localPref;
      if (j >= i) {
        localPref = null;
      }
      do
      {
        return localPref;
        localPref = arrayOfPref[j];
      } while (localPref.name().equals(paramString));
    }
  }
  
  public static Pref findPosition(int paramInt)
  {
    return values()[paramInt];
  }
  
  public DataType getDataType()
  {
    return this.dataType;
  }
  
  public boolean getDefaultBoolean()
  {
    return this.defaultBoolean;
  }
  
  public int getDefaultInt()
  {
    return this.defaultInt;
  }
  
  public String getDefaultString()
  {
    return this.defaultString;
  }
  
  public boolean getExport()
  {
    return this.export;
  }
  
  public String getKey()
  {
    return this.key;
  }
}
