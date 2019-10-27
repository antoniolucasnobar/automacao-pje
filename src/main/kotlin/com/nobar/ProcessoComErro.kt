package com.nobar

import kotlin.reflect.KClass

data class ProcessoComErro (val processo: String, val acao : KClass<out Acao>, val erro: String) {

}
