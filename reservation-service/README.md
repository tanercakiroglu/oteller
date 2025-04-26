Reservation Service

Reservation Service, otel rezervasyonlarını yönetir.

Amaç

Otel rezervasyonlarını saklamak ve yönetmek.
Rezervasyon CRUD işlemlerini sağlamak.
Yeni rezervasyon oluşturulduğunda Kafka'ya event yayınlamak.

İşlevler

Rezervasyon oluşturma, getirme, güncelleme ve silme

"Reservation Created" event'ini Kafka'ya gönderme

API Endpoint'leri

GET /reservations: Tüm rezervasyonları listeler.

GET /reservations/{id}: Belirli bir rezervasyonu getirir.

POST /reservations: Yeni bir rezervasyon oluşturur.

PUT /reservations/{id}: Belirli bir rezervasyonu günceller ve reservation updated eventi yollar.

DELETE /reservations/{id}: Belirli bir rezervasyonu siler.

DELETE /reservations/{id}/cancel: Belirli bir rezervasyonu siler ve reservation-cancelled eventi yollar.

Kullanım

İstemciler, API Gateway üzerinden Reservation Service'e istek gönderir. İsteklerde, rezervasyon bilgileri JSON formatında gönderilir.