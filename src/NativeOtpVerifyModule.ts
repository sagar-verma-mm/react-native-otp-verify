import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  getOtp(): Promise<boolean>;
  getHash(): Promise<string[]>;
  // addListener(handler: (value: string) => any): import("react-native").EmitterSubscription;
  // removeListener(): void;
}

export default TurboModuleRegistry.get<Spec>(
  'RTNOtpVerify'
) as Spec | null;