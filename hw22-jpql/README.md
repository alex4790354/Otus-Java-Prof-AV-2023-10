# hw22-jpql

Practice the basics of Hibernate in practice.
Understand how hibernate annotations affect the formation of SQL queries.

The job must use a database in a docker container.
Take the example from the webinar about JPQL (class DbServiceDemo) as a basis.
Add fields to Client:
address (OneToOne)
class Address {
    private String street;
}
and phone (OneToMany)
class Phone {
    private String number;
}
Mark up the classes in such a way that when saving/reading a Client object, nested objects are saved/read in a cascade.
IMPORTANT:
- Hibernate should only create three tables: for phones, addresses and clients.
- When saving a new object there should be no updates.
- Look at the logs and check that these two requirements are met.


# ########################################################################

На практике освоить основы Hibernate.
Понять как аннотации-hibernate влияют на формирование sql-запросов.

Работа должна использовать базу данных в docker-контейнере .
За основу возьмите пример из вебинара про JPQL (class DbServiceDemo).
Добавьте в Client поля:
адрес (OneToOne)
class Address {
    private String street;
}
и телефон (OneToMany)
class Phone {
    private String number;
}
Разметьте классы таким образом, чтобы при сохранении/чтении объека Client каскадно сохранялись/читались вложенные объекты.
ВАЖНО:
- Hibernate должен создать только три таблицы: для телефонов, адресов и клиентов.
- При сохранении нового объекта не должно быть update-ов.
- Посмотрите в логи и проверьте, что эти два требования выполняются.