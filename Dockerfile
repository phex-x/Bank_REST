# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-slim as build

WORKDIR /app

# Копируем pom.xml и зависимости для кэширования слоёв
COPY pom.xml .
RUN ./mvnw dependency:go-offline || true

# Копируем исходники и собираем jar
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранный jar из предыдущего этапа
COPY --from=build /app/target/Bank_REST-1.0.0.jar app.jar

# Переменные окружения для JVM и Spring
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
