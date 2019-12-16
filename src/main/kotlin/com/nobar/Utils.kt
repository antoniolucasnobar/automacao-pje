package com.nobar

import org.apache.commons.lang3.SystemUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit


object Utils {

    private var properties: Properties? = null
    //    private val caminho = "/Users/lucas/IdeaProjects/noDesvio/src/main/resources/"
    private val caminho = "./src/main/resources/"
//    private val caminho = "./"
    public val geckoPath = caminho + "geckodriver.exe"
    fun loadProperties() {
        try {
            properties = Properties()
            val fis = FileInputStream(caminho + "config.properties")

            val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
                properties?.load(isr)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getProperties(properties: String): String {
        loadProperties()
        return Utils.properties?.getProperty(properties).toString()
    }

    fun getURL(caminho: String) : String {
        return URI(getProperties("pageURL") + getProperties(caminho)).toString()
    }

    fun getProcessos(): List<String> {
        val lineList = mutableListOf<String>()
       getFile( "processos.txt").useLines { lines -> lines.forEach { lineList.add(it.trim()) }}
        return lineList
    }

    fun getFile(arquivo: String) : File {
        return File(caminho + arquivo)
    }

    fun getArch(): Int {

        val arch = System.getenv("PROCESSOR_ARCHITECTURE")
        val wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432")
        val sysUtilsArch = SystemUtils.OS_ARCH
        return if (arch != null && arch.endsWith("64")
            || wow64Arch != null && wow64Arch.endsWith("64")
            || sysUtilsArch != null && sysUtilsArch.endsWith("64")
        ) 64 else 32
    }

    fun getGeckoDriver(): File {
        var os = ""
        var suffix = ""
        when {
            SystemUtils.IS_OS_MAC -> {
                os = "mac"
            }
            SystemUtils.IS_OS_WINDOWS -> {
                os = "mac"
                suffix = ".exe"
            }
            SystemUtils.IS_OS_LINUX -> {
                os = "linux"
            }
        }
        return File("./drivers/geckodriver-${os}-${getArch()}bit${suffix}");
    }

    fun getDriver(): WebDriver {
        val ffOptions = FirefoxOptions()
        //faz o navegador abrir todos os links na mesma aba.
        ffOptions.addPreference("browser.link.open_newwindow", 1)
        ffOptions.setCapability("marionette", true)
        val gecko = getGeckoDriver()
        println( gecko.canonicalPath)
        System.setProperty("webdriver.gecko.driver",  gecko.canonicalPath)
        val driver = FirefoxDriver(ffOptions)
        driver.manage()?.timeouts()?.implicitlyWait(3, TimeUnit.SECONDS)
        return driver
    }


    }