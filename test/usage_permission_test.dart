import 'package:flutter_test/flutter_test.dart';
import 'package:usage_permission/usage_permission.dart';
import 'package:usage_permission/usage_permission_platform_interface.dart';
import 'package:usage_permission/usage_permission_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockUsagePermissionPlatform 
    with MockPlatformInterfaceMixin
    implements UsagePermissionPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');



  @override
  Future<bool> isUsagePermission() {
    // TODO: implement isUsagePermission
    throw UnimplementedError();
  }



  @override
  Future<bool> isReadCallsPermission() {
    // TODO: implement isReadCallsPermission
    throw UnimplementedError();
  }

  @override
  Future<bool> isReadSMSPermission() {
    // TODO: implement isReadSMSPermission
    throw UnimplementedError();
  }

  @override
  Future<bool> grantReadCallsPermission() {
    // TODO: implement grantReadCallsPermission
    throw UnimplementedError();
  }

  @override
  Future<bool> grantReadSMSPermission() {
    // TODO: implement grantReadSMSPermission
    throw UnimplementedError();
  }

  @override
  Future<bool> grantUsagePermission() {
    throw UnimplementedError();
  }

  @override
  void grantBackgroundStartup() {
  }

  @override
  Future<bool> isBackgroundStartupAllowed() {
    throw UnimplementedError();
  }
}

void main() {
  final UsagePermissionPlatform initialPlatform = UsagePermissionPlatform.instance;

  test('$MethodChannelUsagePermission is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelUsagePermission>());
  });

  test('getPlatformVersion', () async {
    UsagePermission usagePermissionPlugin = UsagePermission();
    MockUsagePermissionPlatform fakePlatform = MockUsagePermissionPlatform();
    UsagePermissionPlatform.instance = fakePlatform;
  
    expect(await usagePermissionPlugin.getPlatformVersion(), '42');
  });
}
