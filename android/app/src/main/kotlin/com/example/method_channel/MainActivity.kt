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
  super.configureFlutterEngine(flutterEngine)

        StreamEventsStreamHandler.register(
            flutterEngine.dartExecutor.binaryMessenger,
            object : StreamEventsStreamHandler() {
                override fun onListen(p0: Any?, sink: PigeonEventSink<ButtonEvent>) {
                    Log.d("TAG", "OUVINDDO BOTÕES")

                    eventSink = sink
                }
                override fun onCancel(p0: Any?) {
                    eventSink = null
                }

            }
        )
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

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        Log.d("TAG", "ONKEYDOWN TENTANDO DETECTAR BOTÃO APERTADO ${keyCode}")

        // Capturar o evento do botão e enviar para o Flutter
        val buttonName = when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> "VOLUME_UP"
            KeyEvent.KEYCODE_VOLUME_DOWN -> "VOLUME_DOWN"
            KeyEvent.KEYCODE_POWER -> "POWER"
            KeyEvent.KEYCODE_CAMERA -> "CAMERA"
            /// Adicione outros botões físicos conforme necessário
            else -> "UNKNOWN_${keyCode}"
        }

        val buttonEvent = ButtonEvent(
            buttonName = buttonName,
            timestamp = System.currentTimeMillis(),
            buttonKey = keyCode.toLong());


        // Add debug logging here
        Log.d("TAG", "Attempting to send event: $buttonName")
        eventSink?.let {
            Log.d("TAG", "EventSink is available, sending event")
            it.success(buttonEvent)
        } ?: Log.d("TAG", "EventSink is null!")



        // Retorna false para permitir que o sistema também processe o evento
        // ou true para consumir o evento aqui
        return super.onKeyDown(keyCode, event)
    }



}