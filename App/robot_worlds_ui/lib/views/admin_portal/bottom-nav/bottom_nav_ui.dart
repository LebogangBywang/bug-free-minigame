import 'package:flutter/material.dart';

class BottomNav extends StatelessWidget {
  const BottomNav({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      items: <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: SizedBox(
            child: IconButton(
                onPressed: () {},
                icon: const Icon(
                  Icons.save_alt_outlined,
                  size: 35,
                )),
            width: 64,
            height: 64,
          ),
          label: 'Save World',
        ),
        BottomNavigationBarItem(
          icon: SizedBox(
            child: IconButton(
                onPressed: () {},
                icon: const Icon(
                  Icons.restore_outlined,
                  size: 35,
                )),
            width: 64,
            height: 64,
          ),
          label: 'Restore World',
        ),
        BottomNavigationBarItem(
          icon: SizedBox(
            child: IconButton(
                onPressed: () {},
                icon: const Icon(
                  Icons.exit_to_app_outlined,
                  size: 35,
                )),
            width: 64,
            height: 64,
          ),
          label: 'Close Server',
        ),
      ],
      selectedItemColor: Colors.amber[800],
      selectedFontSize: 20,
      unselectedFontSize: 20,
    );
  }
}
