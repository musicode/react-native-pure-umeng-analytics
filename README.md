# react-native-pure-umeng-analytics

友盟统计

## Installation

```
npm i react-native-pure-umeng-analytics
// link below 0.60
react-native link react-native-pure-umeng-analytics
```

## Setup

### iOS

修改 `AppDelegate.m`，如下

```oc
#import <RNTUmengAnalytics.h>

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  ...
  [RNTUmengAnalytics init:@"appKey" debug:false];
  [RNTUmengAnalytics analytics];
  return YES;
}
```

### Android

`android/build.gradle` 添加友盟仓库.

```
allprojects {
    repositories {
        // add this line
        maven { url 'https://dl.bintray.com/umsdk/release' }
    }
}
```

`android/app/build.gradle` 根据不同的包填写不同的配置，如下：

```
buildTypes {
    debug {
        manifestPlaceholders = [
            UMENG_APP_KEY: '',
            UMENG_PUSH_SECRET: '',
            UMENG_CHANNEL: '',
        ]
    }
    release {
        manifestPlaceholders = [
            UMENG_APP_KEY: '',
            UMENG_PUSH_SECRET: '',
            UMENG_CHANNEL: '',
        ]
    }
}
```

在 `MainApplication` 的 `onCreate` 方法进行初始化，如下：

```kotlin
override fun onCreate() {
    val metaData = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData

    // 初始化友盟基础库
    // 第三个参数表示是否显示调试信息
    RNTUmengAnalyticsModule.init(this, metaData, false)
    // 初始化友盟统计
    RNTUmengAnalyticsModule.analytics(this)
}
```

### 配置混淆规则

在 `android/app/proguard-rules.pro` 添加以下混淆规则，注意替换自己的包名，并且删掉 `[` 和 `]`。

```
-keep public class [您的应用包名].R$*{
public static final int *;
}
```

## Usage

```js
import umengAnalytics from 'react-native-pure-umeng-analytics'

// 集成测试，获取设备信息
umengAnalytics.getDeviceInfo().then(data => {
  data.deviceId
  // android only
  data.mac
})

// 帐号统计
umengAnalytics.signIn('userId')
umengAnalytics.signIn('userId', 'provider')
umengAnalytics.signOut()

// 页面统计
umengAnalytics.enterPage('pageName')
umengAnalytics.leavePage('pageName')

// 自定义事件，eventId 需先在友盟后台注册之后才可以统计
umengAnalytics.sendEvent('eventId')
umengAnalytics.sendEventLabel('eventId', 'label')
umengAnalytics.sendEventData('eventId', { key1: 'value1', key2: 'value2' })
umengAnalytics.sendEventCounter('eventId', { key1: 'value1', key2: 'value2' }, 1)
```
