// import requireNativeComponent from "react-native/Libraries/ReactNative/requireNativeComponent";

// const isFabricEnabled = global.nativeFabricUIManager != null;

// const OtpVerify = isFabricEnabled
//   ? require("./RTNOtpVerifyNativeComponent").default
//   : requireNativeComponent("RTNOtpVerify");

const OtpVerify =  require("./NativeOtpVerifyModule").default

export default OtpVerify;
