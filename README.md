# Дипломный проект по профессии «Тестировщик»

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.


## Процедура запуска автотестов

1. Склонировать репозиторий: 
`git clone https://github.com/Megalapka/java_aqa_diploma.git`

1. Перейти в папку `java_aqa_diploma`

1. Запустить контейнер Docker командой в консоли:
>`docker-compose up`

1. Запустить приложение командой в консоли

 *для MySQL*:
> `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
 
 *для PostgreSQL*:
> `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`

5. Запустить авто-тесты команой в консоли 

*для MySQL*:
> `./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app"`

*для PostgreSQL*:
> `./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`

6. Формирование Allure отчёта
> `./gradlew allureReport` - формирование отчета

> `./gradlew allureServe` -отображение отчета

7. После выполнения всех тестов остановить docker контейнер командой в консоли: 
`docker-compose down`
