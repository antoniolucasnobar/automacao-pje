package com.nobar

import com.vc.nobar.interfaces.Acao
import com.vc.nobar.dejt.DEJTCadastro
import com.vc.nobar.pje.Arquivamento
import com.vc.nobar.pje.NoDesvio
import com.vc.nobar.gui.LoginScreen
import javafx.application.Application
import org.openqa.selenium.WebDriver
import tornadofx.App
import kotlin.reflect.KClass

enum class Acoes(val kClass: KClass<out Acao>, val ativado: Boolean) {
    DEJT(DEJTCadastro::class, true),
    NODESVIO(NoDesvio::class, false),
    ARQUIVAMENTO(Arquivamento::class, false);

    fun getAcao(
        driver: WebDriver,
        loginTxt: String,
        passwordTxt: String
    ): Acao {
        return kClass.constructors.first().call(driver, loginTxt, passwordTxt)
    }
}


class LoginApp : App(LoginScreen::class)

fun main(args: Array<String>) {

    Application.launch(LoginApp::class.java, *args)
    return


//    Utils.loadProperties()
////    driver?.manage()?.window()?.maximize()
//
//    val driver = Utils.getDriver()
//    val acoes2 = Utils.getProperties("acoes")
////        "NoDesvio,Arquivamento"
//        .split(",")
//    acoes2.forEach { ac ->
//        val acaoEnum = Acoes.valueOf(ac.toUpperCase())
//        val acao = acaoEnum.kClass.constructors.first().call(driver)
//        println(acao)
////        acao.preparar()
//        acao.executar(null)
//    }
////    Thread.sleep(5000)
////    driver.close()
//    driver.get(Utils.getURL("urlLegado"))
//    val homePage = HomePage(driver)
//
//    val login = Utils.getProperties("login")
//    val password = Utils.getProperties("password")
//    homePage.login(login, password)
//    //driver.get(Utils.getURL("urlPainelGlobal"))
//
//    val acoes = Utils.getProperties("acoes")
////        "NoDesvio,Arquivamento"
//            .split(",")
//    val processosComErro = ArrayList<ProcessoComErro>()
//    acoes.forEach { ac ->
//        val acaoEnum = Acoes.valueOf(ac.toUpperCase())
//        val acao = acaoEnum.kClass.constructors.first().call(driver)
//        println(acao)
//        acao.preparar()
//        val errados = processosComErro.map { p-> p.processo }
//        Utils.getProcessos().forEach {processo ->
//            try{
//                // se deu erro num passo anterior, nao deve executar os seguintes
//                if (!errados.contains(processo)) {
//                    acao.executar(processo)
//                }
//            } catch (e: Exception) {
//                processosComErro.add(ProcessoComErro(processo, acaoEnum.kClass, e.localizedMessage))
//            }
//        }
//        //tenta executar os que deram problema nesse passo
//        processosComErro.filter { p-> p.acao.equals(acaoEnum.kClass) }.forEach {p ->
//            try{
//                acao.executar(p.processo)
//                //se deu certo dessa vez, apaga da lista
//                processosComErro.remove(p)
//            } catch (e: Exception) {  }
//        }
//
//    }
//    val arquivoErros = File("erros.txt")
//    arquivoErros.writeText(processosComErro.toString())


//    NoDesvio(driver).enviarProcessos()
//
//    Arquivamento(driver).arquivarProcessos()
//
//
//    driver.close()
//    driver.quit()
}




