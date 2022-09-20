
package com.rtnOtpVerify;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
// import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
// import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import java.util.ArrayList;
import com.facebook.react.bridge.ReactContext;

public class RNOtpVerifyModule extends ReactContextBaseJavaModule implements ReactModuleWithSpec, TurboModule {
    private static final String TAG = RNOtpVerifyModule.class.getSimpleName();
    // private final ReactApplicationContext reactContext;
    private BroadcastReceiver mReceiver;
    private boolean isReceiverRegistered = false;
    public static String NAME = "RNOtpVerify";

    private ReactContext reactContext;

    public RNOtpVerifyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mReceiver = new OtpBroadcastReceiver(reactContext);
        // getReactApplicationContext().addLifecycleEventListener(this);
        registerReceiverIfNecessary(mReceiver);
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getOtp(Promise promise) {
        requestOtp(promise);
    }

    @ReactMethod
    public void getHash(Promise promise) {
        try {
            AppSignatureHelper helper = new AppSignatureHelper(reactContext);
            ArrayList<String> signatures = helper.getAppSignatures();
            WritableArray arr = Arguments.createArray();
            for (String s : signatures) {
                arr.pushString(s);
            }
            promise.resolve(arr);
        } catch (Exception e) {
            promise.reject(e);
        }
    }


    private void registerReceiverIfNecessary(BroadcastReceiver receiver) {
        if (getCurrentActivity() == null) return;
        try {
            getCurrentActivity().registerReceiver(
                    receiver,
                    new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            );
            Log.d(TAG, "Receiver Registered");
            isReceiverRegistered = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestOtp(final Promise promise) {
        SmsRetrieverClient client = SmsRetriever.getClient(reactContext);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "started sms listener");
                promise.resolve(true);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Could not start sms listener", e);
                promise.reject(e);
            }
        });
    }

    private void unregisterReceiver(BroadcastReceiver receiver) {
        if (isReceiverRegistered && getCurrentActivity() != null && receiver != null) {
            try {
                getCurrentActivity().unregisterReceiver(receiver);
                Log.d(TAG, "Receiver UnRegistered");
                isReceiverRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ReactMethod
    public void onHostResume() {
        registerReceiverIfNecessary(mReceiver);
    }

    @ReactMethod
    public void onHostPause() {
        unregisterReceiver(mReceiver);
    }

    @ReactMethod
    public void onHostDestroy() {
        unregisterReceiver(mReceiver);
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

}

