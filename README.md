Desafio Android – Finance Test
==================

Aplicativo de transações financeiras, desenvolvido com base nos princípios do Clean Architecture e seguindo as diretrizes do [Guia para a arquitetura do app](https://developer.android.com/topic/architecture). A estrutura foi projetada para garantir manutenibilidade, escalabilidade e testabilidade, utilizando tecnologias modernas do ecossistema Android para assegurar a alta qualidade de produção.

### Arquitetura

* [Room Database](https://developer.android.com/training/data-storage/room)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Jetpack Compose](https://developer.android.com/jetpack/compose) e
  [Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
* [Jetpack Navigation](https://developer.android.com/jetpack/compose/navigation)
* [Kotlin Coroutines e Flow](https://developer.android.com/kotlin/coroutines)
* [Testes unitários](https://developer.android.com/training/testing/local-tests)


### Decisões
* Utilizei BigDecimal como padrão de dado para valor monetário, que garante a precisão do valor e evita erros de cálculo.
* O aplicativo foi feito em um único módulo, mas sua estrutura está preparada para a modularização.
* Optei por não segmentar a camada de dados em features, devido ao tamanho pequeno do projeto, pois simplifica o uso compartilhado pelas features.
* Devido à falta de API, fiz alimentação de dados por repositório local, mas a estrutura permite facilmente a inclusão e substituição pela camada de dados remotos, inclusive mecanismo de cache.
* A UI define todas as suas características visuais pelo tema, permitindo a alternância de modo claro/escuro, e mantendo sua configuração desacoplada, que viabiliza ajustes pontuais na aparência global do app com impacto mínimo ou mesmo inexistente sobre as telas.
