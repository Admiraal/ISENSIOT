# Android project ISENSIOT

### Over het project
Dit project is ter ondersteuning van de Android lessen ten behoeve van de minor Sensor Technologie aan Hogeschool Leiden. 
In dit project maken we een android app met twee schermen:
- MainActivity: Een scherm met een knop naar het volgende scherm. 
- GraphActivity: Een scherm met een grafiek die wijzigt zodra de waarde van de lichtsensor wijzigt


------------

### Het uitlezen van sensoren in Android
Er zijn 2 klassen en 1 interface betrokken bij het uitlezen van een sensor.


- ##### SensorManager Klasse:
In de constructor van de activity opvragen van de SystemService met de volgende regel code:
```java
this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
```


- ##### Sensor Klasse:
In de onResume() callback methode de Sensor klasse declareren met de volgende regel code:
```java
this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
```

- ##### SensorEventManager interface:
Implementeer de SensorEventManager interface. Deze zorgt ervoor dat de activity gereed is om wijzigingen van de sensor waarde te ontvangen. Deze interface zorgt voor het implementeren van de volgende methoden:
```java
@Override
public void onSensorChanged(SensorEvent sensorEvent) {
	// Doe hier iets met de waargenomen waarde van de sensor
}

@Override
public void onAccuracyChanged(Sensor sensor, int i) {
	// Doe hier iets als de precisie van de sensorwaarde niet kan worden gewaarborgd
}

```

### Werken met de Activity lifecycle
De ActivityLifecycle vertelt ons door welke stadia een Activity gaat. Elk stadium van een activity kan je aanspreken met een zogenaamde callback. Een callback kan je gebruiken door de betreffende methode te overriden.

[![Android-Activity-Lifecycle.png](https://i.postimg.cc/28M49x1x/Android-Activity-Lifecycle.png)](https://postimg.cc/565Y6LqH)

#### onResume()
In de onResume() methode declareer je de sensor die je wil gebruiken. Je registreert jouw Activity als ontvanger van sensorwaarde-updates. Hierbij geef je aan hoe snel de sensordata mag worden doorgegeven. Ook vang je hier af als het betreffende device deze sensor niet ter beschikking heeft gesteld voor jouw app. Hieronder voorbeeldcode: 
```java
@Override
public void onResume(){
	super.onResume();

	this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

	if(sensor != null){
		sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_FASTEST);
	} else {
		String inaccurateDataMessage = getResources().getString(R.string.sensorNotAvailable);
		Toast toast = Toast.makeText(this, inaccurateDataMessage, Toast.LENGTH_SHORT);
		toast.show();
	}
}
```

#### onPause()
De onPause() callback methode wordt aangeroepen zodra deze activity niet meer zichtbaar is voor de gebruiker. Op dat moment wil je dus dat de sensormanager stopt met het aanroepen van de sensorwaarde-updates. Dat kan je doen door de Activity af te melden. Hieronder voorbeeldcode: 
```java
@Override
public void onPause(){
	super.onPause();
	sensorManager.unregisterListener(this);
}
```


#### MainActivity:
![De MainActivity](https://i.postimg.cc/G9Lc7b38/main-Activity.png "De MainActivity")

#### GraphActivity:
![De GraphActivity](https://i.postimg.cc/K40mCC49/graph-Activity.png "De GraphActivity")


------------
#### Additional Sensors
Om het lijndiagram te beïnvloeden open je in de android emulator op "Extended Controls" > "Virtual Sensors" > open dan het tabblad "Additional sensors" om de luxmeter te beïnvloeden. De lijndiagram in de GraphActivity toont de nieuwe (lux)waarden.

![Additional Sensors](https://i.postimg.cc/64ptyfbL/additional-sensors.png "Additional Sensors")


### Graph library
Dit project maakt gebruik van de library MPAndroidChart van Phil Jay. 
Link: [https://github.com/PhilJay/MPAndroidChart](https://github.com/PhilJay/MPAndroidChart "https://github.com/PhilJay/MPAndroidChart")

Toevoegen van deze library aan je project:
Voeg dit toe aan repositories binnen dependencyResolutionManagement in het settings.gradle bestand: 
- maven { url 'https://jitpack.io' }

Voeg dit toe aan de dependencies in build.gradle (module):
- implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
