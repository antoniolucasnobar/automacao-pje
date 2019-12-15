package com.vc.nobar.acao.dejt.paginas

import com.nobar.Utils
import com.poiji.bind.Poiji
import com.vc.nobar.Pagina
import com.vc.nobar.acao.dejt.UsuarioDEJT
import org.openqa.selenium.*
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait


class CadastroUsuario(private val driver: WebDriver): Pagina {

    @FindBy(xpath = "//a[contains(.,'Usuário')]")
    private val botaoCadastroUsuario: WebElement? = null

    @FindBy(xpath = "//input[@id='corpo:formulario:cpf']")
    private val cpf: WebElement? = null

    @FindBy(xpath = "//input[@id='corpo:formulario:nome']")
    private val nome: WebElement? = null

    @FindBy(xpath = "//input[@id='corpo:formulario:email']")
    private val email: WebElement? = null

    @FindBy(xpath = "//select[@id='corpo:formulario:perfil']")
    private val perfil: WebElement? = null

//    @FindBy(xpath = "//button[contains(.,'F7-Novo')]")
//    private val botaoNovoCadastro: WebElement? = null

    @FindBy(xpath = "//div[@class='plc-corpo-acao-t'][contains(.,'F12-Gravar')]")
    private val botaoSalvar: WebElement? = null

    @FindBy(xpath = "//button[@id='corpo:formulario:botaoIncluirTodasUnidades']")
    private val botaoIncluirTodasUnidades: WebElement? = null

    private val usuarios: List<UsuarioDEJT>


    init {
        PageFactory.initElements(driver, this)
        usuarios = carregarUsuarios(Utils.getProperties("usuariosDEJT"))
    }

    private fun carregarUsuarios(arquivoUsuarios: String): List<UsuarioDEJT> {
        val users = Poiji.fromExcel(Utils.getFile(arquivoUsuarios), UsuarioDEJT::class.java)
        users.forEachIndexed{index, element -> println("index = ${index+2}, element = $element")}
//        println(users)
        println(users.size)

        return users
    }

    override fun executar() {
//        val lucas = UsuarioDEJT("Antonio Lucas Neres de Oliveira",
//            "024.107.755-94", "antonio.lucas@trt4.jus.br")
//        val cris = UsuarioDEJT("Cristina Bottega",
//            "954.274.900-78", "cbottega@trt4.jus.br")
//       val usuarios = ArrayList<UsuarioDEJT>();
//        usuarios.add(lucas)
//        usuarios.add(cris)
        usuarios.forEach {adicionarUsuarioDEJT(it)}
//        adicionarUsuarioDEJT(lucas)
    }

    private fun adicionarUsuarioDEJT(usuario: UsuarioDEJT) {
        WebDriverWait(driver, 10).until {
            botaoCadastroUsuario?.isEnabled
        }
        botaoCadastroUsuario?.click()

//        this.botaoNovoCadastro?.click()
        WebDriverWait(driver, 10).until {
            this.cpf?.isEnabled
        }
        val jse = driver as JavascriptExecutor
        jse.executeScript("document.getElementById('corpo:formulario:cpf').value='${usuario.cpf}';")
        this.cpf?.sendKeys(Keys.TAB);

        val wait = WebDriverWait(driver, 10)
        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='simplemodal-overlay']"))
        )


        this.nome?.text.isNullOrBlank().let {
            this.nome?.clear()
            this.nome?.sendKeys(usuario.nome.toUpperCase())
        }
        this.email?.text.isNullOrBlank().let {
            this.email?.clear()
            this.email?.sendKeys(usuario.email)
        }
        val dropdown = Select(perfil)
        dropdown.selectByVisibleText("Publicador")
        this.botaoSalvar?.click()
        this.botaoIncluirTodasUnidades?.click()
        this.botaoSalvar?.click()

    }
}