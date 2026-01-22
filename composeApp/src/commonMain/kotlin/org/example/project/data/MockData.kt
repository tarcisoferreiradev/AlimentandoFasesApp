package org.example.project.data

/**
 * Objeto que fornece dados de exemplo (mock) para a aplicação.
 * Centralizar os mocks aqui facilita o desenvolvimento e os testes da UI.
 */
object MockData {
    /**
     * Lista de ações de exemplo com temática natalina para ser exibida na ActionsSection.
     */
    val actionsData = listOf(
        ActionItem(
            date = "QUINTA, 23 OUT 2025",
            title = "Ação das crianças",
            description = "Nossa Ação de Nutrição Infantil foi um sucesso! Levamos aprendizado e diversão para a sala de aula com atividades lúdicas, incluindo um \"teste cego\" de paladar. Foi um dia incrível realizado por uma equipe fantástica!",
            location = "Escola Municipal Sociólogo Gilberto Freyre",
            imageRes = "acao-crianca", // Corresponde a acao-crianca.png
            linkUrl = "#"
        ),
        ActionItem(
            date = "SEG/QUA, 03 E 05 NOV 2025",
            title = "Ação dos Adultos",
            description = "Nossa Ação Adulto promoveu saúde no trabalho no CDC | CENTRO DE DESENVOLVimento E CIDADANIA. Fizemos atendimentos nutricionais e uma palestra sobre alimentação e estresse. Um dia incrível com nossa equipe!",
            location = "CDC",
            imageRes = "acao-adulto", // Corresponde a acao-adulto.png
            linkUrl = "#"
        ),
        ActionItem(
            date = "QUA/QUI 23 E 24 OUT 2025",
            title = "Ação dos idosos",
            description = "Um bate-papo com especialistas sobre Sarcopenia, Hidratação e a importância das Proteínas na Terceira Idade.",
            location = "Parque Santana",
            imageRes = "acao-idoso", // Corresponde a acao-idoso.png
            linkUrl = "#"
        )
    )
}