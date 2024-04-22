# hw08: Garbage Collector.
'Edit Configuration' 
    -> 'Modify options' 
        -> 'Add VM-options'


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
Result:

# G1GC:
- Taking into account the spread of errors, a minimum amount of memory of 256m is quite sufficient for efficient operation of the GC
- a slight improvement (5%) occurs when increasing the memory to 1.280m (Xms1280m-Xmx1280m)
- an additional 5% gain occurs when increasing the memory to 20.480m

# ParallelGC and SerialGC:
- The efficiency of Parallel and Serial GC with small amounts of memory is several times worse compared to G1
- Work efficiency increases as the amount of memory allocated for the GC increases.
- The operating efficiency of Parallel and G1 is equalized with a memory capacity of ~4.096m.
- As the memory size continues to increase, Parallel GC becomes more efficient.
- For a given task, a given configuration, the greatest efficiency is achieved with a memory capacity of 15.360m.
  At the same time, the efficiency of Parallel GC is ~2 times higher than G1.

Code optimization allows you to increase the efficiency of the class by 3-4 times.


# ###################################################################################################################### #
Задача:

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

# ###################################################################################################################### #
Результат:

# G1GC:
- С учетом разброса ошибок, вполне достаточно минимального объема памяти в 256m для эффективной работы GC
- небольшое улучшение (в 5%) происходит при увеличении памяти до 1.280m (Xms1280m-Xmx1280m)
- дополнительный выигрыш в 5% происходит при увеличении памяти до 20.480m

# ParallelGC и SerialGC:
- Эффективность работы Parallel и Serial GC при небольших объемах памяти в разы хуже, по сравнению с G1
- Эффективность работы растить при увеличении объёма памяти, выделенной под GC.
- Эффективность работы Parallel и G1 выравнивается при объеме памяти ~4.096m.
- При дальнейшем увеличении объема памяти работа Parallel GC становится более эффективной.
- Для данной задачи, данной конфигурации, наибольшая эффективность достигается при объеме памяти 15.360m.
  При этом эффективность Parallel GC в ~2 раза выше G1.

Оптимизация кода позволяет увеличить эффективность работы класса в 3-4 раза (для тех типов GC и памяти).
