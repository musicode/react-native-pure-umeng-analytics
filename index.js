
import { NativeModules } from 'react-native'

const { RNTUmengAnalytics } = NativeModules

// enterPage 和 leavePage 必须对称调用
// 在这里做一层保证
let currentPage

export default {

  getDeviceInfo() {
    return RNTUmengAnalytics.getDeviceInfo()
  },

  signIn(userId, provider) {
    RNTUmengAnalytics.signIn(userId, provider)
  },

  signOut() {
    RNTUmengAnalytics.signOut()
  },

  enterPage(pageName) {
    if (!currentPage) {
      RNTUmengAnalytics.enterPage(pageName)
      currentPage = pageName
    }
  },

  leavePage(pageName) {
    if (currentPage === pageName) {
      RNTUmengAnalytics.leavePage(pageName)
      currentPage = undefined
    }
  },

  sendEvent(eventId) {
    RNTUmengAnalytics.sendEvent(eventId)
  },

  sendEventLabel(eventId, label) {
    RNTUmengAnalytics.sendEventLabel(eventId, label)
  },

  sendEventData(eventId, data) {
    RNTUmengAnalytics.sendEventData(eventId, data)
  },

  sendEventCounter(eventId, data, counter) {
    RNTUmengAnalytics.sendEventCounter(eventId, data, counter)
  },

}
