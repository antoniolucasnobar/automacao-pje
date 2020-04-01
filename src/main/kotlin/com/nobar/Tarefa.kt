package com.nobar

import com.vc.nobar.utils.MySelenium
import com.vc.nobar.utils.Utils
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
import java.lang.Exception


class Tarefa(private val driver: WebDriver) {

    fun mover(tarefaDestino: String) {
        try{
            desbloquearProcesso()
            val wait = WebDriverWait(driver, Utils.TIMEOUT, 2500L)
            wait.until{
                ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']"))
            }

            val element = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='$tarefaDestino']"))
            )
            MySelenium.clicar(driver, element)
            //confirma que transitou
//            MySelenium.buscar(driver, By.xpath("//h1[contains(@class,'titulo-tarefa')][contains(.,'Tarefa: $tarefaDestino')]"))
        } catch (e: Exception){
            System.err.println(e.message)
            Thread.sleep(3000)
            mover(tarefaDestino)
        }
    }

    private fun desbloquearProcesso() {
        // verifica se o processo esta bloqueado
        driver.manage()?.timeouts()?.implicitlyWait(1, TimeUnit.SECONDS)
        val isPresent = driver.findElements(By.xpath("//mat-dialog-container//button[contains(.,'Sim')]")).size > 0
        driver.manage()?.timeouts()?.implicitlyWait(5, TimeUnit.SECONDS)
        if (isPresent) {
            val botaoDesbloqueioTarefa =
                driver.findElement(By.xpath("//mat-dialog-container//button[contains(.,'Sim')]"))
            botaoDesbloqueioTarefa?.isDisplayed?.let {
                botaoDesbloqueioTarefa.click()
            }
        }
    }
}