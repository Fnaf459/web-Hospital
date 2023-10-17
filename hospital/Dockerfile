# Используем базовый образ с Java и Gradle
FROM gradle:jdk21 as builder

# Копируем файлы с исходным кодом в контейнер
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# Собираем проект с помощью Gradle
RUN gradle clean build

# Создаем образ с рантаймом
FROM adoptopenjdk/openjdk21:jre
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar
WORKDIR /app

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]
