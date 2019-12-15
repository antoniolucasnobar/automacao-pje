package com.vc.nobar.acao.dejt.paginas

import com.nobar.Papeis
import com.nobar.Utils
import com.vc.nobar.Pagina
import com.vc.nobar.acao.Acao
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

class LoginDEJT(private val driver: WebDriver): Pagina {

    @FindBy(xpath = "//input[@class='loginTexto'][contains(@id,'username')]")
    private val login: WebElement? = null

    @FindBy(xpath = "//input[@class='loginTexto'][contains(@id,'password')]")
    private val password: WebElement? = null

    @FindBy(xpath = "//input[@name='btnLogin']")
    private val botaoTelaLogin: WebElement? = null

    private val loginTxt: String
    private val passwordTxt: String


    init {
        PageFactory.initElements(driver, this)
        loginTxt = Utils.getProperties("loginDEJT")
        passwordTxt = Utils.getProperties("passwordDEJT")
    }

    override fun executar() {
        login?.sendKeys(loginTxt)
        password?.sendKeys(passwordTxt)

        WebDriverWait(driver, 10).until {
            botaoTelaLogin?.isEnabled
        }
        botaoTelaLogin?.click()
    }
}