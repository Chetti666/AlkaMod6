# Roadmap del Proyecto: AlkaMod6 - Billetera Virtual ✅ FINALIZADO

## 1. Configuración del Proyecto y Arquitectura ✅
*   **Definición de Dependencias:** Configurado con Retrofit, Room, Navigation, y Coil.
*   **Estructura de Paquetes (MVVM):** Organizado en `data`, `model`, `ui` y `viewmodel`.

## 2. Capa de Datos (Model & Data) ✅
*   **Room (Local):** Entidades `TransactionEntity` y `UserEntity` con DAOs operativos.
*   **Retrofit (Remoto):** Interfaz `WalletApi` para consumo de servicios REST.
*   **Repositorio:** `WalletRepository` implementado como fuente única de verdad.

## 3. Capa de Lógica (ViewModel) ✅
*   **AuthViewModel:** Gestión de sesiones y registro.
*   **TransactionViewModel:** Gestión de historial y saldo.

## 4. Capa de Interfaz de Usuario (View) ✅
*   **Pantallas:**
    *   Dashboard (Saldo y Perfil con imagen).
    *   Historial (Lista dinámica de transacciones).
    *   Envío de dinero (Formulario de transacciones).
    *   Login y Registro.
*   **Navegación:** `NavGraph` configurado con Compose Navigation.

## 5. Documentación y Entrega ✅
*   **Documentación Técnica:** Creado `DOCUMENTACION_TECNICA.md`.
*   **Gestión de Errores:** Estructura de manejo de excepciones implementada.
