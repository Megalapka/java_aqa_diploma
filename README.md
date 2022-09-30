## Процедура запуска автотестов

1. Склонировать репозиторий: 
`git clone https://github.com/Megalapka/java_aqa_diploma.git`

1. Перейти в папку `java_aqa_diploma`

1. Запустить контейнер Docker командой в консоле 
(_позже разберусь с контейнером для postgres_):
`docker-compose up`

1. Запустить приложение командой в консоли:
`java -jar artifacts/aqa-shop.jar`

1. Запустить авто-тесты.

1. После выполнения всех тестов остановить docker контейнер командой в консоле: 
`docker-compose down`
