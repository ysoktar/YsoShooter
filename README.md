YsoShooter

# Derleme ve Açma

Unziplenen YsoShooter dizini içinde:

javac src/Guns/\*.java src/Zombies/\*.java src/GameClasses/\*.java

java src/GameClasses/Game.java

# Oyun

Wasd ya da oklar ile hareket edilebilir.

X ya da sol fare ile ateş edilebilir, R ile şarjörü yeniden doldulabilir, 12345 ile silah seçileilir, Q ile sol E ile sağ silaha geçilebilir.

Esc ile oyun durdurulabilir. Durdurulduğunda bir daha Esc ile devam edilebilir, G ile yeni oyuna başlatılabilir, F ile kaydedilebilir, L ile şuan kaydedilmiş olan oyun durumu yüklenebilir.

Dalga bittiğinde oyun C ile geçişi bekler. Beklerken hareket edilerek mermi toplanabilir, silah değiştirlebilir ve denenebilir ayrıca oyun dosyaya kaydediliebilir ve dosyadan yüklenebilir.

Ortasında c olan beyaz daire sağlık paketlerin üstünden geçilerek sağlık değeri bir arttırılabilir.

Sol üstteki seçili silah numarası ile aynı numaralı siyah sağ üstünde numara olan siyah daire şarjörlerin üstünden geçilerek seçili silahın şarjör sayısı arttırılabilir.

# Sınıflar

## GameClasses

### Game

Jframe oluşturur ve bu frame’e GamePanel bağlar.

### GameObject

Oyundaki diğer nesnelerin sınıfları bu abstract sınıftan türer. Serializable marker interface’ini kullanır. x, y koordinatlarını ve objenin x ekseniyla yaptğı açıyı tutar.

### GameMover

Player ve Zombie sınıfları bu sınıftan türer. WIDTH, HEIGHT ve collisionRadius statik sınıf değişkenlerini tutar. Up, down left, right booleanları ile hareket yönünü belirler. collidedWith() ve move() ilgili yerlerde metotlorı çağırıldığında başka bir nesne ile çerpıştığınıda tepki vermesini ve hareket etmesini sağlar. move() metodunda Player sınıfının nesnesi olup olmadığını kontrol eder ve öyle ise mermlerin move() metodunu çağırarak mermileri de hareket ettirir.

### Player

GameMover sınıfından türer. GameState sınıfının bir nesnesine sabit bir referans tutar. Shoot, reload booleanları ile silah kullanır. Farenin anlık yerini tutan mouseX ve mouseY tam sayıları kullanarak karakterin dönme açısını override edilen move() metodunda belirler.

Silahtan çıkan mermileri ve asit tükürüklerini birer HashMap’te tutar. Silah envanterini silahları bir ArrayList’te tutarak ve gunIndex ile sağlar.

Can ve skor değerlerini health ve score tam sayılarında tutar.

Test modunda karakterin ölmemesini INVINCIBLE booleanını kullanarak changeHealth() metodunda sağlar.

Silah değiştirmek için changeGun() ve sol, sağ değiştirme metotları vardır.

Karakteri sıfırlayan refresh() metodu vardır.

### GameState

Oyunun durumunu tutar ve dalga geçişlerini ayarlar. Serializable marker interface’ini kullanır. Player sınıfının bir nesnesine sabit bir referans tutar. Zombie, Explosion, Ammo ve HealthPack sınıfların LinkedListlerini tutar. running ve transition booleanları ile oyunun durdulma durumu ve dalga geçiş durumunu tutar. wave ve unlockedGuns tam sayıları ile hangi dalgada olduğunu ve verilen silahları tutar.

checkState() metodu dalganın bitip bitmediğini kontrol eder ve bitmişse yeni dalgaya geçiş sürecini başlatır. nextWave() metodu sonraki dalgaya geçer. lose(), esc() metotları durum değişikliği yapar. refresh() metodu durumu sıfırlar. testZombie() her zombi tipinden 300 tane olacak şekilde toplam 1200 tane zombi ekler ve karakterin INVINCIBLE booleanını doğru yapar.

### GameInput

GamePanel’a bağlanarak klavye ve mouse girdilerini alır. KeyListener, MouseMotionListener, MouseListener interfacelerini kullanır. Player, GameState, GamePanel sınıflarının nesnelerine referans tutar.

