package com.nobar

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.SystemColor.window
import org.openqa.selenium.JavascriptExecutor
import java.util.*
import java.util.concurrent.TimeUnit
import org.openqa.selenium.support.ui.ExpectedConditions




class Arquivamento(private val driver: WebDriver) : Acao {

    override fun preparar() {
        val papel = Utils.getProperties("papel")
        val papeis = Papeis(driver)
        papeis.trocar(papel)
    }

    override fun executar(processo: String) {
        arquivar(processo)
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