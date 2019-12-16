package com.vc.nobar.interfaces

import java.io.File

interface Acao {
    fun getURL(): String
    fun processarArquivo(file: File)
    fun preparar()
    fun executar(processo: String)
}
