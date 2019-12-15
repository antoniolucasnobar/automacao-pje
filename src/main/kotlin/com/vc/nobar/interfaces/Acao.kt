package com.vc.nobar.interfaces

interface Acao {
    fun getURL(): String
    fun preparar()
    fun executar(processo: String)
}
