# Jak strzelać w rzeczy jeżdżąc czołgiem w ciągu 1h
## Czyli kolejny tutorial do gry z libgdx'a

Hej! 

W tym tutorialu dowiecie się, jak zrobić fajną grę używając libgtx! Fajną, bo czołgi są fajne, prawda?

A wiec po kolei, co musimy zrobić, aby rozpocząć pracę:

###### 1.Najpierw wypada pobrać repozytorium z kodem z [adresem](https://github.com/gaijinx/hackaton).
Tutaj znajdziecie nasze repozytorium. Należy je pobrać/sklonować i zaimportować do Android Studio, 
czy innego IDE skonfigurowanego do pracy z Androidowymi Smartphonami.
###### 2.Budujemy apkę! Chcemy być pewnwi, że nasze środowisko jest skonfigurowane do pracy z kodem.
Naszym oczom ukazuje się Game Launcher, agresywny wstęp do brutalnej rozgrywki. Ale jak do niego dotarliśmy? 
Odpalcie plik w ścieżce **android/java/package/AndroidLauncher**. Widzimy, że ma on jedną metodę. **OnCreate**. 
Znaczy to Przy Stworzeniu. Brzmi mistycznie, ale chodzi o to, co pojawi się na naszym ekranie, kiedy apka wystartuje. I co tam się dzieje? Inicjalizujemy MyGame -> brzmi dość logicznie, prawda? Czyli startuje gra! Ale wszystko, co startuje, musi mieć ustawienia. Po to jest AndroidApplicationConfiguration.
###### 3. No to zobaczmy, co zawiera ten plik MyGame
MyGame jest bardzo prosty. Ma pola:
--Muzykę -> chcemy grać coś w tle, aby uprzyjemnić rozlew krwi.
--Rozmiary ekranu -> Jego wysokość i szerokość jest tutaj zdefiniowana
--Ustaw Ekran -> setScreen pokaże nam kolejna klasę, którą rozpoczniemy grę.
###### 4. Czyli MyGame tak naprawdę odpala GameLauncher
