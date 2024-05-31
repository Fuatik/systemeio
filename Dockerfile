# Используем официальный образ Java в качестве базового
FROM openjdk:22-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл jar из текущей директории в контейнер
COPY target/test_task_systemeio-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]