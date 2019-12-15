package com.vc.nobar.acao

interface Acao {
    fun getURL(): String
    fun preparar()
    fun executar(processo: String)
}
