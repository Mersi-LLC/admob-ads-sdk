# AdMob Ads SDK

> **Note:** This SDK is a fork. The original source code belongs to [solodroid-dev](https://github.com/solodroid-dev).

A library for displaying AdMob and Ad Manager ads in Android applications.

## Supported Ad Networks
* AdMob
* Ad Manager

## Installation

Add the dependency to your app's `build.gradle`:

```groovy
dependencies {
    // Ad Network SDK
    implementation 'com.github.Mersi-LLC:admob-ads-sdk:2.+'
}
```

## Usage

### 1. Initialize Ad Network
Initialize the SDK in your `Activity` or `Application` class:

```java
import com.mersillc.ads.sdk.format.AdNetwork;

// Example for AdMob
AdNetwork.Initialize adNetwork = new AdNetwork.Initialize(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobAppId("ca-app-pub-3940256099942544~3347511713")
        .setDebug(false)
        .build();
        
// Example for Ad Manager
AdNetwork.Initialize adNetworkManager = new AdNetwork.Initialize(this)
        .setAdStatus("1")
        .setAdNetwork("google_ad_manager")
        .setBackupAdNetwork("none")
        .setAdMobAppId("ca-app-pub-3940256099942544~3347511713") // Use your AdMob App ID here as well
        .setDebug(false)
        .build();
```

### 2. Banner Ad
```java
import com.mersillc.ads.sdk.format.BannerAd;

BannerAd.Builder bannerAd = new BannerAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAdNetwork("none")
        .setAdMobBannerId("ca-app-pub-3940256099942544/6300978111") // For Ad Manager, use your ad unit format like "/6499/example/banner"
        .build(false);
```
*Note: Make sure to include a placeholder for the banner ad in your XML layout (e.g., `<LinearLayout android:id="@+id/banner_ad_view" ... />`) and attach it.*

### 3. Interstitial Ad
```java
import com.mersillc.ads.sdk.format.InterstitialAd;

InterstitialAd.Builder interstitialAd = new InterstitialAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAdNetwork("none")
        .setAdMobInterstitialId("ca-app-pub-3940256099942544/1033173712") // For Ad Manager, use "/6499/example/interstitial"
        .setInterval(1)
        .build(() -> {
            // onAdDismissed callback
        });

// To show the ad:
interstitialAd.show(() -> {
    // onAdShowed callback
}, () -> {
    // onAdDismissed callback
});
```

### 4. Rewarded Ad
```java
import com.mersillc.ads.sdk.format.RewardedAd;
import com.mersillc.ads.sdk.util.OnRewardedAdCompleteListener;
import com.mersillc.ads.sdk.util.OnRewardedAdDismissedListener;

RewardedAd.Builder rewardedAd = new RewardedAd.Builder(this)
        .setAdStatus("1")
        .setMainAds("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAds("none")
        .setAdMobRewardedId("ca-app-pub-3940256099942544/5224354917") // For Ad Manager, use "/6499/example/rewarded"
        .build(new OnRewardedAdCompleteListener() {
            @Override
            public void onRewardedAdComplete() {
                // Handle reward
            }
        }, new OnRewardedAdDismissedListener() {
            @Override
            public void onRewardedAdDismissed() {
            }
        });

// To show the ad:
rewardedAd.show(onComplete, onDismiss, onError);
```

### 5. Native Ad
```java
import com.mersillc.ads.sdk.format.NativeAd;

NativeAd.Builder nativeAd = new NativeAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAdNetwork("none")
        .setAdMobNativeId("ca-app-pub-3940256099942544/2247696110") // For Ad Manager, use "/6499/example/native"
        .setNativeAdStyle("default") // available styles: default, news, radio, video_small, video_large
        .build();
```

### 6. App Open Ad
```java
import com.mersillc.ads.sdk.format.AppOpenAd;

AppOpenAd.Builder appOpenAd = new AppOpenAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAdNetwork("none")
        .setAdMobAppOpenId("ca-app-pub-3940256099942544/3419835294") // For Ad Manager, use "/6499/example/app-open"
        .build();

// To show the ad (e.g., in onStart() of your Activity):
if (AppOpenAd.isAppOpenAdLoaded) {
    appOpenAd.show();
}
```

### 7. Medium Rectangle Ad
```java
import com.mersillc.ads.sdk.format.MediumRectangleAd;

MediumRectangleAd.Builder mediumRectangleAd = new MediumRectangleAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob") // Use "google_ad_manager" for Ad Manager
        .setBackupAdNetwork("none")
        .setAdMobBannerId("ca-app-pub-3940256099942544/6300978111")
        .setPlacementStatus(1)
        .build();
```
*Note: Make sure to include a placeholder for the medium rectangle ad in your XML layout and attach it.*
