# react-native-pure-umeng-analytics

> 此库已废弃，请移步[新库](https://github.com/react-native-hero/umeng-analytics)

友盟统计

## Installation

```
npm i react-native-pure-umeng-analytics
// 0.60 版本以下手动执行这句
react-native link react-native-pure-umeng-analytics
```

## Setup

![image](https://user-images.githubusercontent.com/2732303/77606227-ded8b680-6f51-11ea-9aa4-0378e79deaa7.png)

打开应用信息页面，安卓推送有 `Appkey` 和 `Umeng Message Secret` 两个字段，iOS 只有 `Appkey` 字段，后面将用这些字段初始化友盟。

### iOS

修改 `AppDelegate.m`，如下

```oc
#import <RNTUmengAnalytics.h>

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  ...
  // 初始化友盟基础库
  // channel 一般填 App Store，如果有测试环境，可按需填写
  // debug 表示是否打印调试信息
  [RNTUmengAnalytics init:@"appKey" channel:@"App Store" debug:false];
  [RNTUmengAnalytics analytics];
  return YES;
}
```

### Android

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
// provider: 不能以下划线开头，使用大写字母和数字标识; 如果是上市公司，建议使用股票代码，比如 WB
umengAnalytics.signIn('userId', 'provider')
umengAnalytics.signOut()

// 页面统计，注意要配对调用
// 不能连续调用 enterPage，也不能连续调用 leavePage
umengAnalytics.enterPage('pageName')
umengAnalytics.leavePage('pageName')

// 自定义事件，eventId 需先在友盟后台注册之后才可以统计
umengAnalytics.sendEvent('eventId')
umengAnalytics.sendEventLabel('eventId', 'label')
umengAnalytics.sendEventData('eventId', { key1: 'value1', key2: 'value2' })
umengAnalytics.sendEventCounter('eventId', { key1: 'value1', key2: 'value2' }, 1)
```
