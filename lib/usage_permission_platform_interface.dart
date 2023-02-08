import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'usage_permission_method_channel.dart';

abstract class UsagePermissionPlatform extends PlatformInterface {
  /// Constructs a UsagePermissionPlatform.
  UsagePermissionPlatform() : super(token: _token);

  static final Object _token = Object();

  static UsagePermissionPlatform _instance = MethodChannelUsagePermission();

  /// The default instance of [UsagePermissionPlatform] to use.
  ///
  /// Defaults to [MethodChannelUsagePermission].
  static UsagePermissionPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [UsagePermissionPlatform] when
  /// they register themselves.
  static set instance(UsagePermissionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> isUsagePermission() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void grantUsagePermission(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> isReadCallsPermission(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void grantReadCallsPermission(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> isReadSMSPermission(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void grantReadSMSPermission(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool> isBackgroundStartupAllowed(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void grantBackgroundStartup(){
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
