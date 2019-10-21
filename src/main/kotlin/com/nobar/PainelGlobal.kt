package com.nobar

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
        WebDriverWait(driver, 10).until {
            botaoAbrirTarefa?.isEnabled
        }
        botaoAbrirTarefa?.click()
    }
}