# Alimentando Fases (AF) - Core Platform

## 1. Visão Geral
O **Alimentando Fases (AF)** é um ecossistema multiplataforma de alta performance dedicado ao setor de nutrição. Desenvolvido com **Kotlin Multiplatform (KMP)**, o projeto integra aplicações para Android, iOS, Web (Wasm), Desktop (JVM) e Desktop (JVM) a partir de uma base de código compartilhada, priorizando segurança, resiliência e uma interface premium baseada no conceito *Dark Luxury*.

## 2. Pilares Arquiteturais
O projeto segue rigorosamente padrões de engenharia de software para garantir escalabilidade e manutenibilidade:
* **Clean Architecture:** Separação clara de responsabilidades entre as camadas de apresentação, domínio e dados.
* **Princípios SOLID:** Baixo acoplamento e alta coesão em todos os módulos.
* **Resiliência:** Tratamento de erros tipado e fluxos de dados imutáveis.
* **Interface Dark Luxury:** UI/UX sofisticada implementada com **Compose Multiplatform**.

## 3. Estrutura do Projeto
A organização dos módulos reflete a estratégia de compartilhamento de código do ecossistema:

* **`/composeApp`**: Módulo central contendo a lógica de UI e recursos compartilhados.
    * `commonMain`: Lógica de interface, navegação e componentes comuns.
    * `androidMain`, `iosMain`, `wasmJsMain`, `jvmMain`: Implementações e entry points específicos por plataforma.
* **`/gradle`**: Gerenciamento centralizado de dependências via **Version Catalog** (`libs.versions.toml`).
* **Recursos de UI**: Sistema de temas, cores, tipografia e assets (como ebooks e ícones de comunidade) centralizados no módulo compartilhado.

## 4. Stack Tecnológica (Versões Core)
As definições técnicas são gerenciadas de forma estrita para evitar conflitos de runtime:
* **Linguagem:** Kotlin `2.2.21`.
* **Framework UI:** Compose Multiplatform `1.9.3`.
* **Build System:** Gradle `8.13.2`.
* **Android SDK:** Compile/Target SDK `36`, Min SDK `24`.
* **Processamento Assíncrono:** Kotlinx Coroutines `1.10.2`.

## 5. Fluxo de Navegação e Telas
O App implementa uma jornada de usuário completa:
* **Onboarding:** SplashScreen, Login e Criação de Conta.
* **Cadastro:** Fluxo de nome, data de nascimento, foto de perfil e verificação de e-mail.
* **Core:** HomeScreen, CommunityScreen, RecipeScreen e ProfileScreen.

## 6. Governança e Qualidade
* **Convenção de Commits:** Utilização obrigatória de *Conventional Commits* (`feat:`, `fix:`, `chore:`, etc.).
* **Gerenciamento de Dependências:** Uso exclusivo do `libs.versions.toml` para garantir tipagem estrita e fonte única de verdade.
* **Documentação:** Foco em decisões arquiteturais de alto nível e contratos de interface.

## 7. Configuração de Ambiente
Para compilar o projeto em todas as plataformas:
1.  Certifique-se de ter o **JDK 17+** instalado.
2.  Utilize o **Android Studio** (versão Ladybug ou superior) com o plugin Kotlin Multiplatform.
3.  Para iOS, é necessário o **Xcode** configurado no macOS.
4.  Execute `./gradlew sync` para sincronizar o catálogo de dependências.
