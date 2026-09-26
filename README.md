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

AdNetwork.Initialize adNetwork = new AdNetwork.Initialize(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobAppId("ca-app-pub-3940256099942544~3347511713")
        .setDebug(false)
        .build();
```

### 2. Banner Ad
```java
import com.mersillc.ads.sdk.format.BannerAd;

BannerAd.Builder bannerAd = new BannerAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobBannerId("ca-app-pub-3940256099942544/6300978111")
        .build(false);
```
*Note: Make sure to include a placeholder for the banner ad in your XML layout (e.g., `<LinearLayout android:id="@+id/banner_ad_view" ... />`) and attach it.*

### 3. Interstitial Ad
```java
import com.mersillc.ads.sdk.format.InterstitialAd;

InterstitialAd.Builder interstitialAd = new InterstitialAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobInterstitialId("ca-app-pub-3940256099942544/1033173712")
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
        .setMainAds("admob")
        .setBackupAds("none")
        .setAdMobRewardedId("ca-app-pub-3940256099942544/5224354917")
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
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobNativeId("ca-app-pub-3940256099942544/2247696110")
        .setNativeAdStyle("default") // available styles: default, news, radio, video_small, video_large
        .build();
```
