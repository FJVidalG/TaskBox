# TaskBox 📝 - Gestor de Tareas con Jetpack Compose

[![Estado](https://img.shields.io/badge/Estado-✅_Finalizado-brightgreen)](https://github.com/tu-usuario/taskbox)
[![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Licencia](https://img.shields.io/badge/Licencia-MIT-yellow)](https://opensource.org/licenses/MIT)

Aplicación para gestión de tareas con listas personalizables y prioridades.

## 🔧 Tecnologías y Arquitectura

| Componente           | Tecnología/Librería                                                                 | Función                                                                 |
|----------------------|-------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| Lenguaje principal   | **Kotlin 1.9**                                                                      | Desarrollo nativo                              |
| Arquitectura         | **MVVM**                                                                            | Separación limpia View - ViewModel - Model                             |
| UI Toolkit           | **Jetpack Compose**                                                | Construcción declarativa de interfaces                                 |
| Gestión de estado    | **ViewModel + StateFlow (Próxima implementación)**                                                           | Flujo reactivo de datos y estado compartido                            |
| Diseño               | **Material 3**                                                                      | Sistema de diseño moderno con theming dinámico                         |
| Navegación           | **Compose Navigation 2.8.0 (Próxima implementación)**                                                        | Gestión de flujos con type-safe arguments                              |
| Persistencia         | **Room**                                                   | Almacenamiento local con SQLite y DAOs                                 |
| Dependencias         | **Gradle 8.1.1**                                                                    | Builds modularizados con Convention Plugins                            |


## 📌 Características Principales
| Módulo           | Funcionalidades Implementadas                |
|------------------|----------------------------------------------|
| **Listas**       | - Creación con nombre/color <br> - Edición   |
| **Tareas**       | - Campos obligatorios y opcionales <br> - Descripciones detalladas (multilínea) <br> - Prioridades (Alta/Media/Baja) <br> - Fechas configurables <br> - Etiquetas personalizadas |
| **UI/UX**        | - Diseño Material 3 <br> - Preview dinámicos <br> - Validación en tiempo real |


## 🚀 Características Finales
- Gestión de listas y tareas con prioridades
- Base de datos Room
- Notificaciones
- Navegación
- Diseño Material 3
- Arquitectura MVVM con Hilt

## 🖼️ Capturas de Pantalla
| Vista de Listas | Creación de Tareas |
|-----------------|--------------------|
| ![Lists](url_img)| ![Task](url_img)   |

## 📱 Compatibilidad y Requisitos

| Característica          | Detalle                                                                 |
|-------------------------|-------------------------------------------------------------------------|
| **Versión mínima**      | Android 14 (API 34)                                                    |
| **Versión objetivo**    | Android 14+ (API 35)                                                   |
| **Dispositivos**        | Teléfonos y tablets con resolución HD+ (720x1280) o superior           |
| **Orientación**         | Soporte completo para Portrait y Landscape                             |
| **Densidad de pantalla**| Optimizado para mdpi (160dpi) a xxxhdpi (640dpi)                       |
| **RAM recomendada**     | Mínimo 2GB (4GB para uso óptimo con listas complejas)         

## 👤 Autor

**Francisco Jesús Vidal García**  
[![Email](https://img.shields.io/badge/📧_Email-fjvidalgarcia%40gmail.com-%23007EC6?style=flat&logo=gmail&logoColor=white)](mailto:fjvidalgarcia@gmail.com)  
[![LinkedIn](https://img.shields.io/badge/🔗_LinkedIn-Francisco_Vidal-%230A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/francisco-jes%C3%BAs-vidal-garc%C3%ADa-174189336/)
