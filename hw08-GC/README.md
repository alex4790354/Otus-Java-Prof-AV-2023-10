# hw08: Garbage Collector.

Result: 
К сожалению:
- В текущей версии Intellij (2024.1) настройка Environment Variables уже немного отличается от описанного в видео (см Environment_Variables.PNG)
  описания "новой настройки" пока не нашел. 
  часть параметров копируется, часть нет
- Добавление или изменение EV параметров типа -Xms256m, видимого влияния на результат тестов не несет. 
- Настройка объема памяти GC используя файл idea64.exe.vmoptions видимого результата так же не дал.
  Есть какая-то разница в сроках работы времени класса, но погрешность не дает сделать явный вывод.    


# ###################################################################################################################### #
Task:

There is a ready-made application (homework module)
Run it with a heap size of 256 MB and look at the execution time in the log.
Example output:
spend msec:18284, sec:18
Increase the heap size to 2GB, measure the execution time.
Record the launch results in a table.
Determine the optimal hip size, i.e. size, exceeding which,
does not reduce application execution time.
Optimize the application.
Those. without changing the operating logic (but changing the code), make the application work quickly with minimal hitch.
Repeat the program execution time measurements for the same heap size values.

# ###################################################################################################################### #

Edit Configuration -> Modify options -> Add EV-options. 

# ###################################################################################################################### #

Есть готовое приложение (модуль homework)
Запустите его с размером хипа 256 Мб и посмотрите в логе время выполнения.
Пример вывода:
spend msec:18284, sec:18
Увеличьте размер хипа до 2Гб, замерьте время выполнения.
Результаты запусков записывайте в таблицу.
Определите оптимальный размер хипа, т.е. размер, превышение которого,
не приводит к сокращению времени выполнения приложения.
Оптимизируйте работу приложения.
Т.е. не меняя логики работы (но изменяя код), сделайте так, чтобы приложение работало быстро с минимальным хипом.
Повторите измерения времени выполнения программы для тех же значений размера хипа.
