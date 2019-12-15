package com.vc.nobar.dejt.paginas

import com.nobar.Utils
import com.vc.nobar.interfaces.Pagina
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

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