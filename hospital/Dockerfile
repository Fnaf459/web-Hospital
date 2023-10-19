# Используем базовый образ с Java и Gradle
FROM gradle:8.4-jdk21 as builder

# Копируем файлы с исходным кодом в контейнер
COPY --chown=gradle:gradle . /home/gradle/project

# Задаем рабочий каталог
WORKDIR /home/gradle/project

# Собираем проект с помощью Gradle
RUN gradle clean build

# Используем базовый образ с Java
FROM gradle:8.4-jdk21

# Задаем переменные среды для подключения к базе данных
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://192.168.56.1:5432/hospital
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=303024

# Копируем JAR-файл внутрь контейнера
COPY --from=builder /home/gradle/project/build/libs/hospital-0.0.1-SNAPSHOT.jar /app/hospital.jar

# Задаем переменные среды для Gradle
ENV PATH="/opt/gradle/bin:${PATH}"

# Задаем рабочий каталог
WORKDIR /app

# Копируем файлы с исходным кодом в контейнер
COPY . /app/

# Собираем проект с помощью Gradle
RUN gradle clean build

# Пробрасываем порт 8081
EXPOSE 8081

# Запускаем JAR-файл
CMD ["java", "-jar", "hospital.jar"]
