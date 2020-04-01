package com.nobar

import com.vc.nobar.utils.MySelenium
import com.vc.nobar.utils.Utils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class Papeis(private val driver: WebDriver) {

    @FindBy(xpath = "//button[contains(@class,'perfil-button')]")
    private val botaoTrocarPapel: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun trocar(papel: String) {
        /**
         * troca para o painel global, pois o gim demooooora muito pra carregar, e isso atrapalha os testes.
         */
//        val p = driver?.findElement(By.xpath("//button[contains(@name,'Painel Global')]"))
//        p?.click()
        botaoTrocarPapel?.click()
        val p2 = driver.findElement(By.xpath("//button[@aria-label='$papel']"))
        p2?.click()

    }

    fun trocarProximoVaraOuPosto(incluirPosto: Boolean): Boolean{
        botaoTrocarPapel?.click()
        val xpathExpr = "//button[contains(@aria-label,'VARA') " + if (incluirPosto) "or contains(@aria-label,'POSTO')" else "" + "]"
        val xpath = By.xpath(xpathExpr)
        val p2 = MySelenium.buscarVarios(driver, xpath, null)
        if (p2.size > Utils.avanco) {
            p2[Utils.avanco].click()
            return true
        } else {
            return false
        }


    }
}