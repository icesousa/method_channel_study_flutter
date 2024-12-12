import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:method_channel/buttonsimulator.dart';

import 'src/generated/button_event_stream.g.dart';

void main() {
    WidgetsFlutterBinding.ensureInitialized(); // Add this

    streamEvents().listen((event) {
      print(
          'Botão físico pressionado: ${event.buttonName} em ${event.timestamp}');
      // Trate o evento conforme necessário
    });
  

  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  static const platform = MethodChannel('button_simulator');

  // Método para simular o botão de volume
  Future<void> simulateVolumeButton(String action) async {
    try {
      await platform.invokeMethod('simulateButton', {'button': action});
    } on PlatformException catch (e) {
      print('Erro ao simular botão: ${e.message}');
    }
  }

  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: Column(
            children: [
              Text('Hello World!'),
              ElevatedButton(
                onPressed: () => simulateVolumeButton('volume_up'),
                child: Text('Simular Volume +'),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () => simulateVolumeButton('volume_down'),
                child: const Text('Simular Volume -'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