### GameWave

Dalgaları oluştur. Zombi, Ammo ve Healthpack sınıflarının LinkedListinin tutar ve doldurur. addZombie(), addAmmo() ve addHealthPack() yardımcı metotlarını kullanır. GameState’te state değiştirilirken nesneye kaydedilmeden kullanılır.

### GamePanel

Oyun döngüsünü ve tüm çizimleri yapar. Jpanel’den türer Runnable interface’ini kullanır GameState, Collision ve Player sınıflarının objelerin birer referans tutar. Önce tüm çizimleri bir BufferedImage’e yapar sonra override edilen drawComponent() metodunda sadece o BuffereImage’i çizer.

Override edilen run() fonksiyonunda sonsuz dögü içinde oyun döngüsünü gameLoop(), System.nanoTime() ve Thread.sleep() kullanarak kurar ve fps hesabı yapar. 30 fps hedefler.

gameLoop() metodunda gameUpdate(), gameRender(), renderTransition(), renderMenu(), renderLoseMenu() metotlarını kullanarak duruma göre çizim ve güncelleme yapar. Sadece Explosion sınıfının draw() metodunu çağırır. Çok büyük çaplı bir proje olmadığından öbür sınıflara draw() metodu yazmadım onları direk metotta çiziyorum. Çizilen her sınıfın draw() metodu olması OOP prensiplerine daha uygun.

gameUpdate() metodu player.move(), her zombi için zombie.move(), collision.detectCollisions() ve state.checkState() metotlarını çağırır.

save() ve load() fonksiyonları oyunu “.save” dosyasına kaydetme ve dosyadan yüklemeyi sağlar.

### Collision

Tüm çarpışmaları kontrol eder. Serializable marker interface’ini kullanır. Player sınıfının nesnesine bir referans tutar. Zombie, Explosion ve Ammo sınıflarının LinkedList’ini tutar. Her hareket eden nesnenin birbirleriyle, mermilerin zombilerle, patlama ve tükürüklerin her hareket eden nesne ile çarpışmasını Iterator yardımıyla kontrol eder. Hareket edenler için checkCollision(), mermiler için checkBullet() yardımcı metotlarını kullanır.

HealthPack, Explosion, ExplodingBuller, Ammo sınıflarının çarpışma durumanda ne yapacağı da checkCollision() tarafından belirlenir.

### HealthPack

GameObject sınıfından türer. radius ve health tam sayı değişkenlerini tutar.

## Guns

### Bullet

GameObject sınıfından türer. Statik color değişkeni var. penetrate booleanı ile zombinin içinden geçip geçemediğini, speed tam sayısı ile hızını tutar.

### Gun

Abstract sınıftır. Serializable marker interface’ini kullanır. magazineCapacity ve cooldownSeconds sabit sınıf değişkenlerini tutar. addMagazine(), getTotalBullets(), getBullets(), shoot(), reload() metotları vardır.

### Pistol & AssaultRifle & Shotgun & SniperRifle & RocketLauncher

Gun sınıfından türerler ve üstüne gerekli değişkenler ekler ve gerekirse shoot() ve reload() metotlarını override ederler

### ExplodingBullet

Bullet sınıfından türer. radius sınıf değişkeni patlamanın yarıçapını tutar.

### Explosion

GameObject sınıfından türer. radius, damage, x, y tam sayı değişkenlerini tutar. draw() metodu ile çizilir.

### Ammo

GameObject sınıfından türer. radius ve type tam sayı değişkenlerini tutar.

## Zombies

### Zombie

GameMover sınıfından türer. bulletsToDie, speed, damage, points tam sayı değişkenlerini tutar. ImageIcon olarak resmini ve Player sınıfının nesnesine bir referans tutar. hit() metodu ile canını bir azaltır. collidedWith() ve move() metotlarını override eder.

### CrawlerZombie & TankZombie & AcidSpittingZombie

Zombie sınıfından türerler. Gerektiği zamana move() metodunu override ederler. Zombie sınıfının değişkenlerine kendi özelliklerine göre değer verirler.

CrawlerZombie jump() metodu ile zıplar. AcidSpittingZombie spit() metoduyla AcidSpit tükürür.

### AcidSpit

ExplodingBullet sınıfından türer. COLOR statik renk değişkeni ve damage tam sayı değişkenini tutar.