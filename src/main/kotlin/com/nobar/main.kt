package com.nobar

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities




fun main(args: Array<String>) {

    Utils.loadProperties()
    val ffOptions = FirefoxOptions()
    ffOptions.addPreference("browser.link.open_newwindow", 1)
    ffOptions.setCapability("marionette", true);
//    "C:\\Selenium\\geckodriver.exe"
    System.setProperty("webdriver.gecko.driver", Utils.getProperties("gecko"))

    val driver = FirefoxDriver(ffOptions)
    driver?.manage()?.timeouts()?.implicitlyWait(3, TimeUnit.SECONDS)
//    driver?.manage()?.window()?.maximize()

    driver.get(Utils.getURL("urlLegado"))
    val homePage = HomePage(driver!!)

    val login = Utils.getProperties("login")
    val password = Utils.getProperties("password")
    homePage.login(login, password)
    driver.get(Utils.getURL("urlPainelGlobal"))

    NoDesvio(driver).enviarProcessos()

    Arquivamento(driver).arquivarProcessos()


    driver.close()
//    driver.quit()
}




