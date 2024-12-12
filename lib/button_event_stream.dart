import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/src/generated/button_event_stream.g.dart',
  dartOptions: DartOptions(),
  kotlinOut: 'android/app/src/main/kotlin/com/example/ButtonEventStream.kt',
  kotlinOptions: KotlinOptions(),
  swiftOut: 'ios/Runner/ButtonEventStream.swift',
  swiftOptions: SwiftOptions(),
  dartPackageName: 'button_event_stream_package',
))
@EventChannelApi()
abstract class EventChannelMethods {
  ButtonEvent streamEvents();
}

class ButtonEvent {
  String? buttonName;
  int? timestamp;
  int? buttonKey;
}
