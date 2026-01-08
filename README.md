# 🥗 Alimentando Fases (AF) | Core Platform
> **Ecossistema Multiplataforma de Alta Performance para Nutrição de Precisão.**

---

## 🏛️ Arquitetura de Missão Crítica
O projeto é alicerçado em pilares de engenharia de software de elite, garantindo resiliência e escalabilidade industrial:

* **🧼 Clean Architecture:** Separação rigorosa de responsabilidades entre as camadas de **Domain**, **Data** e **Presentation**.
* **🛡️ SOLID & Resiliência:** Código focado em baixo acoplamento, imutabilidade e tratamento de erros tipado para evitar falhas em runtime.
* **✨ Interface Dark Luxury:** Experiência de usuário premium e minimalista desenvolvida com **Compose Multiplatform**.
* **🔐 Segurança Business-First:** Lógica sensível e regras de negócio estruturadas para ofuscação e futura compilação em **WebAssembly (Wasm)**.

---

## 🏗️ Estrutura do Ecossistema
A base de código compartilhada (Shared Core) alimenta todas as frentes do ecossistema de forma centralizada:

| Módulo | Finalidade Técnica | Tecnologias Chave |
| :--- | :--- | :--- |
| **`composeApp`** | Core de UI e Lógica de Apresentação | Compose Multiplatform, NavHost |
| **`commonMain`** | Regras de Negócio e Contratos de Dados | Pure Kotlin, Coroutines |
| **`wasmJsMain`** | Interface Web de Alta Performance | Kotlin/Wasm |
| **`androidApp`** | Entry point Nativo Android | Android SDK 36, Kotlin 2.2.21 |
| **`iosApp`** | Entry point Nativo iOS | SwiftUI, MainViewController |

---

## 🛠️ Stack Tecnológica (Single Source of Truth)
Gerenciado via **Version Catalog** para garantir tipagem estrita e consistência em todas as dependências:

* **Linguagem:** Kotlin `2.2.21`
* **UI Framework:** Compose Multiplatform `1.9.3`
* **Build System:** Gradle `8.13.2`
* **Assincronismo:** Coroutines `1.10.2`
* **Navegação:** Sistema centralizado em `Navigation.kt` com suporte a fluxos complexos.

---

## 📱 Domínios e Fluxos de Navegação
O AF gerencia jornadas de usuário sofisticadas com estados persistentes:

* **⚡ Onboarding & Auth:** `SplashScreen` ➔ `LoginScreen` ➔ `VerifyEmail`.
* **👤 Perfil & Autoridade:** Cadastro biométrico e sistema de verificação de nutricionistas.
* **🍳 Core Experience:** `HomeScreen`, `CommunityScreen` e engine de receitas.

---

## 📜 Governança de Engenharia
* **Commits:** Padrão *Conventional Commits* (`feat:`, `fix:`, `refactor:`).
* **Qualidade:** Revisão de código focada em complexidade algorítmica e contratos de interface.
* **Versionamento:** Gestão centralizada de artefatos para deploy multi-ambiente.

---
**Propriedade intelectual protegida. Foco total em excelência técnica e segurança.**
