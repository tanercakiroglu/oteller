Hotel Service
   
Hotel Service, otel ve oda bilgilerini yönetir.

Amaç
Otel ve oda bilgilerini saklamak ve yönetmek.
Otel ve oda CRUD (Create, Read, Update, Delete) işlemlerini sağlamak.
İşlevler
Otel oluşturma, güncelleme, silme ve listeleme
Oda oluşturma, güncelleme, silme ve listeleme

API Endpoint'leri

GET /hotels: Tüm otelleri listeler.

GET /hotels/{id}: Belirli bir oteli getirir.

POST /hotels: Yeni bir otel oluşturur.

PUT /hotels/{id}: Belirli bir oteli günceller.

DELETE /hotels/{id}: Belirli bir oteli siler.

GET /rooms: Tüm odaları listeler.

GET /rooms/{id}: Belirli bir odayı getirir.

POST /rooms: Yeni bir oda oluşturur.

PUT /rooms/{id}: Belirli bir odayı günceller.

DELETE /rooms/{id}: Belirli bir odayı siler.

Kullanım
İstemciler, API Gateway üzerinden Hotel Service'e istek gönderir. İsteklerde, otel ve oda bilgileri JSON formatında gönderilir.