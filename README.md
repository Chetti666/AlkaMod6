# AlkaMod6 - Billetera Virtual 📱💳

AlkaMod6 es una aplicación de billetera virtual moderna desarrollada para Android. Permite a los usuarios gestionar su saldo, visualizar un historial detallado de transacciones y realizar envíos de dinero virtuales en un entorno seguro y eficiente.

## 🚀 Características principales
- **Autenticación:** Registro e inicio de sesión de usuarios.
- **Dashboard:** Visualización de saldo actual y perfil de usuario con avatar dinámico.
- **Historial de Transacciones:** Listado dinámico de ingresos y egresos (Offline-first).
- **Transferencias:** Formulario para realizar envíos de dinero virtuales.
- **Soporte Offline:** Persistencia de datos local para acceso sin conexión.

## 🛠️ Stack Tecnológico
- **UI:** Jetpack Compose (Material 3).
- **Arquitectura:** MVVM (Model-View-ViewModel).
- **Networking:** Retrofit + Gson para consumo de MockAPI.
- **Persistencia Local:** Room Database.
- **Navegación:** Navigation Compose.
- **Imágenes:** Coil (Carga asíncrona de imágenes).
- **Lenguaje:** Kotlin + Coroutines & Flow.

---

## 🔑 Credenciales de Acceso (Prueba)
Para probar la aplicación rápidamente sin registrar una nueva cuenta, puedes utilizar las siguientes credenciales preconfiguradas en **MockAPI.io**:

| Campo | Credencial |
| :--- | :--- |
| **Email** | `test@test.com` |
| **Contraseña** | `1234` |

*Nota: Asegúrate de tener conexión a internet para el primer inicio de sesión.*

---

## 📂 Estructura del Proyecto
- `data/`: Modelos de datos, DAOs de Room y servicios de Retrofit.
- `ui/`: Pantallas (Screens) y componentes visuales desarrollados en Compose.
- `viewmodel/`: Lógica de negocio y gestión de estados de la interfaz.
- `navigation/`: Grafo de navegación centralizado de la app.

## 📜 Documentación Adicional
Para más detalles sobre la implementación y decisiones técnicas, consulta el archivo:
👉 [DOCUMENTACION_TECNICA.md](./DOCUMENTACION_TECNICA.md)
