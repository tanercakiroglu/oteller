
Butun projeler java 21 ile calisiyor.

Hepsinde clean install aldiktan sonra docker file in bulundugu dizinde 
```
    docker-compose build --no-cache 
    docker-compose up -d 
```
komutlari ile calistirilabilir.

Zaman yetersizligi yuzunden bilinen eksiklikler
1-) Projelerdeki common_config paketleri aslinda ayri bir proje olmasi lazim ve bagimlilik olarak eklenmesi lazim
2-) Api-gateway ve hotel-service eksik entegrasyon testleri 
3-) Rezervasyon create veye update edilirken hotel servisine getByHotelIdAndRoomNumber call u atilmali ve otel ve oda numarasi valide edilmeli