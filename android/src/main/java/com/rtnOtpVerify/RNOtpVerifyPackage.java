
package com.rtnOtpVerify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
// import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.module.model.ReactModuleInfo;

import java.util.HashMap;
import java.util.Map;


public class RNOtpVerifyPackage extends TurboReactPackage {

    @Nullable
    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactContext) {
        if (name.equals(RNOtpVerifyModule.NAME)) {
           return new RNOtpVerifyModule(reactContext);
       } else {
           return null;
       }
     }
 
     @Override
     public ReactModuleInfoProvider getReactModuleInfoProvider() {
         return () -> {
             final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
             moduleInfos.put(
                 RNOtpVerifyModule.NAME, 
                 new ReactModuleInfo(
                     RNOtpVerifyModule.NAME,
                     RNOtpVerifyModule.NAME,
                     false, // canOverrideExistingModule
                     false, // needsEagerInit
                     true, // hasConstants
                     false, // isCxxModule
                     true // isTurboModule
                 )
             );
             return moduleInfos;
         };
     }
}