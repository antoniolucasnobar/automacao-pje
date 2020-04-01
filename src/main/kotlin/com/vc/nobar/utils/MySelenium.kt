package com.vc.nobar.utils

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS


object MySelenium {

    fun clicar(driver: WebDriver, botao: WebElement?){
        WebDriverWait(driver, Utils.TIMEOUT).until {
            botao?.isEnabled
        }
        botao?.click()
    }

    fun invisibilityOfElementLocated(locator: By): ExpectedCondition<Boolean?>? {
        return object : ExpectedCondition<Boolean?> {
            override fun apply(driver: WebDriver?): Boolean? {
                return try {
                    !driver!!.findElement(locator).isDisplayed
                } catch (var3: NoSuchElementException) {
                    true
                } catch (var4: StaleElementReferenceException) {
                    true
                }
            }

            override fun toString(): String {
                return "element to no longer be visible: $locator"
            }
        }
    }

    fun buscarVarios(driver: WebDriver, locator: By, element: WebElement?): MutableList<WebElement> {
        val searchContext: SearchContext = element ?: driver;

        WebDriverWait(driver, Utils.TIMEOUT).until {
            presenceOfElementLocated(searchContext, locator)
        }
        return searchContext.findElements(locator)
    }

    fun buscar(driver: WebDriver, locator: By): WebElement {
        WebDriverWait(driver, Utils.TIMEOUT).until {
            presenceOfElementLocated(driver, locator)
        }
        return driver.findElement(locator)
    }
    //https://stackoverflow.com/questions/5709204/random-element-is-no-longer-attached-to-the-dom-staleelementreferenceexception
    private fun presenceOfElementLocated(searchContext: SearchContext, locator: By): Function<WebElement>? {
        return object : Function<WebElement> {
            fun apply(searchContext: SearchContext): WebElement? {
                return searchContext.findElement(locator)
            }
        }
    }

    fun abrirFiltro(driver: WebDriver, nomeFiltro: String){
        val xpathFiltro = By.xpath("//mat-select[.//span[contains(text(),'$nomeFiltro')]]")
        val filtro = MySelenium.buscar(driver,xpathFiltro)
        filtro.click()
    }

    fun clicarNaOpcaoDoFiltro(driver: WebDriver,opcao: String){
        val xpath = By.xpath("//mat-option[.//span[contains(text(),'$opcao')]]/mat-pseudo-checkbox")
        val filtros = MySelenium.buscarVarios(driver, xpath, null)
        filtros.forEach{f -> f.click()}
    }

    fun filtrar(driver: WebDriver){
        val xpath = By.xpath("//button[@aria-label='Filtrar']")
        val filtro = MySelenium.buscar(driver,xpath)
        filtro.click()
    }

    fun check(driver: WebDriver): Boolean {

        val wait: Wait<*> = FluentWait<Any?>(driver)
            .withTimeout(15, TimeUnit.SECONDS)
            .pollingEvery(3, TimeUnit.SECONDS)
            .ignoring(NoSuchElementException::class.java)


        val xpathExpr = "//span[contains(text(), 'Não há processos neste tema.')]"

        wait.until{
            presenceOfElementLocated(driver, By.xpath(xpathExpr))
        }


        val span = driver.findElements(By.xpath(xpathExpr))
        return span.size > 0
    }
}