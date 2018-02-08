package entity.webhook.facebook;

import java.util.Map;

/**
 * Created by vku131 on 2/8/18.
 */
public class Postback {
    private String payload;
    private Map<String,String> referral;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Map<String, String> getReferral() {
        return referral;
    }

    public void setReferral(Map<String, String> referral) {
        this.referral = referral;
    }
}
