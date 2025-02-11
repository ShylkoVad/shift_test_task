Описание программы
Данная программа предназначена для чтения данных о сотрудниках и менеджерах из текстового файла, обработки этих данных, сортировки сотрудников и вывода информации в консоль или файл. Программа также генерирует статистику по департаментам и обрабатывает возможные ошибки в данных.

Запуск программы:
1. Соберите проект с помощью команды: gradle build
2. Запустите программу через командную строку

Примеры запуска с параметрами:
1. Сортировка сотрудников по зарплате в порядке возрастания: java -jar D:\JAVA\shift_test_task\build\libs\shift_test_task-1.0-SNAPSHOT.jar -s=salary --order=asc
2. Сортировка сотрудников по имени в порядке убывания: java -jar D:\JAVA\shift_test_task\build\libs\shift_test_task-1.0-SNAPSHOT.jar --sort=name --order=desc
3. Вывод в файл: java -jar D:\JAVA\shift_test_task\build\libs\shift_test_task-1.0-SNAPSHOT.jar -s=salary --order=asc --output=file --path=src\main\resources\output.txt
