# 🍽️ Recetas App

## 📌 Descripción
Recetas App es una aplicación desarrollada en Android con Jetpack Compose, que permite a los usuarios explorar y gestionar recetas de cocina de manera intuitiva y eficiente. La aplicación sigue buenas prácticas de arquitectura y desarrollo para garantizar escalabilidad, mantenibilidad y testabilidad.

---

## 🏛️ Solución de Arquitectura
Este proyecto implementa **Clean Architecture** con una estructura simplificada, omitiendo la capa de Dominio debido al tamaño reducido de la aplicación. La arquitectura se divide en:

- **Capa de Datos (Data Layer):** Contiene la lógica de acceso a los datos, los cuales se encuentran en un api mock de postman.
- **Capa de Presentación (Presentation Layer):** Implementada con Jetpack Compose y basada en el patrón MVVM (Model-View-ViewModel) para una mejor separación de responsabilidades.

```plaintext
📂 app/
 ├── 📂 data/  # Acceso a datos (repositorios, fuentes de datos, modelos de datos)
 ├── 📂 ui/    # Composables y manejo de UI con ViewModels
 ├── 📂 di/    # Inyección de dependencias con Hilt
 ├── 📂 tests/ # Pruebas unitarias y de UI
```

---

## ✅ Buenas Prácticas Implementadas
- Uso de **Jetpack Compose** para una UI declarativa y reactiva.
- Inyección de dependencias con **Hilt** para una gestión eficiente de objetos.
- **State Hoisting** y separación de responsabilidades en los Composables.
- Gestión de estado con **StateFlow**.
- Manejo de errores con **sealed classes** y **Result Wrappers**.

---

## 📐 Patrones de Diseño Aplicados
- **MVVM (Model-View-ViewModel):** Para desacoplar la UI de la lógica de negocio.
- **Repository Pattern:** Para gestionar el acceso a los datos de manera centralizada.
- **Use Cases (aunque omitidos por simplicidad):** Considerados para separar la lógica de negocio si el proyecto crece.
- **Adapter Pattern:** Aplicado en algunos Composables que gestionan listas dinámicas.

---

## 🧪 Pruebas Automatizadas
Se han implementado pruebas para garantizar la calidad del código:

### 🔹 Pruebas Unitarias
- Pruebas de lógica de negocio en ViewModels con **JUnit Jupiter**, **Mockk**, y **Turbine** para testear flows y coroutines.
- Validación de transformación de datos en los repositorios.

### 🔹 Pruebas de Composables
- Uso de **Compose UI Tests** para verificar la correcta renderización de componentes.
- Pruebas de navegación y eventos con **Compose Testing Library**.

```kotlin
@Test
fun testHomeScreen_SuccessState() {
    val recipes = listOf(
        Recipe("1", "", "Ceviche", 0.0, 0.0),
        Recipe("2", "", "Ocopa", 0.0, 0.0)
    )

    composeTestRule.setContent {
        HomeScreen(
            onRecipeClicked = {},
            recipes = Resource.Success(recipes)
        )
    }

    composeTestRule.onNodeWithText("Ceviche").assertIsDisplayed()
    composeTestRule.onNodeWithText("Ocopa").assertIsDisplayed()
}
```

---

## 🚀 Novedades de la Plataforma Implementadas
- Uso de **Material 3** para un diseño moderno y adaptable.
- **Coroutines + Flow** para operaciones asíncronas eficientes.
- **Jetpack Navigation** para manejo de rutas de pantalla de manera declarativa.

---

## 🎯 Conclusión
Este proyecto demuestra la aplicación de buenas prácticas en desarrollo Android con un enfoque moderno en arquitectura, pruebas y patrones de diseño. A medida que crezca, se pueden incluir nuevas optimizaciones y la capa de dominio si es necesario.

📌 ¡Gracias por revisar este README! Si tienes sugerencias o mejoras, no dudes en contribuir. 😊