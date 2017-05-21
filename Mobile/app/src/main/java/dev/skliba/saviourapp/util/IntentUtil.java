package dev.skliba.saviourapp.util;


import android.content.Intent;
import android.net.Uri;

public class IntentUtil {

    private IntentUtil() {
        throw new UnsupportedOperationException("Cannot create instance of this class.");
    }

    public static Intent buildPhoneCallIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        return intent;
    }
}
