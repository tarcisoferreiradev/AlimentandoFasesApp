package org.example.project.data

/**
 * Data class que representa os dados de um card de ação.
 * Esta classe serve como um contrato de dados para os componentes de UI.
 *
 * @property date A data do evento.
 * @property title O título da ação.
 * @property description Uma breve descrição da ação.
 * @property location O local onde a ação ocorreu.
 * @property imageRes O nome do recurso de imagem (sem extensão), usado para carregar o drawable.
 * @property linkUrl A URL opcional para a ação do botão "Veja aqui".
 */
data class ActionItem(
    val date: String,
    val title: String,
    val description: String,
    val location: String,
    val imageRes: String,
    val linkUrl: String? = null
)
