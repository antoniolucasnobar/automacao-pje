package com.vc.nobar.pje

import com.nobar.*
import com.vc.nobar.interfaces.ItemProcessamento
import com.vc.nobar.interfaces.Acao
import org.openqa.selenium.WebDriver
import java.io.File


class Arquivamento(private val driver: WebDriver) : Acao {
    override fun getURL(): String {
        return Utils.getURL("urlLegado");
    }

    override fun processarArquivo(file: File): List<ItemProcessamento> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun preparar() {
        val papel = Utils.getProperties("papel")
        val papeis = Papeis(driver)
        papeis.trocar(papel)
    }

    override fun executar(item: ItemProcessamento?) {
        arquivar(item?.getItem().toString())
    }

    fun arquivar(processo: String) {
        val painelGlobal = PainelGlobal(driver)
        painelGlobal.abrirTarefa(processo)

        val tarefas = Tarefa(driver)
        tarefas.mover(processo,"Análise")
        tarefas.mover(processo,"Arquivar o processo")

        val tarefasLegado = TarefaLegado(driver)
        tarefasLegado.mover(processo, "Arquivar definitivamente")

//        val tarefa = Utils.getProperties("tarefaArquivamento")
//        val wait = WebDriverWait(driver, 10)
//        wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']"))
//        )
//        val element = wait.until(
//            ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='$tarefa']"))
//        )
//        element.click()
    }


    fun podeExecutar(): Boolean{
        val properties = Utils.getProperties("executarArquivamento")
        return "Sim".equals(properties, true)
    }


    fun arquivarProcessos() {
        if (podeExecutar()) {
            preparar()
            Utils.getProcessos().forEach {
                arquivar(it)
            }
        } else {
            println("nao vai tambem nao")
        }
    }
}