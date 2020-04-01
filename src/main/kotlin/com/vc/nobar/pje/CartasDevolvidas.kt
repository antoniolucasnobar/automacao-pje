package com.vc.nobar.pje

import com.nobar.Papeis
import com.nobar.Tarefa
import com.nobar.TarefaLegado
import com.vc.nobar.utils.MySelenium
import com.vc.nobar.utils.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CartasDevolvidas(private val driver: WebDriver) {



    fun executar(){
        HomePage(driver).login()
//        Papeis(driver).trocar("VARA DO TRABALHO DE ALEGRETE - Diretor de Secretaria")
        while(true) {
            val trocou = Papeis(driver).trocarProximoVaraOuPosto(false)
            if (!trocou) {
                break
            }
            while (true) {
                val painel = PainelGlobal(driver)
                painel.abrirAgrupamento("Arquivados")
                filtrar("Fase processual", listOf("Conhecimento", "Execução"))
                filtrar("Tarefa", listOf("Cartas"))

                if (MySelenium.check(driver)) {
                    break
                }

                val abriu = painel.abrirTarefaPrimeiroProcesso("Cartas devolvidas")

                if (!abriu) {
                    break
                }
                val tarefasLegado = TarefaLegado(driver)
                tarefasLegado.mover("Prosseguir com a carta")

                val tarefas = Tarefa(driver)
                tarefas.mover("Arquivar o processo")
                MySelenium.buscar(driver, By.xpath("//h1[contains(@class,'titulo-tarefa')][contains(.,'Tarefa: Escolher tipo de arquivamento')]"))

                tarefas.mover("Arquivar carta")
                MySelenium.buscar(driver, By.xpath("//h1[contains(@class,'titulo-tarefa')][contains(.,'Tarefa: Arquivo')]"))
            }
            Utils.avanco++
            Utils.writeAvanco()
        }

    }

    fun filtrar(nomeFiltro: String, opcoes: List<String>){
        MySelenium.abrirFiltro(driver, nomeFiltro)
        opcoes.forEach{o -> MySelenium.clicarNaOpcaoDoFiltro(driver, o)}
        MySelenium.filtrar(driver)
    }


}