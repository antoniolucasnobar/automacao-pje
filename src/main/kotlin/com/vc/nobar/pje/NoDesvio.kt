package com.vc.nobar.pje

import com.nobar.Papeis
import com.vc.nobar.utils.Utils
import com.poiji.bind.Poiji
import com.vc.nobar.interfaces.ItemProcessamento
import com.vc.nobar.interfaces.Acao
import com.vc.nobar.pje.model.Processo
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.util.concurrent.TimeUnit

class NoDesvio(private val driver: WebDriver) : Acao {

    @FindBy(xpath = "//input[@id='inputNumeroProcesso']")
    private val numeroProcesso: WebElement? = null

    @FindBy(xpath = "//input[@name='justificativa']")
    private val justificativa: WebElement? = null

    @FindBy(xpath = "//button[contains(.,'Enviar')]")
    private val botaoEnviarNoDesvio: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun podeExecutar(): Boolean{
        val properties = Utils.getProperties("executarNoDesvio")
        return "Sim".equals(properties, true)
    }

    override fun executar() {
        enviarProcessos()
    }

    fun enviar(processo: String, justificativaUsuario: String) {
        driver.get(Utils.getURL("urlNoDesvio"))
        numeroProcesso?.sendKeys(processo)
        justificativa?.sendKeys(justificativaUsuario)
        driver?.manage()?.timeouts()?.implicitlyWait(1, TimeUnit.SECONDS)
        val isPresent = driver.findElements(By.xpath("//mat-dialog-container//button[contains(.,'OK')]")).size > 0
        driver?.manage()?.timeouts()?.implicitlyWait(3, TimeUnit.SECONDS)
        if (!isPresent) {
            if (! botaoEnviarNoDesvio?.isEnabled!!){
                WebDriverWait(driver, 10).until {
                    botaoEnviarNoDesvio?.isEnabled
                }
            }
            botaoEnviarNoDesvio?.click()

        } else {
            val botaoOk = driver?.findElement(By.xpath("//mat-dialog-container//button[contains(.,'OK')]"))
            botaoOk?.isDisplayed?.let {
                botaoOk?.click()
            }
        }
    }

    fun enviarProcessos() {
        if (podeExecutar()) {
            preparar()
            val justificativaNoDesvio = Utils.getProperties("justificativaNoDesvio")
            Utils.getProcessos().forEach {
                enviar(it, justificativaNoDesvio)
            }
        } else {
            println("nao vai nao!")
        }
    }

    override fun getURL(): String {
        return Utils.getURL("urlLegado");
    }

    override fun processarArquivo(file: File): List<ItemProcessamento> {
        val processos = Poiji.fromExcel(file, Processo::class.java)
//        processos.forEachIndexed{index, element -> println("index = ${index}, element = $element")}
        return processos
    }

    override fun preparar() {
        val homePage = HomePage(driver)
        homePage.login()

        val adm = Utils.getProperties("papelAdm")
        val papeis = Papeis(driver)
        papeis.trocar(adm)
    }

}