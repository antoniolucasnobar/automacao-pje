package com.nobar

import com.vc.nobar.dejt.DEJTCadastro
import com.vc.nobar.gui.LoginScreen
import com.vc.nobar.interfaces.Acao
import com.vc.nobar.pje.*
import com.vc.nobar.utils.Utils
import org.openqa.selenium.WebDriver
import tornadofx.App
import kotlin.reflect.KClass

enum class Acoes(val kClass: KClass<out Acao>, val ativado: Boolean) {
    DEJT(DEJTCadastro::class, true),
    NODESVIO(NoDesvio::class, false),
    ARQUIVAMENTO(Arquivamento::class, false);

    fun getAcao(
        driver: WebDriver
    ): Acao {
        val loginTxt: String = Utils.getProperties("login")
        val passwordTxt: String = Utils.getProperties("password")
        return kClass.constructors.first().call(driver, loginTxt, passwordTxt)
    }

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

//    val parentLogger: Logger = LogManager.getLogger()
//
//    parentLogger.error("log4j2 t1111a aqui!")
//
//    val logger = KotlinLogging.logger {}
//    logger.debug { "hello message" }
//    if (1 == (2 - 1)) return;


//    Application.launch(LoginApp::class.java, *args)
//    return;
//    Utils.getLogger().debug("teste")
//    Utils.getLogger().error("Reeeeou!")

    Utils.loadProperties()
    val driver = Utils.getDriver()

CartasDevolvidas(driver).executar()

    if (1 == (2 - 1)) return;
    val acoes = Utils.getProperties("acoes")
        .split(",")
    acoes.forEach { ac ->
        val acaoEnum = Acoes.valueOf(ac.toUpperCase())
//        val acao = acaoEnum.kClass.constructors.first().call(driver)
        val acao = acaoEnum.getAcao(driver)
        println(acao)
        acao.executar()
    }
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




