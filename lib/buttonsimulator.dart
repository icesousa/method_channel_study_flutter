// import 'package:flutter/material.dart';
// import 'package:flutter/services.dart';

// class ButtonSimulator extends StatefulWidget {
//   const ButtonSimulator({Key? key}) : super(key: key);

//   @override
//   _ButtonSimulatorState createState() => _ButtonSimulatorState();
// }

// class _ButtonSimulatorState extends State<ButtonSimulator> {
//   static const platform = MethodChannel('button_simulator');

//   // Método para simular o botão de volume
//   Future<void> simulateVolumeButton(String action) async {
//     try {
//       await platform.invokeMethod('simulateButton', {'button': action});
//     } on PlatformException catch (e) {
//       print('Erro ao simular botão: ${e.message}');
//     }
//   }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: const Text('Simulador de Botões'),
//       ),
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: [
//             ElevatedButton(
//               onPressed: () => simulateVolumeButton('volume_up'),
//               child: const Text('Simular Volume +'),
//             ),
//             const SizedBox(height: 20),
//             ElevatedButton(
//               onPressed: () => simulateVolumeButton('volume_down'),
//               child: const Text('Simular Volume -'),
//             ),
//           ],
//         ),
//       ),
//     );
//   }
// }


