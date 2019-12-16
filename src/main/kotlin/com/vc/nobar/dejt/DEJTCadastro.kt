package com.vc.nobar.dejt

import com.poiji.bind.Poiji
import com.vc.nobar.interfaces.Pagina
import com.vc.nobar.interfaces.Acao
import com.vc.nobar.dejt.paginas.CadastroUsuario
import com.vc.nobar.dejt.paginas.LoginDEJT
import com.vc.nobar.dejt.paginas.PaginaInicialDEJT
import org.openqa.selenium.WebDriver
import java.io.File


class DEJTCadastro(
    private val driver: WebDriver,
    private val loginTxt: String,
    private val passwordTxt: String
) : Acao {

    private lateinit var users: List<UsuarioDEJT>
    private val paginas = ArrayList<Pagina>()


    override fun getURL(): String {
        return "https://dejt.jt.jus.br/dejt/";
    }

    override fun processarArquivo(file: File) {
        users = Poiji.fromExcel(file, UsuarioDEJT::class.java)
        users.forEachIndexed{index, element -> println("index = ${index+2}, element = $element")}
//        println(users)
        println(users.size)

    }

    override fun preparar() {
        paginas.add(PaginaInicialDEJT(driver))
        paginas.add(LoginDEJT(driver, loginTxt, passwordTxt))
        paginas.add(CadastroUsuario(driver, users))
    }

    override fun executar(processo: String) {
        try {
            driver.get(this.getURL())
            paginas.forEach(Pagina::executar)
        } finally {
            driver.close()
        }
    }

}