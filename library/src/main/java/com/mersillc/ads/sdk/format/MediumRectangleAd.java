package com.mersillc.ads.sdk.format;

import static com.mersillc.ads.sdk.util.Constant.ADMOB;
import static com.mersillc.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.mersillc.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.mersillc.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.banner.AdView;
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd;
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.mersillc.ads.sdk.R;
import com.mersillc.ads.sdk.util.Tools;

public class MediumRectangleAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;
        private AdView adView;
        private com.google.android.libraries.ads.mobile.sdk.banner.AdView adManagerAdView;
        FrameLayout ironSourceBannerView;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobBannerId = "";
        private String googleAdManagerBannerId = "";
        private String fanBannerId = "";
        private String unityBannerId = "";
        private String appLovinBannerId = "";
        private String appLovinBannerZoneId = "";
        private String mopubBannerId = "";
        private String ironSourceBannerId = "";
        private int placementStatus = 1;
        private boolean darkTheme = false;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadBannerAd();
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

        public Builder setAdMobBannerId(String adMobBannerId) {
            this.adMobBannerId = adMobBannerId;
            return this;
        }

        public Builder setGoogleAdManagerBannerId(String googleAdManagerBannerId) {
            this.googleAdManagerBannerId = googleAdManagerBannerId;
            return this;
        }

        public Builder setFanBannerId(String fanBannerId) {
            this.fanBannerId = fanBannerId;
            return this;
        }

        public Builder setUnityBannerId(String unityBannerId) {
            this.unityBannerId = unityBannerId;
            return this;
        }

        public Builder setAppLovinBannerId(String appLovinBannerId) {
            this.appLovinBannerId = appLovinBannerId;
            return this;
        }

        public Builder setAppLovinBannerZoneId(String appLovinBannerZoneId) {
            this.appLovinBannerZoneId = appLovinBannerZoneId;
            return this;
        }

        public Builder setMopubBannerId(String mopubBannerId) {
            this.mopubBannerId = mopubBannerId;
            return this;
        }

        public Builder setIronSourceBannerId(String ironSourceBannerId) {
            this.ironSourceBannerId = ironSourceBannerId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadBannerAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                        adContainerView.post(() -> {
                            adView = new AdView(activity);
                            adContainerView.removeAllViews();
                            adContainerView.addView(adView);
                            com.google.android.libraries.ads.mobile.sdk.banner.AdSize adSize = Tools.getAdSizeMREC();
                            BannerAdRequest bannerAdRequest = new BannerAdRequest.Builder(adMobBannerId, adSize).build();
                            adView.loadAd(bannerAdRequest, new AdLoadCallback<BannerAd>() {
                                @Override
                                public void onAdLoaded(@NonNull BannerAd bannerAd) {
                                    adContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                    adContainerView.setVisibility(View.GONE);
                                    loadBackupBannerAd();
                                }
                            });
                        });
                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                        googleAdContainerView.post(() -> {
                            adView = new AdView(activity);
                            googleAdContainerView.removeAllViews();
                            googleAdContainerView.addView(adView);
                            com.google.android.libraries.ads.mobile.sdk.banner.AdSize adSize = Tools.getAdSizeMREC();
                            BannerAdRequest bannerAdRequest = new BannerAdRequest.Builder(googleAdManagerBannerId, adSize).build();
                            adView.loadAd(bannerAdRequest, new AdLoadCallback<BannerAd>() {
                                @Override
                                public void onAdLoaded(@NonNull BannerAd bannerAd) {
                                    googleAdContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    googleAdContainerView.setVisibility(View.GONE);
                                    loadBackupBannerAd();
                                }
                            });
                        });
                        break;

                    default:
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void loadBackupBannerAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                        adContainerView.post(() -> {
                            adView = new AdView(activity);
                            adContainerView.removeAllViews();
                            adContainerView.addView(adView);
                            com.google.android.libraries.ads.mobile.sdk.banner.AdSize adSize = Tools.getAdSizeMREC();
                            BannerAdRequest bannerAdRequest = new BannerAdRequest.Builder(adMobBannerId, adSize).build();
                            adView.loadAd(bannerAdRequest, new AdLoadCallback<BannerAd>() {
                                @Override
                                public void onAdLoaded(@NonNull BannerAd bannerAd) {
                                    adContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                    adContainerView.setVisibility(View.GONE);
                                }
                            });
                        });
                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                        googleAdContainerView.post(() -> {
                            adView = new AdView(activity);
                            googleAdContainerView.removeAllViews();
                            googleAdContainerView.addView(adView);
                            com.google.android.libraries.ads.mobile.sdk.banner.AdSize adSize = Tools.getAdSizeMREC();
                            BannerAdRequest bannerAdRequest = new BannerAdRequest.Builder(googleAdManagerBannerId, adSize).build();
                            adView.loadAd(bannerAdRequest, new AdLoadCallback<BannerAd>() {
                                @Override
                                public void onAdLoaded(@NonNull BannerAd bannerAd) {
                                    googleAdContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    googleAdContainerView.setVisibility(View.GONE);
                                }
                            });
                        });
                        break;

                    default:
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void destroyAndDetachBanner() {
            if (adView != null) {
                adView.destroy();
                adView = null;
            }
            if (adManagerAdView != null) {
                adManagerAdView.destroy();
                adManagerAdView = null;
            }
        }

    }

}
