package com.github.musicode.umenganalytics

import android.app.Application
import android.os.Bundle
import com.facebook.react.bridge.*
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

class RNTUmengAnalyticsModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {

    companion object {

        // 初始化友盟基础库
        fun init(app: Application, metaData: Bundle, debug: Boolean) {

            val appKey = metaData.getString("UMENG_APP_KEY", "")
            val appSecret = metaData.getString("UMENG_APP_SECRET", "")
            val channel = metaData.getString("UMENG_CHANNEL", "")

            UMConfigure.setLogEnabled(debug)
            UMConfigure.init(app, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, appSecret)

        }

        // 初始化友盟统计
        fun analytics(app: Application, metaData: Bundle) {
            // 手动采集
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL)
        }

    }

    init {
        reactContext.addLifecycleEventListener(this)
    }

    override fun getName(): String {
        return "RNTUmengAnalytics"
    }

    @ReactMethod
    fun signIn(name: String, provider: String?) {
        val hasProvider = provider?.isNotEmpty() ?: false
        if (hasProvider) {
            MobclickAgent.onProfileSignIn(provider, name)
        }
        else {
            MobclickAgent.onProfileSignIn(name)
        }
    }

    @ReactMethod
    fun signOut() {
        MobclickAgent.onProfileSignOff()
    }

    @ReactMethod
    fun enterPage(pageName: String) {
        MobclickAgent.onPageStart(pageName)
    }

    @ReactMethod
    fun leavePage(pageName: String) {
        MobclickAgent.onPageEnd(pageName)
    }

    @ReactMethod
    fun sendEvent(eventId: String) {
        MobclickAgent.onEvent(reactContext, eventId)
    }

    @ReactMethod
    fun sendEventLabel(eventId: String, label: String) {
        MobclickAgent.onEvent(reactContext, eventId, label)
    }

    @ReactMethod
    fun sendEventData(eventId: String, data: ReadableMap) {
        MobclickAgent.onEventObject(reactContext, eventId, data.toHashMap())
    }

    @ReactMethod
    fun sendEventCounter(eventId: String, data: ReadableMap, counter: Int) {
        val map = HashMap<String, String>()
        for ((key,value) in data.toHashMap()){
            map[key] = value as String
        }
        MobclickAgent.onEventValue(reactContext, eventId, map, counter)
    }

    override fun onHostResume() {
        MobclickAgent.onResume(reactContext)
    }

    override fun onHostPause() {
        MobclickAgent.onPause(reactContext)
    }

    override fun onHostDestroy() {

    }

}