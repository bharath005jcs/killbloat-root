package com.jcsdev.killbloat;

import android.content.Context;
import android.widget.Toast;

public class Toaster
{
  private static Toast toast;
  
  public static void showToast(Context paramContext, int paramInt)
  {
    showToast(paramContext, paramContext.getString(paramInt));
  }
  
  public static void showToast(Context paramContext, CharSequence paramCharSequence)
  {
    int i;
    if (paramCharSequence.length() < 40)
    {
      i = 0;
      if (toast != null) {
        break label51;
      }
      toast = new Toast(paramContext);
      toast = Toast.makeText(paramContext, paramCharSequence, i);
    }
    for (;;)
    {
      toast.show();
      return;
      i = 1;
      break;
      label51:
      toast.cancel();
      toast.setText(paramCharSequence);
      toast.setDuration(i);
    }
  }
}
