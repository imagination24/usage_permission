# usage_permission

A  Flutter plugin  to check And request Permission
Requires API level 19 as a minimum!

## install
```
usage_states:
      git:
        url: https://github.com/imagination24/usage_permission.git
```
## Permission
Add the following permission to the manifest namespace in AndroidManifest.xml:
```
    <uses-permission android:name="android.permission.READ_CALL_LOG"/>
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission
        android:name="android.permission.PACKAGE_USAGE_STATS"
        tools:ignore="ProtectedPermissions" />
```
## Usage
```

//checkUsagePermission
bool result = await UsagePermission().isUsagePermission();

//grantUsagePermission
UsagePermission().grantUsagePermission();

//checkCallRecordPermission
bool result = await UsagePermission().isReadCallsPermission();

//grantCallRecordPermission
UsagePermission().grantReadCallsPermission();

//checkSMSRecordPermission
bool result = await UsagePermission().isReadSMSPermission();

//grantSMSRecordPermission
UsagePermission().grantReadSMSPermission();

//checkBackgroundStartupPermission
bool result = await UsagePermission().isBackgroundStartupAllowed();

//grantBackgroundStartup
UsagePermission().grantBackgroundStartup();
```

