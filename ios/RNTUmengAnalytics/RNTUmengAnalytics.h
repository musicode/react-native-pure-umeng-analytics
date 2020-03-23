#import <React/RCTBridgeModule.h>

@interface RNTUmengAnalytics : NSObject <RCTBridgeModule>

+ (void)init:(NSString *)appKey debug:(BOOL)debug;

+ (void)analytics;

@end
