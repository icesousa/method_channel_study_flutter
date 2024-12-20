// Autogenerated from Pigeon (v22.7.0), do not edit directly.
// See also: https://pub.dev/packages/pigeon
@file:Suppress("UNCHECKED_CAST", "ArrayInDataClass")


import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMethodCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

/** Generated class from Pigeon that represents data sent in messages. */
data class ButtonEvent (
  val buttonName: String? = null,
  val timestamp: Long? = null,
  val buttonKey: Long? = null
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): ButtonEvent {
      val buttonName = pigeonVar_list[0] as String?
      val timestamp = pigeonVar_list[1] as Long?
      val buttonKey = pigeonVar_list[2] as Long?
      return ButtonEvent(buttonName, timestamp, buttonKey)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      buttonName,
      timestamp,
      buttonKey,
    )
  }
}
private open class ButtonEventStreamPigeonCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      129.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          ButtonEvent.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is ButtonEvent -> {
        stream.write(129)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

val ButtonEventStreamPigeonMethodCodec = StandardMethodCodec(ButtonEventStreamPigeonCodec());


private class PigeonStreamHandler<T>(
    val wrapper: PigeonEventChannelWrapper<T>
) : EventChannel.StreamHandler {
  var pigeonSink: PigeonEventSink<T>? = null

  override fun onListen(p0: Any?, sink: EventChannel.EventSink) {
    pigeonSink = PigeonEventSink<T>(sink)
    wrapper.onListen(p0, pigeonSink!!)
  }

  override fun onCancel(p0: Any?) {
    pigeonSink = null
    wrapper.onCancel(p0)
  }
}

interface PigeonEventChannelWrapper<T> {
  open fun onListen(p0: Any?, sink: PigeonEventSink<T>) {}

  open fun onCancel(p0: Any?) {}
}

class PigeonEventSink<T>(private val sink: EventChannel.EventSink) {
  fun success(value: T) {
    sink.success(value)
  }

  fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
    sink.error(errorCode, errorMessage, errorDetails)
  }
  
  fun endOfStream() { 
    sink.endOfStream()
  }
}
      
abstract class StreamEventsStreamHandler : PigeonEventChannelWrapper<ButtonEvent> {
  companion object {
    fun register(messenger: BinaryMessenger, streamHandler: StreamEventsStreamHandler, instanceName: String = "") {
      var channelName: String = "dev.flutter.pigeon.button_event_stream_package.EventChannelMethods.streamEvents"
      if (instanceName.isNotEmpty()) {
        channelName += ".$instanceName"
      }
      val internalStreamHandler = PigeonStreamHandler<ButtonEvent>(streamHandler)
      EventChannel(messenger, channelName, ButtonEventStreamPigeonMethodCodec).setStreamHandler(internalStreamHandler)
    }
  }
}
      
