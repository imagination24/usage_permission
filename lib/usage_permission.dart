
import 'usage_permission_platform_interface.dart';

class UsagePermission {
  Future<String?> getPlatformVersion() {
    return UsagePermissionPlatform.instance.getPlatformVersion();
  }

  Future<bool> isUsagePermission(){
    return UsagePermissionPlatform.instance.isUsagePermission();
  }

  void grantUsagePermission(){
     UsagePermissionPlatform.instance.grantUsagePermission();
  }

  Future<bool> isReadCallsPermission(){
    return UsagePermissionPlatform.instance.isReadCallsPermission();
  }

  void grantReadCallsPermission(){
    UsagePermissionPlatform.instance.grantReadCallsPermission();
  }

  Future<bool> isReadSMSPermission(){
    return UsagePermissionPlatform.instance.isReadSMSPermission();
  }

  void grantReadSMSPermission(){
    UsagePermissionPlatform.instance.grantReadSMSPermission();
  }

  Future<bool> isBackgroundStartupAllowed(){
    return UsagePermissionPlatform.instance.isBackgroundStartupAllowed();
  }

  void grantBackgroundStartup(){
    UsagePermissionPlatform.instance.grantBackgroundStartup();
  }
}
