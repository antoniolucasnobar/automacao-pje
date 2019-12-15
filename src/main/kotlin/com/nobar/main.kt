package com.nobar

import com.vc.nobar.interfaces.Acao
import com.vc.nobar.dejt.DEJTCadastro
import com.vc.nobar.pje.Arquivamento
import com.vc.nobar.pje.NoDesvio
import com.vc.nobar.gui.LoginScreen
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxOptions
import tornadofx.App
import java.io.File
import java.lang.Exception
import kotlin.reflect.KClass

enum class Acoes(val kClass: KClass<out Acao>) {
    NODESVIO(NoDesvio::class),
    ARQUIVAMENTO(Arquivamento::class),
    DEJT(DEJTCadastro::class)


}


class LoginApp : App(LoginScreen::class)

fun main(args: Array<String>) {

//    Application.launch(LoginApp::class.java, *args)


    Utils.loadProperties()
    val ffOptions = FirefoxOptions()
    ffOptions.addPreference("browser.link.open_newwindow", 1)
    ffOptions.setCapability("marionette", true)
//    "C:\\Selenium\\geckodriver.exe"
//    val gecko = File(Utils.getProperties("gecko"))
    //TODO ver como ajustar isso.
//    val gecko = File(Utils.geckoPath)
//    println( gecko.canonicalPath)
//    System.setProperty("webdriver.gecko.driver",  gecko.canonicalPath)

    val driver = FirefoxDriver(ffOptions)
    driver.manage()?.timeouts()?.implicitlyWait(3, TimeUnit.SECONDS)
//    driver?.manage()?.window()?.maximize()

    val acoes2 = Utils.getProperties("acoes")
//        "NoDesvio,Arquivamento"
        .split(",")
    acoes2.forEach { ac ->
        val acaoEnum = Acoes.valueOf(ac.toUpperCase())
        val acao = acaoEnum.kClass.constructors.first().call(driver)
        println(acao)
        acao.preparar()
        acao.executar("")
    }
//    Thread.sleep(5000)
//    driver.close()
    return;

    driver.get(Utils.getURL("urlLegado"))
    val homePage = HomePage(driver)

    val login = Utils.getProperties("login")
    val password = Utils.getProperties("password")
    homePage.login(login, password)
    //driver.get(Utils.getURL("urlPainelGlobal"))

    val acoes = Utils.getProperties("acoes")
//        "NoDesvio,Arquivamento"
            .split(",")
    val processosComErro = ArrayList<ProcessoComErro>()
    acoes.forEach { ac ->
        val acaoEnum = Acoes.valueOf(ac.toUpperCase())
        val acao = acaoEnum.kClass.constructors.first().call(driver)
        println(acao)
        acao.preparar()
        val errados = processosComErro.map { p-> p.processo }
        Utils.getProcessos().forEach {processo ->
            try{
                // se deu erro num passo anterior, nao deve executar os seguintes
                if (!errados.contains(processo)) {
                    acao.executar(processo)
                }
            } catch (e: Exception) {
                processosComErro.add(ProcessoComErro(processo, acaoEnum.kClass, e.localizedMessage))
            }
        }
        //tenta executar os que deram problema nesse passo
        processosComErro.filter { p-> p.acao.equals(acaoEnum.kClass) }.forEach {p ->
            try{
                acao.executar(p.processo)
                //se deu certo dessa vez, apaga da lista
                processosComErro.remove(p)
            } catch (e: Exception) {  }
        }

    }
    val arquivoErros = File("erros.txt")
    arquivoErros.writeText(processosComErro.toString())


//    NoDesvio(driver).enviarProcessos()
//
//    Arquivamento(driver).arquivarProcessos()
//
//
//    driver.close()
//    driver.quit()
}




