# Android project ISENSIOT

### Over het project
Dit project is ter ondersteuning van de Android lessen ten behoeve van de minor Sensor Technologie aan Hogeschool Leiden.


### Het uitlezen van sensoren in Android
Er zijn 2 klassen en 1 interface betrokken bij het uitlezen van een sensor.
Klassen: 
- Sensor
- SensorManager
Interface:
- SensorEventManager


### Graph library
In het project is gebruik gemaakt van de library MPAndroidChart van Phil Jay. 
Link: [https://github.com/PhilJay/MPAndroidChart](https://github.com/PhilJay/MPAndroidChart "https://github.com/PhilJay/MPAndroidChart")



#### MainActivity:
![De MainActivity](https://i.postimg.cc/G9Lc7b38/main-Activity.png "De MainActivity")

#### GraphActivity:
![De GraphActivity](https://i.postimg.cc/K40mCC49/graph-Activity.png "De GraphActivity")


------------
#### Additional Sensors
Om het lijndiagram te beïnvloeden open je in de android emulator op "Extended Controls" > "Virtual Sensors" > open dan het tabblad "Additional sensors" om de luxmeter te beïnvloeden. De lijndiagram in de GraphActivity toont de nieuwe (lux)waarden.
![Additional Sensors](https://i.postimg.cc/64ptyfbL/additional-sensors.png "Additional Sensors")