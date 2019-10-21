package com.nobar

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

    fun login(login: String, password: String) {
        userName?.sendKeys(login)
        passwordField?.sendKeys(password)
        botaoLogin?.click()
    }
}