MIS

Этот раздел для описания и концентрации знаний по всем технологиям, еспользуемым на проекте.
Если вы столкнулись с проблемой и нашли решение, поделитесь кратко технологией и хорошими ресурсами.
Не стесняйтесь делиться информацией и делайте добро.

ВАЖНО!
Если вы модифицировали логику или привнесли функционал,
или нашли дополнительные полезные ресурсы, то
обязательно допишите здесь полезную информацию для всех.


HQL

https://java-online.ru/hibernate-hql.xhtml


Swagger

Это фреймворк для спецификации RESTful API. С его помощью можно удобно отправлять запросы на сервер.
Web интерфейс Swagger доступен после старта проекта и аутентификации под определенным юзером
по адресу: http://localhost:8080/swagger-ui/index.html#/ (значение порта 8080 может быть настроено в
application.properties: server.port=)
Информация по SWAGGER - https://habr.com/ru/post/434798/
Настройка в классе SwaggerConfig.
Кратко по работе со Swagger:
После запуска проекта перейдя по адресу: http://localhost:8080/swagger-ui/index.html загрузится
веб интерфейс Swagger. Нам будет доступен контроллер для аутентификации auth-controller
с помощью которого мы можем отправить запрос типа LoginRequest, содержащий два строковых поля:
email и password на сервер (нажать try it out -> вместо string подставить значения -> execute).
При успешной аутентификации получаем ответ типа JwtResponse содержащий сгенерированный токен
(начинается на Bearer), id, email, roles. Для его добавления в header при каждом последующем
запросе нужно скопировать его значение без кавычек, нажать Authorize (кнопка с замочком) ->
вставить значение в поле value -> Authorize (замочек поменяет значение на закрытый).
Для тестирования работы методов контроллера выбираем нужный -> try it out -> вводим необходимые
данные -> execute.

Unit testing

https://habr.com/ru/post/169381/


Integration testing

https://www.youtube.com/watch?v=Lnc3o8cCwZY


MapStruct

https://mapstruct.org/documentation/dev/reference/html/
Mapstruct — это библиотека, которая помогает сопоставлять (маппить) объекты одних сущностей
в объекты других сущностей при помощи сгенерированного кода на основе конфигураций,
которые описываются через интерфейсы.


Global Exception handler

https://habr.com/ru/post/528116/
https://www.baeldung.com/exception-handling-for-rest-with-spring#controlleradvice
Любой класс с аннотацией @ControllerAdvice является глобальным обработчиком исключений,
который очень гибко настраивается. Метод handleException имеет аннотацию @ExceptionHandler,
в которой, можно определить список обрабатываемых исключений.


Properties

Как установить свои проперти?
Открываете файл application.properties, изучаете. В IntelliJ IDEA, чуть правее середины,
в верхнем интерфейсе(около кнопки Run) есть выпадающее окно, далее жмете Edit configurations.
Нажмите на плюсик слева и добавьте свою конфигурацию, например с названием RunDataInitializer.
Далее выбираете Modify options(подсвечено), ставите галочку на строке Override configuratin properties,
вводите свои переменные в режиме ключ-значение, в Build and run укажите класс с методом main и сохраняете
свою новую конфигурацию.
По этой же схеме нужно будет переписать конфигурация MisApplication.
И проверьте аннотацию @СonditionalOnExpression, класс DataInitializer запустится тогда,
когда в будет выбрана нужная конфигурация, с нужной переменной "RUN_INIT".


ApiValidationUtils

Используйте данный класс для валидации данных в контроллере.


FlyWay
Flyway - это инструмент миграции (переноса) базы данных. Проще говоря, это инструмент,
который помогает вам выполнять сценарии базы данных при развертывании приложений.
Flyway поддерживает два типа сценариев, SQL и Java. Вы можете упаковать сценарий в приложение.
Когда приложение запускается, Flyway управляет выполнением этих сценариев.
Эти сценарии называются миграцией Flyway.
Flyway создаст таблицу в вашей БД для записи выполнения миграции.
Имя таблицы по умолчанию:flyway_schema_history. В данном проекте все сценарии хранятся в ресурсах > db.migrations.
Основная информации по ссылкам по порядку вполне достаточно для освоения:

https://www.youtube.com/watch?v=ArM7nCys4hY
https://www.youtube.com/watch?v=61ChGaqxELw


JpaRepository

Для простых CRUD запросов используйте готовые методы  JpaRepository.
Для более сложных запросов можно использовать анотацию @query над методом и
там прописать SQL запрос.
https://sysout.ru/spring-data-jpa-zaprosy-generiruemye-po-imeni-metoda/
https://habr.com/ru/post/435114/


SQL Учебник

https://www.schoolsw3.com/sql/index.php


Mockito

https://habr.com/ru/post/444982/
https://www.youtube.com/watch?v=Wmrdfzzpr6A
https://www.youtube.com/watch?v=lpPEAHLGoJg


dto
https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions


Spring security с авторизацией токеном jsonwebtoken
https://www.bezkoder.com/spring-boot-jwt-authentication/

Используемые классы:
WebSecurityConfigurerAdapter - основной класс конфгурации. В массиве AUTH_WHITELIST можно добавить открытые
ресурсы. В методе configure(HttpSecurity http) настраивается
UserDetailsServiceImpl - содержит метод поиска и загрузки пользователя по email и создание на его основе
объекта UserDetails который будет использоваться для аутентификации и проверки
UsernamePasswordAuthenticationToken получает