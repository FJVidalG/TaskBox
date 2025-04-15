# TaskBox 📝 - Gestor de Tareas con Jetpack Compose

[![Estado](https://img.shields.io/badge/Estado-🚧_En_Desarrollo-orange)](https://github.com/tu-usuario/taskbox)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose)

Aplicación para gestión de tareas con listas personalizables y prioridades. **En desarrollo activo**.

## 📌 Características Principales
| Módulo           | Funcionalidades Implementadas                |
|------------------|----------------------------------------------|
| **Listas**       | - Creación con nombre/color <br> - Edición   |
| **Tareas**       | - Prioridades (Alta/Media/Baja) <br> - Fechas <br> - Etiquetas personalizadas |
| **UI/UX**        | - Diseño Material 3 <br> - Preview dinámicos |

## 🛠️ Tecnologías
**Core**
- Kotlin 1.9
- Jetpack Compose (v2023.08.00)
- Material 3 Design

**Arquitectura**
- Patrón Repository (`TaskRepository`)
- Componentes `@Composable` reutilizables

## 📂 Estructura
```text
src/main/java/com/fjvid/taskbox/
├── data/            # Modelos y repositorios
└── ui/              # Pantallas y componentes
    ├── components/  # PrioritySelector, DatePickerField
    └── screens/     # TaskFormScreen, ListsScreen
    └── themes/      # Elementos visuales
```

## 🚧 Roadmap (Próximos Pasos)
1. **Almacenamiento Persistente**  
   - Room Database (SQLite)
   - Sync con Firebase
2. **Navegación Avanzada**  
   - BottomNavigationBar
   - Pantalla de estadísticas
3. **Testing**  
   - Pruebas de integración
   - CI/CD con GitHub Actions

## 🖼️ Capturas de Pantalla
| Vista de Listas | Creación de Tareas |
|-----------------|--------------------|
| ![Lists](url_img)| ![Task](url_img)   |

## 👤 Autor

**Francisco Jesús Vidal García**  
[![Email](https://img.shields.io/badge/📧_Email-fjvidalgarcia%40gmail.com-%23007EC6?style=flat&logo=gmail&logoColor=white)](mailto:fjvidalgarcia@gmail.com)  
[![LinkedIn](https://img.shields.io/badge/🔗_LinkedIn-Francisco_Vidal-%230A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/francisco-jes%C3%BAs-vidal-garc%C3%ADa-174189336/)

