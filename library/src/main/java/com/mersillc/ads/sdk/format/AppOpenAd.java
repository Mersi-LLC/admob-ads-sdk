package com.mersillc.ads.sdk.format;

import static com.mersillc.ads.sdk.util.Constant.ADMOB;
import static com.mersillc.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.mersillc.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdEventCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.mersillc.ads.sdk.util.OnShowAdCompleteListener;

@SuppressLint("StaticFieldLeak")
public class AppOpenAd {
    public static com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd appOpenAd = null;
    public static boolean isAppOpenAdLoaded = false;

    public static class Builder {

        private static final String TAG = "com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd";
        private final Activity activity;
        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobAppOpenId = "";
        private String adManagerAppOpenId = "";
        private String applovinAppOpenId = "";
        private String wortiseAppOpenId = "";

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadAppOpenAd();
            return this;
        }

        public Builder build(OnShowAdCompleteListener onShowAdCompleteListener) {
            loadAppOpenAd(onShowAdCompleteListener);
            return this;
        }

        public Builder show() {
            showAppOpenAd();
            return this;
        }

        public Builder show(OnShowAdCompleteListener onShowAdCompleteListener) {
            showAppOpenAd(onShowAdCompleteListener);
            return this;
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Builder setAdMobAppOpenId(String adMobAppOpenId) {
            this.adMobAppOpenId = adMobAppOpenId;
            return this;
        }

        public Builder setAdManagerAppOpenId(String adManagerAppOpenId) {
            this.adManagerAppOpenId = adManagerAppOpenId;
            return this;
        }

        public Builder setApplovinAppOpenId(String applovinAppOpenId) {
            this.applovinAppOpenId = applovinAppOpenId;
            return this;
        }

        public Builder setWortiseAppOpenId(String wortiseAppOpenId) {
            this.wortiseAppOpenId = wortiseAppOpenId;
            return this;
        }

        public void destroyOpenAd() {
            isAppOpenAdLoaded = false;
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (appOpenAd != null) {
                            appOpenAd = null;
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        //main ads
        public void loadAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdRequest adRequest = new AdRequest.Builder(adNetwork.equals(ADMOB) || adNetwork.equals(FAN_BIDDING_ADMOB) ? adMobAppOpenId : adManagerAppOpenId).build();
                        com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd.load(adRequest, new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd>() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd ad) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = ad;
                                    showAppOpenAd(onShowAdCompleteListener);
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on start] app open ad loaded");
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    loadBackupAppOpenAd(onShowAdCompleteListener);
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on start] failed to load app open ad: " + loadAdError.getMessage());
                                });
                            }
                        });
                        break;

                    default:
                        onShowAdCompleteListener.onShowAdComplete();
                        break;
                }
            } else {
                onShowAdCompleteListener.onShowAdComplete();
            }
        }

        public void showAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (appOpenAd != null) {
                        appOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    onShowAdCompleteListener.onShowAdComplete();
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on start] close app open ad");
                                });
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    onShowAdCompleteListener.onShowAdComplete();
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on start] failed to show app open ad: " + adError.getMessage());
                                });
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "[" + adNetwork + "] " + "[on start] show app open ad");
                            }

                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdImpression() {
                            }
                        });
                        appOpenAd.show(activity);
                    } else {
                        onShowAdCompleteListener.onShowAdComplete();
                    }
                    break;

                default:
                    onShowAdCompleteListener.onShowAdComplete();
                    break;
            }
        }

        public void loadAppOpenAd() {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdRequest adRequest = new AdRequest.Builder(adNetwork.equals(ADMOB) || adNetwork.equals(FAN_BIDDING_ADMOB) ? adMobAppOpenId : adManagerAppOpenId).build();
                        com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd.load(adRequest, new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd>() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd ad) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = ad;
                                    isAppOpenAdLoaded = true;
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on resume] app open ad loaded");
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    isAppOpenAdLoaded = false;
                                    loadBackupAppOpenAd();
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on resume] failed to load app open ad : " + loadAdError.getMessage());
                                });
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showAppOpenAd() {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (appOpenAd != null) {
                        appOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    loadAppOpenAd();
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on resume] close app open ad");
                                });
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    loadAppOpenAd();
                                    Log.d(TAG, "[" + adNetwork + "] " + "[on resume] failed to show app open ad: " + adError.getMessage());
                                });
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "[" + adNetwork + "] " + "[on resume] show app open ad");
                            }

                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdImpression() {
                            }
                        });
                        appOpenAd.show(activity);
                    } else {
                        showBackupAppOpenAd();
                    }
                    break;

                default:
                    break;
            }
        }

        //backup ads
        public void loadBackupAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdRequest adRequest = new AdRequest.Builder(backupAdNetwork.equals(ADMOB) || backupAdNetwork.equals(FAN_BIDDING_ADMOB) ? adMobAppOpenId : adManagerAppOpenId).build();
                        com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd.load(adRequest, new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd>() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd ad) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = ad;
                                    showBackupAppOpenAd(onShowAdCompleteListener);
                                    Log.d(TAG, "[" + backupAdNetwork + "] " + "[on start] [backup] app open ad loaded");
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                activity.runOnUiThread(() -> {
                                    appOpenAd = null;
                                    showBackupAppOpenAd(onShowAdCompleteListener);
                                    Log.d(TAG, "[" + backupAdNetwork + "] " + "[on start] [backup] failed to load app open ad: " + loadAdError.getMessage());
                                });
                            }
                        });
                        break;

                    default:
                        onShowAdCompleteListener.onShowAdComplete();
                        break;
                }
            } else {
                onShowAdCompleteListener.onShowAdComplete();
            }
        }

        public void showBackupAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (appOpenAd != null) {
                        appOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAd = null;
                                onShowAdCompleteListener.onShowAdComplete();
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on start] [backup] close app open ad");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                appOpenAd = null;
                                onShowAdCompleteListener.onShowAdComplete();
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on start] [backup] failed to show app open ad: " + adError.getMessage());
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on start] [backup] show app open ad");
                            }

                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdImpression() {
                            }
                        });
                        appOpenAd.show(activity);
                    } else {
                        onShowAdCompleteListener.onShowAdComplete();
                    }
                    break;

                default:
                    onShowAdCompleteListener.onShowAdComplete();
                    break;
            }
        }

        public void loadBackupAppOpenAd() {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdRequest adRequest = new AdRequest.Builder(backupAdNetwork.equals(ADMOB) || backupAdNetwork.equals(FAN_BIDDING_ADMOB) ? adMobAppOpenId : adManagerAppOpenId).build();
                        com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd.load(adRequest, new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd>() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd ad) {
                                appOpenAd = ad;
                                isAppOpenAdLoaded = true;
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on resume] [backup] app open ad loaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                appOpenAd = null;
                                isAppOpenAdLoaded = false;
                                loadBackupAppOpenAd();
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on resume] [backup] failed to load app open ad : " + loadAdError.getMessage());
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showBackupAppOpenAd() {
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (appOpenAd != null) {
                        appOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAd = null;
                                loadBackupAppOpenAd();
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on resume] [backup] close app open ad");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                appOpenAd = null;
                                loadBackupAppOpenAd();
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on resume] [backup] failed to show app open ad: " + adError.getMessage());
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "[" + backupAdNetwork + "] " + "[on resume] [backup] show app open ad");
                            }

                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdImpression() {
                            }
                        });
                        appOpenAd.show(activity);
                    }
                    break;

                default:
                    break;
            }
        }

    }

}
