package com.mersillc.ads.sdk.format;

import static com.mersillc.ads.sdk.util.Constant.ADMOB;
import static com.mersillc.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.mersillc.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAdEventCallback;
import com.mersillc.ads.sdk.util.OnRewardedAdCompleteListener;
import com.mersillc.ads.sdk.util.OnRewardedAdDismissedListener;
import com.mersillc.ads.sdk.util.OnRewardedAdErrorListener;

public class RewardedAd {

    @SuppressWarnings("deprecation")
    public static class Builder {

        private static final String TAG = "SoloRewarded";
        private final Activity activity;
        private com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd adMobRewardedAd;
        private com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd adManagerRewardedAd;
        private String adStatus = "";
        private String mainAds = "";
        private String backupAds = "";
        private String adMobRewardedId = "";
        private String adManagerRewardedId = "";
        private String fanRewardedId = "";
        private String unityRewardedId = "";
        private String applovinMaxRewardedId = "";
        private String applovinDiscRewardedZoneId = "";
        private String ironSourceRewardedId = "";
        private String wortiseRewardedId = "";
        private String alienAdsRewardedId = "";
        private int placementStatus = 1;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
            loadRewardedAd(onComplete, onDismiss);
            return this;
        }

        public Builder show(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            showRewardedAd(onComplete, onDismiss, onError);
            return this;
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setMainAds(String mainAds) {
            this.mainAds = mainAds;
            return this;
        }

        public Builder setBackupAds(String backupAds) {
            this.backupAds = backupAds;
            return this;
        }

        public Builder setAdMobRewardedId(String adMobRewardedId) {
            this.adMobRewardedId = adMobRewardedId;
            return this;
        }

        public Builder setAdManagerRewardedId(String adManagerRewardedId) {
            this.adManagerRewardedId = adManagerRewardedId;
            return this;
        }

        public Builder setFanRewardedId(String fanRewardedId) {
            this.fanRewardedId = fanRewardedId;
            return this;
        }

        public Builder setUnityRewardedId(String unityRewardedId) {
            this.unityRewardedId = unityRewardedId;
            return this;
        }

        public Builder setApplovinMaxRewardedId(String applovinMaxRewardedId) {
            this.applovinMaxRewardedId = applovinMaxRewardedId;
            return this;
        }

        public Builder setApplovinDiscRewardedZoneId(String applovinDiscRewardedZoneId) {
            this.applovinDiscRewardedZoneId = applovinDiscRewardedZoneId;
            return this;
        }

        public Builder setIronSourceRewardedId(String ironSourceRewardedId) {
            this.ironSourceRewardedId = ironSourceRewardedId;
            return this;
        }

        public Builder setWortiseRewardedId(String wortiseRewardedId) {
            this.wortiseRewardedId = wortiseRewardedId;
            return this;
        }

        public Builder setAlienAdsRewardedId(String alienAdsRewardedId) {
            this.alienAdsRewardedId = alienAdsRewardedId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
            if (!com.mersillc.ads.sdk.format.AdNetwork.isAdMobInitialized) {
                com.mersillc.ads.sdk.format.AdNetwork.waitForAdMobInitialization(() -> loadRewardedAd(onComplete, onDismiss));
                return;
            }
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (mainAds) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd.load(
                                new AdRequest.Builder(adMobRewardedId).build(),
                                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd>() {
                                    @Override
                                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd ad) {
                                        adMobRewardedAd = ad;
                                        adMobRewardedAd.setAdEventCallback(new RewardedAdEventCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                adMobRewardedAd = null;
                                                loadRewardedAd(onComplete, onDismiss);
                                                onDismiss.onRewardedAdDismissed();
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                                adMobRewardedAd = null;
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                            }

                                            @Override
                                            public void onAdImpression() {
                                            }

                                            @Override
                                            public void onAdClicked() {
                                            }
                                        });
                                        Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        Log.d(TAG, loadAdError.toString());
                                        adMobRewardedAd = null;
                                        loadRewardedBackupAd(onComplete, onDismiss);
                                        Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                                    }
                                });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd.load(
                                new AdRequest.Builder(adManagerRewardedId).build(),
                                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd>() {
                                    @Override
                                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd ad) {
                                        adManagerRewardedAd = ad;
                                        adManagerRewardedAd.setAdEventCallback(new RewardedAdEventCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                adManagerRewardedAd = null;
                                                loadRewardedAd(onComplete, onDismiss);
                                                onDismiss.onRewardedAdDismissed();
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                                adManagerRewardedAd = null;
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                            }

                                            @Override
                                            public void onAdImpression() {
                                            }

                                            @Override
                                            public void onAdClicked() {
                                            }
                                        });
                                        Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        Log.d(TAG, loadAdError.toString());
                                        adManagerRewardedAd = null;
                                        loadRewardedBackupAd(onComplete, onDismiss);
                                        Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                                    }
                                });
                        break;

                    default:
                        break;
                }
            }
        }

        public void loadRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
            if (!com.mersillc.ads.sdk.format.AdNetwork.isAdMobInitialized) {
                com.mersillc.ads.sdk.format.AdNetwork.waitForAdMobInitialization(() -> loadRewardedBackupAd(onComplete, onDismiss));
                return;
            }
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAds) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd.load(
                                new AdRequest.Builder(adMobRewardedId).build(),
                                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd>() {
                                    @Override
                                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd ad) {
                                        adMobRewardedAd = ad;
                                        adMobRewardedAd.setAdEventCallback(new RewardedAdEventCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                adMobRewardedAd = null;
                                                loadRewardedAd(onComplete, onDismiss);
                                                onDismiss.onRewardedAdDismissed();
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                                adMobRewardedAd = null;
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                            }

                                            @Override
                                            public void onAdImpression() {
                                            }

                                            @Override
                                            public void onAdClicked() {
                                            }
                                        });
                                        Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad loaded");
                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        Log.d(TAG, loadAdError.toString());
                                        adMobRewardedAd = null;
                                        Log.d(TAG, "[" + backupAds + "] [backup] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                                    }
                                });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd.load(
                                new AdRequest.Builder(adManagerRewardedId).build(),
                                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd>() {
                                    @Override
                                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd ad) {
                                        adManagerRewardedAd = ad;
                                        adManagerRewardedAd.setAdEventCallback(new RewardedAdEventCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                adManagerRewardedAd = null;
                                                loadRewardedAd(onComplete, onDismiss);
                                                onDismiss.onRewardedAdDismissed();
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                                                adManagerRewardedAd = null;
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                            }

                                            @Override
                                            public void onAdImpression() {
                                            }

                                            @Override
                                            public void onAdClicked() {
                                            }
                                        });
                                        Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad loaded");
                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        Log.d(TAG, loadAdError.toString());
                                        adManagerRewardedAd = null;
                                        Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                                    }
                                });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (mainAds) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (adMobRewardedAd != null) {
                            adMobRewardedAd.show(activity, rewardItem -> {
                                activity.runOnUiThread(onComplete::onRewardedAdComplete);
                                Log.d(TAG, "The user earned the reward.");
                            });
                        } else {
                            showRewardedBackupAd(onComplete, onDismiss, onError);
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerRewardedAd != null) {
                            adManagerRewardedAd.show(activity, rewardItem -> {
                                activity.runOnUiThread(onComplete::onRewardedAdComplete);
                                Log.d(TAG, "The user earned the reward.");
                            });
                        } else {
                            showRewardedBackupAd(onComplete, onDismiss, onError);
                        }
                        break;

                    default:
                        activity.runOnUiThread(onError::onRewardedAdError);
                        break;
                }
            }

        }

        public void showRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAds) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (adMobRewardedAd != null) {
                            adMobRewardedAd.show(activity, rewardItem -> {
                                activity.runOnUiThread(onComplete::onRewardedAdComplete);
                                Log.d(TAG, "The user earned the reward.");
                            });
                        } else {
                            activity.runOnUiThread(onError::onRewardedAdError);
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerRewardedAd != null) {
                            adManagerRewardedAd.show(activity, rewardItem -> {
                                activity.runOnUiThread(onComplete::onRewardedAdComplete);
                                Log.d(TAG, "The user earned the reward.");
                            });
                        } else {
                            activity.runOnUiThread(onError::onRewardedAdError);
                        }
                        break;

                    default:
                        activity.runOnUiThread(onError::onRewardedAdError);
                        break;
                }
            }

        }

        public void destroyRewardedAd() {
            if (adMobRewardedAd != null) {
                adMobRewardedAd = null;
            }
            if (adManagerRewardedAd != null) {
                adManagerRewardedAd = null;
            }
        }

    }

}
