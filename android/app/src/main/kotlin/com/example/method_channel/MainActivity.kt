package com.example.method_channel
import android.util.Log
import ButtonEvent
import PigeonEventSink
import StreamEventsStreamHandler
import android.view.KeyEvent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "button_simulator"


    private var eventSink: PigeonEventSink<ButtonEvent>? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            Log.d("TAG", "executando metodo")
            when (call.method) {

                "simulateButton" -> {
                    val button = call.argument<String>("button")
                    when (button) {
                        "volume_up" -> {
                            Log.d("TAG", "executando volume up")
                            val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP)
                            dispatchKeyEvent(event)
                            result.success(null)
                        }

                        "volume_down" -> {
                            Log.d("TAG", "executando metodo volume down")
                            val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN)
                            dispatchKeyEvent(event)
                            result.success(null)
                        }

                        else -> {
                            result.notImplemented()
                        }
                    }
                }

                else -> {
                    result.notImplemented()
                }
            }
        }



        super.configureFlutterEngine(flutterEngine)

        StreamEventsStreamHandler.register(
                flutterEngine.dartExecutor.binaryMessenger,
                object : StreamEventsStreamHandler() {
                    override fun onListen(p0: Any?, sink: PigeonEventSink<ButtonEvent>) {
                        eventSink = sink
                    }

                    override fun onCancel(p0: Any?) {
                        eventSink = null
                    }
                }
        )
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }
    // Sobrescrever o método onKeyDown para capturar eventos de botões físicos
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Capturar o evento do botão e enviar para o Flutter
        val buttonName = when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> "VOLUME_UP"
            KeyEvent.KEYCODE_VOLUME_DOWN -> "VOLUME_DOWN"
            KeyEvent.KEYCODE_POWER -> "POWER"
            KeyEvent.KEYCODE_CAMERA -> "CAMERA"
            /// Adicione outros botões físicos conforme necessário
            else -> "UNKNOWN_${keyCode}"
        }

        eventSink?.let { sink ->
            val buttonEvent = ButtonEvent(
                    buttonName = buttonName,
                    timestamp = System.currentTimeMillis()
            )
            sink.success(buttonEvent)
        }

        // Retorna false para permitir que o sistema também processe o evento
        // ou true para consumir o evento aqui
        return super.onKeyDown(keyCode, event)
    }
}