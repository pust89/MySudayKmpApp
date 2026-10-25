# MySundayKmpApp

Простое приложение на Kotlin Multiplatform и Compose Multiplatform. Один и тот же интерфейс работает на Android и iOS.

Три экрана: список товаров, карточка товара и корзина. Внизу таб-бар «Продукты» / «Корзина». Товары и корзина хранятся только в памяти: сети и базы данных нет.

## Стек

| | |
|---|---|
| Kotlin | 2.4.20 |
| Compose Multiplatform | 1.12.1, Material3 1.12.0-alpha03 |
| Навигация | Decompose 3.5.0 |
| Состояние экранов | MVIKotlin 4.4.0 |
| DI | kotlin-inject 0.9.0, KSP 2.3.12 |
| Корутины и сериализация | kotlinx-coroutines 1.11.0, kotlinx-serialization 1.12.0 |
| Сборка | Gradle 9.8.0, AGP 9.1.1 |
| Android | minSdk 24, compileSdk / targetSdk 37 |

Версии лежат в `gradle/libs.versions.toml`.

## Структура модулей

```
:androidApp              APK, точка входа Android (MainActivity)
    └── :shared          корневой модуль: навигация, DI, iOS-фреймворк Shared
            ├── :shared:products    список товаров и деталка
            ├── :shared:cart        экран корзины
            └── :shared:data        модель Product и репозитории
```

| Модуль | Что внутри | От кого зависит |
|---|---|---|
| `:shared:data` | `Product`, `ProductsRepository`, `CartRepository` и их in-memory реализации | ни от кого из приложения |
| `:shared:products` | список и деталка: навигация список → деталка, экраны, Store | `:shared:data` |
| `:shared:cart` | экран корзины и его Store | `:shared:data` |
| `:shared` | корневая навигация, `AppComponent`, `RootContent`, `MainViewController` | `:shared:products`, `:shared:cart` |
| `:androidApp` | `MainActivity`, сборка APK | только `:shared` |

`:shared:products` и `:shared:cart` не зависят друг от друга. Общий тип между ними — `Product` из `:shared:data`.

iOS-фреймворк `Shared` (`isStatic = true`) собирается только из `:shared`. Swift-обёртка лежит в `iosApp` и подключает этот фреймворк.

## Как устроено

Навигация на Decompose. Табы — `ChildStack` с `bringToFront`: переключение «Продукты» / «Корзина» не сбрасывает открытый экран. Внутри вкладки «Продукты» свой стек: список → деталка, назад возвращает к списку.

Состояние каждого экрана держит MVIKotlin Store. Зависимости собирает kotlin-inject: корневой `AppComponent` и отдельные компоненты фич. Список товаров отдаёт `InMemoryProductsRepository`, корзину — `InMemoryCartRepository`. Одна корзина на процесс: добавление на деталке сразу видно во вкладке «Корзина».

## Запуск

- Android: `./gradlew :androidApp:assembleDebug`
- iOS: открыть каталог `iosApp` в Xcode и запустить оттуда
