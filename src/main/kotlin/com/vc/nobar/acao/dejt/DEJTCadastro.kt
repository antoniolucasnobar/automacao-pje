package com.vc.nobar.acao.dejt

import com.nobar.*
import com.vc.nobar.Pagina
import com.vc.nobar.acao.Acao
import com.vc.nobar.acao.dejt.paginas.CadastroUsuario
import com.vc.nobar.acao.dejt.paginas.LoginDEJT
import com.vc.nobar.acao.dejt.paginas.PaginaInicialDEJT
import org.openqa.selenium.WebDriver


class DEJTCadastro(private val driver: WebDriver) : Acao {

    private val paginas = ArrayList<Pagina>()


    override fun getURL(): String {
        return "https://dejt.jt.jus.br/dejt/";
    }

    override fun preparar() {
        driver.get(this.getURL())
        paginas.add(PaginaInicialDEJT(driver))
        paginas.add(LoginDEJT(driver))
        paginas.add(CadastroUsuario(driver))
    }

    override fun executar(processo: String) {
        paginas.forEach(Pagina::executar)
    }

    fun arquivar(processo: String) {
        val painelGlobal = PainelGlobal(driver)
        painelGlobal.abrirTarefa(processo)

        val tarefas = Tarefa(driver)
        tarefas.mover(processo,"Análise")
        tarefas.mover(processo,"Arquivar o processo")

        val tarefasLegado = TarefaLegado(driver)
        tarefasLegado.mover(processo, "Arquivar definitivamente")

//        val tarefa = Utils.getProperties("tarefaArquivamento")
//        val wait = WebDriverWait(driver, 10)
//        wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']"))
//        )
//        val element = wait.until(
//            ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='$tarefa']"))
//        )
//        element.click()
    }


    fun podeExecutar(): Boolean{
        val properties = Utils.getProperties("executarArquivamento")
        return "Sim".equals(properties, true)
    }


    fun arquivarProcessos() {
        if (podeExecutar()) {
            preparar()
            Utils.getProcessos().forEach {
                arquivar(it)
            }
        } else {
            println("nao vai tambem nao")
        }
    }
}