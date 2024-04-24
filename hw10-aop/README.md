# hw10-aop

Develop functionality:
A class method can be marked with a homemade @Log annotation, for example like this:
class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {};
}

When calling this method, the parameter values must be logged “automagically” to the console.
For example like this.
class Demo {
    public void action() {
        new TestLogging().calculation(6);
    }
}

The console should have: executed method: calculation, param: 6
Please note: there should not be an explicit call to logging.
Please note that an annotation can be placed, for example, on the following methods:
- public void calculation(int param1)
- public void calculation(int param1, int param2)
- public void calculation(int param1, int param2, String param3)


# ########################

Разработать  функционал:
метод класса можно пометить самодельной аннотацией @Log, например, так:
class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {};
}

При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.
class Demo {
    public void action() {
        new TestLogging().calculation(6);
    }
}

В консоле дожно быть:
- executed method: calculation, 
- param: 6
Обратите внимание: явного вызова логирования быть не должно.
Учтите, что аннотацию можно поставить, например, на такие методы:
- public void calculation(int param1)
- public void calculation(int param1, int param2)
- public void calculation(int param1, int param2, String param3)

