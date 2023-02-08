 import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'usage_permission_platform_interface.dart';

/// An implementation of [UsagePermissionPlatform] that uses method channels.
class MethodChannelUsagePermission extends UsagePermissionPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('usage_permission');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<bool> isUsagePermission() async{
    bool isUsagePermission = await methodChannel.invokeMethod("isUsagePermission");
    return isUsagePermission;
  }

  @override
  void grantUsagePermission()async {
    methodChannel.invokeMethod('grantUsagePermission');
  }

  @override
  Future<bool> isReadCallsPermission() async {
    bool isReadCallsPermission = await methodChannel.invokeMethod("isReadCallsPermission");
    return isReadCallsPermission;
  }

  @override
  void grantReadCallsPermission() async{
     methodChannel.invokeMethod('grantReadCallsPermission');
  }

  @override
  Future<bool> isReadSMSPermission() async {
    bool isReadSMSPermission = await methodChannel.invokeMethod("isReadSMSPermission");
    return isReadSMSPermission;
  }

  @override
  void grantReadSMSPermission() async{
     methodChannel.invokeMethod('grantReadSMSPermission');
  }

  @override
  Future<bool> isBackgroundStartupAllowed()async {
    bool isBackgroundStartupAllowed = await methodChannel.invokeMethod("isBackgroundStartupAllowed");
    return isBackgroundStartupAllowed;
  }

  @override
  void grantBackgroundStartup() {
    methodChannel.invokeMethod("grantBackgroundStartup");
  }
}
