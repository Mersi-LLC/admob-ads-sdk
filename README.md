# admob-ads-sdk
> **Note:** This SDK is a fork. The original source code belongs to [solodroid-dev](https://github.com/solodroid-dev).

<p>A library for displaying AdMob ads</p>
<p>Ads Sdk list:</p>
  <ul>
    <li>AdMob</li>
    <li>Ad Manager</li>
  </ul>

```gradle
dependencies {
    //Ad Network Sdk, see the documentation for other Ad Network Sdk options
    implementation 'com.github.Mersi-LLC:admob-ads-sdk:2.+'
}
```

## Usage

### 1. Initialize Ad Network
Initialize the SDK in your `Activity` or `Application` class:
```java
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
BannerAd.Builder bannerAd = new BannerAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobBannerId("ca-app-pub-3940256099942544/6300978111")
        .build(false);
```
*Note: Make sure to include a placeholder for the banner ad in your XML layout (e.g. `<LinearLayout android:id="@+id/banner_ad_view" ... />`) and attach it.*

### 3. Interstitial Ad
```java
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
rewardedAd.show(...); // Pass listeners when showing
```

### 5. Native Ad
```java
NativeAd.Builder nativeAd = new NativeAd.Builder(this)
        .setAdStatus("1")
        .setAdNetwork("admob")
        .setBackupAdNetwork("none")
        .setAdMobNativeId("ca-app-pub-3940256099942544/2247696110")
        .setNativeAdStyle("default") // available styles: default, news, radio, video_small, video_large
        .build();
```
