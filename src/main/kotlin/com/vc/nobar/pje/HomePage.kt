package com.vc.nobar.pje

import com.vc.nobar.utils.Utils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class HomePage(private val driver: WebDriver) {

    @FindBy(xpath = "//input[@name='username']")
    private val userName: WebElement? = null

    @FindBy(xpath = "//input[@name='password']")
    private val passwordField: WebElement? = null

    @FindBy(xpath = "//button[@type='submit'][contains(.,'Entrar')]")
    private val botaoLogin: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun login() {
        driver.get(Utils.getURL("urlLegado"))
        val login = Utils.getProperties("loginPJe")
        val password = Utils.getProperties("passwordPJe")
        userName?.sendKeys(login)
        passwordField?.sendKeys(password)
        botaoLogin?.click()
    }
}