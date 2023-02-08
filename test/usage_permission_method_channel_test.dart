import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:usage_permission/usage_permission_method_channel.dart';

void main() {
  MethodChannelUsagePermission platform = MethodChannelUsagePermission();
  const MethodChannel channel = MethodChannel('usage_permission');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
