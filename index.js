
import { NativeModules } from 'react-native'

const { RNTUmengAnalytics } = NativeModules

export default {

  signIn(userId, provider) {
    RNTUmengAnalytics.signIn(userId, provider)
  },

  signOut() {
    return RNTUmengAnalytics.signOut()
  },

  enterPage(pageName) {
    return RNTUmengAnalytics.enterPage(pageName)
  },

  leavePage(pageName) {
    return RNTUmengAnalytics.leavePage(pageName)
  },

  sendEvent(eventId) {
    return RNTUmengAnalytics.sendEvent(eventId)
  },

  sendEventLabel(eventId, label) {
    return RNTUmengAnalytics.sendEventLabel(eventId, label)
  },

  sendEventData(eventId, data) {
    return RNTUmengAnalytics.sendEventData(eventId, data)
  },

  sendEventCounter(eventId, data, counter) {
    return RNTUmengAnalytics.sendEventCounter(eventId, data, counter)
  },

}
