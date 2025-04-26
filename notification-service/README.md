Notification Service

Notification Service, kullanıcılara bildirim göndermekle sorumludur.

Amaç

Kullanıcılara e-posta, SMS gibi bildirimler göndermek.
Diğer servislerden gelen event'lere (örneğin, rezervasyon oluşturma) tepki vermek.

İşlevler

Kafka'dan "Reservation Created" event'ini tüketme
Kullanıcılara bildirim gönderme

API Endpoint'leri
Notification Service, genellikle doğrudan bir API sunmaz. Bunun yerine, Kafka'dan gelen event'lere tepki verir. Ancak, bildirim durumunu kontrol etmek veya bildirim şablonlarını yönetmek için API'ler eklenebilir.


Kullanım

Diğer servisler (örneğin, Reservation Service), Kafka'ya event'ler gönderir. Notification Service, bu event'leri tüketir ve gerekli bildirimleri gönderir.