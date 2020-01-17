package com.vc.nobar.interfaces

import java.io.File

interface Acao {
    fun getURL(): String
    fun preparar()
    fun processarArquivo(file: File): List<ItemProcessamento>
    fun executar(item: ItemProcessamento?)
}
