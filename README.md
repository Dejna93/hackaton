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
- Muzykę -> chcemy grać coś w tle, aby uprzyjemnić rozlew krwi.
- Rozmiary ekranu -> Jego wysokość i szerokość jest tutaj zdefiniowana
- Ustaw Ekran -> setScreen pokaże nam kolejna klasę, którą rozpoczniemy grę.
###### 4. Czyli MyGame tak naprawdę odpala GameLauncher
A co to jest GameLauncher?
No chcemy mieć ekran startowy, zanim użytkownik zacznie grać. Co taki ekran ma zawierać?
- Tytuł i instrukcję. One wyjaśnią użytkownikowi, jak uruchomić naszą aplikację i powitają go ciepło. Dane te przechowujemy w polach typu **string**. Następnie pobiera je BitmapFont, którego inizjalizujemy z naszą czcionką. Ale zaraz! Skąd mam wziąć czcionkę? To akurat jest proste. Libgdx dostarcza narzędzie do tego. Stworzymy nasz wygląd i zapiszemy pliki png i fnt w folderze assets pod android. Narzędzie nazywa się Hiero.
- Potrzebujemy jeszcze obrazka, będącym logo/starterem naszej aplikacji. Zapisujemy go w teksturze (klasa texture). 

No dobra, ale gdzie to wszystko upakować, czyli co robią kolejne metody w naszej implementacji Ekranu - Screen?
Nasz ekran składa się z:
- konstruktora -> Tu zainicjalizujemy nasze zmienne
- funkcji update -> Tu będziemy pobierać dane od użytkownika. Metoda handle key uruchamia następny ekran po tym, jak Gdx.input.isTouched() zwróci prawda, czyli użytkownik dotknie ekranu.
- render -> To odświeżamy non stop. Tutaj będziemy rysować i właśnie to robimy. Funkcje draw umieszczają nasze 3 obiekty na ekranie. Pamiętajcie, że lewy górny róg to 0,0. No i że trzeba odjąć pół obiektu, aby był na środku, bo nasza pozycja będzie dotyczyć domyślnie krawędzi. Stąd te dziwne odejmowanie i dodawanie.
- dispose -> Co się pootwierało, trzeba pozamykać :) Nie zapominajmy sprzątać po sobie w kodzie. 

No to powiedzmy, że użytkownik nacisnął coś na ekranie. Nasz update zauważył, że nastąpiło wciśnięcie i można już wywołać znany nam setScreen() -> PRZECHODZIMY DO KOLEJNEGO EKRANU. 

###### 5.PlayScreen, czyli Let The Bodies Hit The Floor.
Czas na pancerną rozwałkę. Jednak nie będzie tak łatwo, będziemy dodawać wszystko po kolei. Zacznijmy od czołgu. Aby mnie pisać wszystkiego w jednym miejscu, Stworzymy klasę Player. Od razu jesteśmy świadomi, że nasze czołgi będą zasuwać w 4 strony i będą mogły się obracać. Potrzebujemy rozrózniać te akcje. Dlatego powstaną klasy 
- Kierunek 
- Obrot
One przechowują nam dane w przyjemnej i zrozumialej dla oka formie, jak góra, dół, prawo, lewo i kąty 90, 180etc.
Teraz mamy juz wszystko, aby ruszac czolgiem. A wiec klasa czolg!

###### 6.Nasz pancerny bohater: Czołg

Co może robić Czołg.

- Czołg moze być! Więc ustalmy jego rozmiar i go narysujmy. Będzie to Sprite, bo chcemy, aby interakcja między innymi obiektami na planszy obowiącywała.
- Czołg kiedyś wystrzelił. I aby nie robił tego zbyt często, musimy pamiętać, co ile to się dzieje.
- Czołg będzie się poruszał. W prawo, lewo itp. 
- Czołg będzie się poruszał tam, gdzie użytkownik chce. Czyli obslugujemy input.
- Potrzebujemu zwracać kolizję. Więc musimy dostać prostokąt, który nasz czołg reprezentuje.
Bardzo istotny tu jest parametr deltatime. Jest on zależny od parametrów procesora, więc mnożenie przez niego sprawia, że ruszamy się tak samo płynnie na szybszych i wolniejszych urządzeniach. A kiedy coś postrzeli? Musi wybuchnąć. Czyli klasa Eksplozja. W momencie, w którym natrafimy pociskiem, następuje bum. Wtedy pojawia się obraz wybuchu na mapie.

###### 7.PlayScreen.

PlayGame jest znanym nam obiektem rozszerzającym scenę. Czyli będzie wczytywal akcję użytkownika i poruszał czołgiem. Tutaj możecie zobaczyć w komnetarzach jak wczytać input. Zapraszamy do implementowania własnych opcji, wszystkie metody są dostępne.
Zróbmy tak:
- Postaraj się samodzielnie umieścić - narysować czołg.
- Przesuń go. Użyj gotowego move. Zastanow się, jak to zrobć
- Dodaj przeciwników, możesz wzorować się na naszym kodzie.

Hej, Pamiętaj, że zawsze, kiedy czegoś nie jesteś pewien, możesz eksperymentować z gotowym, zakomentowanym kodem. 
Życzymy przyjemnej rozgrywki :D
