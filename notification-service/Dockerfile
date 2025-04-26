# Temel imaj olarak OpenJDK'yı kullan
FROM openjdk:19-jdk-slim

# Uygulama için bir dizin oluştur
WORKDIR /app

# Uygulama JAR dosyasını kopyala
COPY target/*.jar app.jar

# Uygulamayı çalıştırmak için komut
CMD ["java", "-jar", "app.jar"]

# Container ayağa kalktıktan sonra çalışmasını istediğimiz portu belirtiyoruz.
EXPOSE 8083