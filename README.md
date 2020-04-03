# Hello Sensor! 

## Individuell programmeringsuppgift - Beskrivning av uppbyggnad av app ##

### Allmänt ###
Har bytat bakgrundsbild genomgående i appen och lagt till en toolbar istället för default action bar.
Har använt mig av delar av [detta klipp](https://www.youtube.com/watch?v=DMkzIOLppf4), och [delar av den här koden](https://stackoverflow.com/questions/3307090/how-to-add-background-image-to-activity)
för att ändra bakgrund. Har både ändrat manuellt i XML-filerna och i design-läget. 

### Main Activity ### 

Har haft [Build your first app](https://developer.android.com/training/basics/firstapp/index.html) som mall enligt uppgift. Skapade här två knappar,
en som leder till aktiviteten COMPASS och en som leder till aktiviteten ACCELEROMETER. Har bytat färg och typsnitt på knapparna genomgående i appen. 

### Compass ###
Kopierade och använde mig av koden från [den givna länken](https://www.wlsdevelop.com/index.php/en/blog?option=com_content&view=article&id=38).

Jag har ändrat och lagt till: 
* _Bild_: Lagt till en egen bild. 
* _Vibration_: Haptisk interaktion. Lagt till så att det vibrerar när det pekar mot norr samt mot syd. Har använt inspo från [den här koden](https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate).
* _Ljud_: Ljud interaktion. Har lagt till så att den spelar olika ljud när du pekar mot norr samt syd. Har använt inspo från [den här koden](https://developer.android.com/reference/android/media/MediaPlayer.html).
* _Färg på text_: Färgen på texten av gradtalet ändras när den pekar mot syd respektive norr. 
* _Lågpass filtrering: La till lågpass filtrering för sensorerna enligt anvisad kod [Lågpass filtrering av sensorer](https://www.built.io/blog/applying-low-pass-filter-to-android-sensor-s-readings).

### Accelerometer ###
Har använt anvisade dokumentationer för [Sensor Manager](https://developer.android.com/reference/android/hardware/SensorManager.html) och [Sensor Event](https://developer.android.com/reference/android/hardware/SensorEvent.html).

Jag har ändrat och lagt till: 
* _Vibration_: När man tiltar telefonen till vänster eller höger i x-led så tiltar den. Har använt inspiration från samma länk som för kompassen.
* _Färg_: När man tiltar telefonen till vänster eller höger så ändras bakgrundsfärgen.
* _Ljud_: Det spelas två olika ljud beroende på om du har tiltat telefonen till vänster eller höger.
* _Text_: Det står "LEFT" respektive "RIGHT" beroende på vilket håll telefonen är lutad åt.
