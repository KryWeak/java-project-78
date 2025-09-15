# Java Project 78 — Валидатор данных

### Hexlet tests and linter status:
[![Actions Status](https://github.com/KryWeak/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/KryWeak/java-project-78/actions)
[![CI](https://github.com/KryWeak/java-project-78/actions/workflows/ci.yml/badge.svg)](https://github.com/KryWeak/java-project-78/actions/workflows/ci.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=KryWeak_java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=KryWeak_java-project-78)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=KryWeak_java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=KryWeak_java-project-78)

---

## 📌 Описание

Проект представляет собой библиотеку для **валидации данных на Java**.  
Поддерживаются проверки для строк, чисел, а также для объектов (`Map`).  
Можно задавать схемы валидации, комбинировать правила и проверять данные на соответствие.

---

## 🚀 Установка и запуск

Склонировать репозиторий и выполнить сборку:

```bash
git clone https://github.com/KryWeak/java-project-78.git
cd java-project-78
./gradlew build

Запустить тесты:

./gradlew test

Пример использования:

import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;

import java.util.HashMap;
import java.util.Map;

public class Example {
    public static void main(String[] args) {
        Validator v = new Validator();

        // --- StringSchema ---
        StringSchema stringSchema = v.string().required().minLength(5).contains("hex");
        System.out.println(stringSchema.isValid("hexlet"));   // true
        System.out.println(stringSchema.isValid("hello"));    // false

        // --- NumberSchema ---
        NumberSchema numberSchema = v.number().required().positive().range(5, 10);
        System.out.println(numberSchema.isValid(7));   // true
        System.out.println(numberSchema.isValid(4));   // false
        System.out.println(numberSchema.isValid(-1));  // false

        // --- MapSchema ---
        MapSchema mapSchema = v.map().required().sizeof(2);
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", 42);
        System.out.println(mapSchema.isValid(data)); // true

        // --- MapSchema с shape (вложенная валидация) ---
        MapSchema humanSchema = v.map().required();
        Map<String, BaseSchema<?>> shape = new HashMap<>();
        shape.put("name", v.string().required());
        shape.put("age", v.number().positive());
        humanSchema.shape(shape);

        Map<String, Object> human = new HashMap<>();
        human.put("name", "Alice");
        human.put("age", 25);
        System.out.println(humanSchema.isValid(human)); // true

        human.put("age", -5);
        System.out.println(humanSchema.isValid(human)); // false
    }
}
