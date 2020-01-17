package com.vc.nobar.dejt.paginas

import com.nobar.Utils
import com.vc.nobar.interfaces.ItemProcessamento
import com.vc.nobar.interfaces.Pagina
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

class PaginaInicialDEJT(private val driver: WebDriver): Pagina {

    @FindBy(xpath = "//a[contains(.,'Efetuar Login')]")
    private val botaoTelaLogin: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    override fun executar(item: ItemProcessamento?) {
        WebDriverWait(driver, Utils.TIMEOUT).until {
            botaoTelaLogin?.isEnabled
        }
        botaoTelaLogin?.click()
    }
}