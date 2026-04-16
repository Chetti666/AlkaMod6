# Documentación Técnica - AlkaMod6 Billetera Virtual

## 1. Diseño de Arquitectura
La aplicación está construida siguiendo el patrón de arquitectura **MVVM (Model-View-ViewModel)**, lo que garantiza una separación clara de responsabilidades y facilita las pruebas unitarias.

### Capas de la Aplicación:
- **View (Capa de UI):** Desarrollada con **Jetpack Compose**. Se encarga de mostrar los datos al usuario y capturar eventos. No contiene lógica de negocio.
- **ViewModel (Capa de Lógica):** Actúa como puente entre la UI y el Repositorio. Gestiona el estado de la UI utilizando `StateFlow` y maneja operaciones asíncronas con `Coroutines`.
- **Model (Capa de Datos):** Incluye el Repositorio, la base de datos local (Room) y el servicio de API (Retrofit).

---

## 2. Estructura del Código
El proyecto se organiza en los siguientes paquetes bajo `com.example.alkamod6`:
- `data/local/`: Contiene la configuración de **Room** (Entities, DAOs y AppDatabase).
- `data/remote/`: Contiene la interfaz de **Retrofit** para el consumo de la API REST.
- `data/model/`: Objetos de transferencia de datos (DTOs).
- `data/repository/`: Clase `WalletRepository` que implementa la lógica de "Fuente Única de Verdad".
- `ui/screens/`: Pantallas de la aplicación (Login, Register, Dashboard, SendMoney).
- `ui/navigation/`: Configuración del flujo de navegación con **Compose Navigation**.
- `viewmodel/`: Lógica de negocio y gestión de estados.

---

## 3. Implementación de Tecnologías Clave

### Gestión de Base de Datos con Room
Se utiliza Room para el almacenamiento persistente y acceso sin conexión (Offline support).
- **Entidades:** `UserEntity` para el perfil y `TransactionEntity` para el historial.
- **DAO:** `WalletDao` define las consultas SQL mediante anotaciones, permitiendo observar los datos en tiempo real usando `Flow`.

### Integración de API con Retrofit
Retrofit gestiona las comunicaciones HTTP con el backend.
- Se configuró un convertidor de **Gson** para el mapeo automático de JSON a objetos Kotlin.
- Las solicitudes se realizan de forma asíncrona mediante funciones `suspend` dentro de las Corrutinas de Kotlin.

### Carga de Imágenes con Coil
Se implementó **Coil** (en lugar de Picasso por ser nativo de Compose) para la carga de avatares de usuario, optimizando el uso de memoria y la caché de imágenes.

---

## 4. Responsabilidades de Clases Principales (MVVM)

| Clase | Responsabilidad |
| :--- | :--- |
| **MainActivity** | Punto de entrada. Inicializa Room, Retrofit y configura el NavHost. |
| **WalletRepository** | Decide si los datos provienen de la red o de la base de datos local. |
| **AuthViewModel** | Gestiona el login, registro y la sesión activa del usuario. |
| **TransactionViewModel** | Expone el balance y la lista de transacciones a la UI. |
| **DashboardScreen** | Renderiza el balance y el historial de transacciones. |
| **WalletApi** | Interfaz que define los endpoints de la API REST. |

---

## 5. Decisiones de Diseño
- **Jetpack Compose:** Elegido para una UI declarativa y moderna.
- **KSP (Kotlin Symbol Processing):** Usado para procesar las anotaciones de Room de manera más rápida que Kapt.
- **Offline-First:** La app siempre muestra primero los datos de Room para garantizar una respuesta inmediata al usuario.
