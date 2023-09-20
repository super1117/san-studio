package com.pdk.re.specified.models;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;

import com.pdk.re.specified.R;

public class FireBaseModel {

    private boolean openApp;
    private boolean isIpAllowed;
    private String allowedSimCard;
    private String link2;


    public FireBaseModel(
            boolean openApp,
            boolean isIpAllowed,
            String allowedSimCard,
            String link2
    ) {
        this.openApp = openApp;
        this.isIpAllowed = isIpAllowed;
        this.allowedSimCard = allowedSimCard;
        this.link2 = link2;
    }

    public boolean isOpenApp() {
        return openApp;
    }

    public void setOpenApp(boolean openApp) {
        this.openApp = openApp;
    }

    public boolean isIpAllowed() {
        return isIpAllowed;
    }

    public void setIpAllowed(boolean ipAllowed) {
        isIpAllowed = ipAllowed;
    }

    public String getAllowedSimCard() {
        return allowedSimCard;
    }

    public void setAllowedSimCard(String allowedSimCard) {
        this.allowedSimCard = allowedSimCard;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public Pair<Integer, String> getAllow(Context context) {
        String defaultStr = new String(Base64.decode(context.getString(R.string.app_secret), Base64.DEFAULT));
        int flag = 0;
        String flagStr = defaultStr;
        if (isIpAllowed && openApp && !TextUtils.isEmpty(link2)) {
            flag = 1;
            flagStr = link2;
        }
        String phonySimIso = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso();
        if (!TextUtils.isEmpty(allowedSimCard) && !TextUtils.equals(phonySimIso, allowedSimCard)) {
            flag = 0;
            flagStr= defaultStr;
        }
        return new Pair<>(flag, flagStr);
    }
}
