Микросервесное CRUD REST FULL приложение, для создание и бронирование билетов. Создание маршрутов, перевезчиков, юзеров с ролями.
Убедитесь что у вас свободные порты 8081,8082,8083,8085,5432
Клонируйте себе приложение и запустите файл db.yml.
В Docker поднимится база данных и создадутся все нужные таблицы.
http://localhost:8082/swagger-ui.html#/auth-controller зарегистрируйтесь и выбирете роль. И скопируйте токен.
Авторизуйтесь с токеном. Bearer"пробел"Ваш токен
http://localhost:8085/swagger-ui.html#/carrier-controller  создайте перевозчика  Создавать перевозчика может только Administrator
http://localhost:8083/swagger-ui.html#/route-controller создайте маршрут. Создавать маршрут может только Administrator
http://localhost:8081/swagger-ui.html#/ticket-controller создайте билет.  Создавать билеты может только Administrator
Юзер с ролью Administrator может то что и Client, а также удалать билеты, маршруты, перевозчиков.
Юзер с ролью Client может просматривать свободные юилеты, бронировать(покупать), и просматривать свои билеты.

