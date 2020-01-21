package com.vc.nobar.dejt.paginas

import com.nobar.Utils
import com.vc.nobar.interfaces.ItemProcessamento
import com.vc.nobar.interfaces.Pagina
import com.vc.nobar.dejt.UsuarioDEJT
import org.openqa.selenium.*
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait


class CadastroUsuario(private val driver: WebDriver, private val usuarios: List<UsuarioDEJT>): Pagina {

    @FindBy(xpath = "//a[contains(.,'Usuário')]")
    private val botaoCadastroUsuario: WebElement? = null

    @FindBy(xpath = "//input[@id='corpo:formulario:id']")
    private val codigo: WebElement? = null


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

    init {
        PageFactory.initElements(driver, this)
    }


    fun executar(): Boolean {
//        usuarios.forEach {adicionarUsuarioDEJT(it)}
        println("comecando na linha ${Utils.avanco}")
        usuarios.forEachIndexed { index, element ->
            if (index >= Utils.avanco) {
                try {
                    adicionarUsuarioDEJT(element)
                    Utils.avanco++
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Erro na linha $index - $element. Vamos tentar continuar dessa linha")
                    Utils.writeAvanco()
                    return false;
                }
            }
        }
        return true;
    }

    private fun adicionarUsuarioDEJT(usuario: UsuarioDEJT) {
        WebDriverWait(driver, Utils.TIMEOUT).until {
            botaoCadastroUsuario?.isEnabled
        }
        botaoCadastroUsuario?.click()

//        this.botaoNovoCadastro?.click()
        WebDriverWait(driver, Utils.TIMEOUT).until {
            this.cpf?.isEnabled
        }
        val jse = driver as JavascriptExecutor
        jse.executeScript("document.getElementById('corpo:formulario:cpf').value='${usuario.cpf}';")
        this.cpf?.sendKeys(Keys.TAB);

        val wait = WebDriverWait(driver, Utils.TIMEOUT)
        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='simplemodal-overlay']"))
        )

        val cod = codigo?.getAttribute("value")
        println("$cod|$usuario")

        if (!cod.isNullOrBlank()) {
            println("ja cadastrado! nao faz nada.")
            Utils.msg = "(cadastro feito anteriormente)"

            val precisaEnviarConvite = driver.findElements(By.xpath("//button[contains(.,'Reenviar Convite')]")).size > 0

            if (precisaEnviarConvite) {
                val reenviarConvite = driver.findElement(By.xpath("//button[contains(.,'Reenviar Convite')]"))
                println("enviando convite para ${usuario.nome}")
                reenviarConvite.click()
                Utils.convites++;
                wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(.,'Convite enviado com sucesso!')]"))
                )
            }
            incluirTodasUnidades(wait)
            return;
        }
        println("nao cadastrado! cadastro agora")



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
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(.,'Registro gravado com sucesso')]"))
        )
        incluirTodasUnidades(wait)
        println("cadastro feito com sucesso!")
        Utils.msg = "(cadastro OK)"

    }

    private fun incluirTodasUnidades(wait: WebDriverWait) {
        this.botaoIncluirTodasUnidades?.click()
//        wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[contains(.,'Registro gravado com sucesso')]"))
//        )
        this.botaoSalvar?.click()
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(.,'Registro gravado com sucesso')]"))
        )
    }

    override fun executar(item: ItemProcessamento?) {
        adicionarUsuarioDEJT(item?.getItem() as UsuarioDEJT)
    }
}