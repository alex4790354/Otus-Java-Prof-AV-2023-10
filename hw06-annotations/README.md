# hw06-annotations

1) create three annotations - @Test, @Before, @After.
2) Create a test class containing methods marked with annotations.
3) Create a "test launcher". As input, it should receive the name of the class with tests, in which it should find and run the methods marked with annotations and point 1.
4) The startup algorithm should be as follows:
- Method(s) Before
- current Test method
- After method(s)
- for each such “triple” you need to create your own test class object.
5) An exception in one test should not interrupt the entire testing process.
6) Based on the exceptions that occurred during testing, display test execution statistics (how many were successful, how many failed, how many were in total)
7) The “test launcher” should not have a state, but at the same time all functionality should be divided into private methods.
We need to figure out how to pass information between methods.


# ########################

1) создать три аннотации - @Test, @Before, @After.
2) Создать класс-тест, в котором будут методы, отмеченные аннотациями.
3) Создать "запускалку теста". На вход она должна получать имя класса с тестами, в котором следует найти и запустить методы отмеченные аннотациями и пункта 1.
4) Алгоритм запуска должен быть следующий::
- метод(ы) Before
- текущий метод Test
- метод(ы) After
- для каждой такой "тройки" надо создать СВОЙ объект класса-теста.
5) Исключение в одном тесте не должно прерывать весь процесс тестирования.
6) На основании возникших во время тестирования исключений вывести статистику выполнения тестов (сколько прошло успешно, сколько упало, сколько было всего)
7) "Запускалка теста" не должна иметь состояние, но при этом весь функционал должен быть разбит на приватные методы.
Надо придумать, как передавать информацию между методами.
