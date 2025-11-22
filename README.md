# Shopping List

Android приложение для управления списком покупок, разработанное с использованием современных подходов Android-разработки.

## Описание

Shopping List — это простое и удобное приложение для создания и управления списком покупок. Позволяет пользователям добавлять товары с указанием названия и количества, отмечать их как купленные и удалять свайпом.

## Основной функционал

 Добавление новых товаров в список
 Редактирование существующих товаров
 Удаление товаров свайпом (swipe-to-delete)
 Отметка товаров как купленных/некупленных (долгое нажатие)
 Автоматическое сохранение данных в локальной базе данных
 Адаптивный интерфейс с поддержкой планшетов

## Технологии и библиотеки

### Язык программирования
- **Kotlin** 2.0.21

### Архитектура
- **Clean Architecture** — разделение на слои (data, domain, presentation)
- **MVVM** (Model-View-ViewModel) паттерн
- **Repository Pattern** для абстракции источника данных

### Android компоненты
- **Android SDK** (minSdk 24, targetSdk 35)
- **View Binding** — привязка представлений
- **ViewModel** (Lifecycle) — управление UI-связанными данными
- **LiveData** — реактивные потоки данных
- **RecyclerView** с DiffUtil — эффективное отображение списков

### База данных
- **Room Database** 2.6.1 — локальное хранение данных
- **Room KTX** — поддержка Kotlin Coroutines

### Dependency Injection
- **Dagger 2** 2.57 — внедрение зависимостей

### Асинхронность
- **Kotlin Coroutines** 1.10.2 — асинхронное программирование

### UI/UX
- **Material Design Components** 1.12.0 — современный дизайн
- **ConstraintLayout** — гибкая компоновка интерфейса
- **ItemTouchHelper** — поддержка swipe-жестов

### Инструменты разработки
- **Gradle** (Kotlin DSL) — система сборки
- **KSP** (Kotlin Symbol Processing) — кодогенерация для Room

## Структура проекта

```
app/src/main/java/com/flaao0/shoppinglist/
├── data/                      # Слой данных
│   ├── AppDatabase.kt        # Room база данных
│   ├── ShopItemDbModel.kt    # Entity для Room
│   ├── ShopListDao.kt        # DAO интерфейс
│   ├── ShopListMapper.kt     # Маппинг между слоями
│   └── ShopListRepositoryImpl.kt  # Реализация репозитория
│
├── domain/                    # Слой бизнес-логики
│   ├── ShopItem.kt           # Domain модель
│   ├── ShopListRepository.kt # Интерфейс репозитория
│   ├── AddShopItemUseCase.kt
│   ├── DeleteShopItemUseCase.kt
│   ├── EditShopItemUseCase.kt
│   ├── GetShopItemByIdUseCase.kt
│   └── GetShopItemListUseCase.kt
│
├── presentation/              # Слой представления
│   ├── MainActivity.kt       # Главная активность
│   ├── ShopItemActivity.kt   # Активность для редактирования
│   ├── ShopItemFragment.kt   # Фрагмент для добавления/редактирования
│   ├── MainViewModel.kt      # ViewModel для главного экрана
│   ├── ShopItemViewModel.kt  # ViewModel для редактирования
│   ├── ShopItemAdapter.kt    # Adapter для RecyclerView
│   ├── ShopItemViewHolder.kt # ViewHolder
│   ├── ViewModelFactory.kt   # Фабрика для ViewModel
│   └── ShopApp.kt            # Application класс
│
└── di/                        # Dependency Injection
    ├── ApplicationComponent.kt
    ├── ApplicationScope.kt
    ├── DataModel.kt
    ├── ViewModelKey.kt
    └── ViewModelModule.kt
```

## Требования

- Android Studio Hedgehog | 2023.1.1 или новее
- JDK 11 или выше
- Android SDK с минимум API 24 (Android 7.0)
- Gradle 8.12.1+

## Установка и запуск

1. Клонируйте репозиторий:
```bash
git clone <repository-url>
cd ShoppingList1
```

2. Откройте проект в Android Studio

3. Дождитесь завершения синхронизации Gradle

4. Запустите приложение на эмуляторе или реальном устройстве (минимальная версия Android 7.0)

## Использование

1. **Добавление товара**: Нажмите на кнопку с плюсом в правом нижнем углу, введите название и количество товара
2. **Редактирование**: Нажмите на товар в списке для редактирования
3. **Отметка как купленного**: Сделайте долгое нажатие на товар
4. **Удаление**: Свайпните товар влево или вправо

## Особенности реализации

- **Clean Architecture**: Четкое разделение ответственности между слоями обеспечивает легкую поддержку и тестируемость
- **Use Cases**: Бизнес-логика вынесена в отдельные use case классы
- **Repository Pattern**: Абстракция от источника данных позволяет легко заменить Room на другое хранилище
- **Dagger 2**: Внедрение зависимостей обеспечивает слабую связанность компонентов
- **Coroutines**: Асинхронные операции выполняются без блокировки UI-потока
- **DiffUtil**: Эффективное обновление RecyclerView без перерисовки всех элементов

## Лицензия

Этот проект создан в образовательных целях.

