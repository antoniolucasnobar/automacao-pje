package com.vc.nobar.utils

import org.apache.commons.lang3.SystemUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import java.io.*
import java.net.URI
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit


object Utils {
    var TIMEOUT = 60L
    var msg = ""
    var convites: Int = 0;
    var avanco: Int = 0;
    private var properties = Properties()
    private const val caminho1 = "./src/main/resources/"
    private var caminho = "./"
    private var errados = Properties()


    fun getConfigFile(): FileInputStream? {
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(caminho + "config.properties")
        } catch (e: Exception) {
            fis = FileInputStream(caminho1 + "config.properties")
            caminho = caminho1
        }
        return fis
    }

//    fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
//
//    fun getLogger(name: String): Logger = LoggerFactory.getLogger(name)




    fun loadProperties() {
        try {
            val fis = getConfigFile()
            val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
            properties?.load(isr)
            avanco = Integer.parseInt(properties["comecarNaLinha"] as String?)
            val timeoutProperty = properties["timeout"]
            timeoutProperty?.let { TIMEOUT = (timeoutProperty as String).toLong() }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeAvanco() {
        properties?.setProperty("comecarNaLinha", avanco.toString())
        val fos = FileOutputStream(caminho + "config.properties")
        properties.store(fos, null)
    }

    fun getProperties(properties: String): String {
        loadProperties()
        return Utils.properties?.getProperty(properties).toString()
    }

    fun getURL(caminho: String) : String {
        return URI(
            getProperties("pageURL") + getProperties(
                caminho
            )
        ).toString()
    }

    fun getProcessos(): List<String> {
        val lineList = mutableListOf<String>()
       getFile("processos.txt").useLines { lines -> lines.forEach { lineList.add(it.trim()) }}
        return lineList
    }

    fun getFile(arquivo: String) : File {
        return File(caminho + arquivo)
    }

    fun getFile() : File {
        return File(
            caminho + getProperties(
                "arquivo"
            )
        )
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
                os = "windows"
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
        ffOptions.addPreference("devtools.selfxss.count", 100)
        ffOptions.setCapability("marionette", true)
        val gecko =
            getGeckoDriver() // binario/executavel q depende da plataforma
        println( gecko.canonicalPath)
        System.setProperty("webdriver.gecko.driver",  gecko.canonicalPath)
        val driver = FirefoxDriver(ffOptions)
        driver.manage()?.timeouts()?.implicitlyWait(5, TimeUnit.SECONDS)
        return driver
    }


    }