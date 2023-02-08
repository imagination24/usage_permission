package com.laizhenghuo.usage_permission;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** UsagePermissionPlugin */
public class UsagePermissionPlugin implements FlutterPlugin, MethodCallHandler , ActivityAware {
  private MethodChannel channel;
  private Context context;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "usage_permission");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + Build.VERSION.RELEASE);
    } else if(call.method.equals("isUsagePermission")){
      result.success(isUsagePermission());
    } else if(call.method.equals("grantUsagePermission")){
      grantUsagePermission();
    } else if(call.method.equals("isReadCallsPermission")){
      result.success(isReadCallsPermission());
    }else if(call.method.equals("isReadSMSPermission")){
      result.success(isReadSMSPermission());
    }else if(call.method.equals("grantReadCallsPermission")){
      grantReadCallsPermission();
    }else if(call.method.equals("grantReadSMSPermission")){
      grantReadSMSPermission();
    }else if(call.method.equals("grantBackgroundStartup")){
      grantBackgroundStartup();
    }else if (call.method.equals("isBackgroundStartupAllowed")){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        result.success(isBackgroundStartupAllowed());
      }
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private boolean isUsagePermission(){
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
      int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,Process.myUid(), context.getPackageName());
      if(mode == AppOpsManager.MODE_ALLOWED){
        return true;
      }
    }
    return false;
  }

  private void grantUsagePermission(){
    if(!isUsagePermission()){
      Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    }
  }

  private boolean isReadCallsPermission(){
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED;
  }

  private boolean isReadSMSPermission(){
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
  }

  private void grantReadCallsPermission(){
    if(!isReadCallsPermission()){
      ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_CALL_LOG},100);
    }
  }

  private void grantReadSMSPermission(){
    if(!isReadSMSPermission()){
      ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_SMS},101);
    }
  }
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private boolean isBackgroundStartupAllowed(){
    if (checkManufacturer("Xiaomi")||checkManufacturer("xiaomi")){
      return isMiBackgroundStartupAllowed();
    }else if (checkManufacturer("vivo")){
      return  isVivoBackgroundStartup();
    }else if (checkManufacturer("oppo")){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return Settings.canDrawOverlays(context);
      }
    }
    return true;
  }
  @RequiresApi(api = Build.VERSION_CODES.DONUT)
  private boolean checkManufacturer(String manufacturer){
    return manufacturer.equals(Build.MANUFACTURER);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private boolean isMiBackgroundStartupAllowed() {
    AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
    try {
      int op = 10021;
      Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
      Integer result = (Integer) method.invoke(ops, op, Process.myUid(), context.getPackageName());
      return result == AppOpsManager.MODE_ALLOWED;
    } catch (Exception e) {
      Log.e("TAG", "not support");
    }
    return false;
  }
  @RequiresApi(api = Build.VERSION_CODES.DONUT)
  private void grantBackgroundStartup(){
    if (checkManufacturer("Xiaomi")||checkManufacturer("xiaomi")){
      grantMiBackgroundStartup();
    }else if (checkManufacturer("vivo")){

    }else if (checkManufacturer("oppo")){

    }
  }
  private void grantMiBackgroundStartup(){
    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    localIntent.putExtra("extra_pkgname", context.getPackageName());
    context.startActivity(localIntent);
  }


  public boolean isVivoBackgroundStartup() {
    String packageName = context.getPackageName();
    Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
    String selection = "pkgname = ?";
    String[] selectionArgs = new String[]{packageName};
    try {
      Cursor cursor = context
              .getContentResolver()
              .query(uri2, null, selection, selectionArgs, null);
      if (cursor != null) {
        if (cursor.moveToFirst()) {
          int currentMode = cursor.getInt(cursor.getColumnIndex("currentstate"));
          cursor.close();
          return currentMode==0;
        } else {
          cursor.close();
          return false;
        }
      }
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return false;
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }
}
