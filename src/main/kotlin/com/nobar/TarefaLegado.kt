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




class TarefaLegado(private val driver: WebDriver) {

    fun mover(processo: String, tarefaDestino: String) {
        val wait = WebDriverWait(driver, 10)
//        wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']"))
//        )
        val element = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='$tarefaDestino']"))
        )
        element.click()
    }
}