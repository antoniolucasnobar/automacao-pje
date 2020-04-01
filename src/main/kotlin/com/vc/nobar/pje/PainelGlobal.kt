package com.vc.nobar.pje

import com.vc.nobar.utils.MySelenium
import com.vc.nobar.utils.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

class PainelGlobal(private val driver: WebDriver) {

    @FindBy(xpath = "//input[@id='inputNumeroProcesso']")
    private val numeroProcesso: WebElement? = null

    @FindBy(xpath = "//a[@mattooltip='Abrir tarefa']")
    private val botaoAbrirTarefa: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun irParaPainel(){
        driver.get(Utils.getURL("urlPainelGlobal"))
    }

    fun abrirTarefa(processo: String) {
        irParaPainel()
        numeroProcesso?.sendKeys(processo)
        abrirTarefaPrimeiroProcesso("")
    }

    fun abrirTarefaPrimeiroProcesso(nomeTarefa: String) : Boolean{
        val xpath = By.xpath("//a[@mattooltip='Abrir tarefa' and contains(text(), '$nomeTarefa')][.//descendant::*[not (contains(text(), 'Arquivados'))]]")
        val tarefas = MySelenium.buscarVarios(driver,xpath, null)

        if (tarefas.isNotEmpty()) {
            tarefas[0].click()
            return true
        } else {
            return false
        }

    }


    fun abrirAgrupamento(nome: String){
        irParaPainel()
        val xpath = By.xpath("//mat-card-title[span[contains(text(), '$nome')]]")
        val agrupamento = MySelenium.buscar(driver,xpath)
        agrupamento.click()
    }
}