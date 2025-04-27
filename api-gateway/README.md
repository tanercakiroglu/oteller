# Otel Rezervasyon Sistemi Mikroservisleri

Bu proje, otel rezervasyon işlemlerini yönetmek için geliştirilmiş bir mikroservis mimarisidir. Her bir servis, belirli bir işlevselliği yerine getirmek üzere tasarlanmıştır.

## Servisler

### 1. API Gateway

API Gateway, tüm mikroservisler için tek bir giriş noktası görevi görür. İstemcilerden gelen istekleri uygun servise yönlendirir ve güvenlik (kimlik doğrulama, yetkilendirme) işlemlerini gerçekleştirir.

#### Amaç

* Tüm mikroservisler için tek bir giriş noktası sağlamak.
* İstekleri ilgili servislere yönlendirmek.
* Kimlik doğrulama ve yetkilendirme işlemlerini yönetmek.

#### İşlevler

* **Yönlendirme:** İstemci isteklerini URL'ye göre ilgili servise yönlendirir.
* **Kimlik Doğrulama:** JWT (JSON Web Token) kullanarak gelen isteklerin kimliğini doğrular.
* **Yetkilendirme:** Doğrulanmış kullanıcıların belirli kaynaklara erişim yetkisini kontrol eder.

#### Yönlendirme Kuralları

Yönlendirme kuralları `application.yaml` dosyasında tanımlanmıştır:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: hotel-service
          uri: http://hotel-service:8081
          predicates:
            - Path=/hotels/**, /rooms/**
          filters:
            - JwtTokenFilterFactory
        - id: reservation-service
          uri: http://reservation-service:8082
          predicates:
            - Path=/reservations/**
          filters:
            - JwtTokenFilterFactory
        - id: notification-service
          uri: http://notification-service:8083
          predicates:
            - Path=/notifications/** # Gerekirse bir yol belirleyin
          filters:
            - JwtTokenFilterFactory
```
Kullanım
İstemciler, API Gateway'in adresine (örneğin, http://localhost:8080/token) basic auth ile istek gonderilir.
Asagida koda gomulu bir kullanici ismi sifre mevcuttur.
```
  @Bean
  public MapReactiveUserDetailsService userDetailsRepository() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user1")
        .password("password")
        .roles("READ")
        .build();
    return new MapReactiveUserDetailsService(user);
  }
```

Butun projeler java 21 ile calisiyor.

Hepsinde clean install aldiktan sonra bulunduklari dizinde 
```
    docker-compose build --no-cache 
    docker-compose up -d 
```
komutlari ile calistirilabilir.