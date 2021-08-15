/**
 *
 */
package br.com.alura.leilao.leiloes;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import br.com.alura.leilao.login.LoginPageObject;

/**
 * @author Izabel Rodrigues
 *
 */
class LeilaoTest {

	private static final String USUARIO_FULANO = "fulano";

	private LoginPageObject loginPageObject;
	private LeilaoPageObject leilaoPageObject;
	private CadastroLeilaoPageObject cadastroLeilaoPage;

	@EnabledOnOs({ OS.WINDOWS })
	@BeforeEach
	public void beforeEachIfWin() {
		this.loginPageObject = new LoginPageObject(OS.WINDOWS);
		this.cadastroLeilaoPage = irParaCadastroLeilao();

	}

	@EnabledOnOs({ OS.OTHER })
	public void beforeEachIfNotWin() {
		this.loginPageObject = new LoginPageObject(OS.OTHER);
	}

	@AfterEach
	public void afterEach() {
		this.leilaoPageObject.fechar();
		// this.cadastroLeilaoPage.fechar();
		// this.loginPageObject.fechar();
	}

	@Test
	void validaCriacaoLeilaoComSucesso() {


		String dataAbertura = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String nome = "Leilão do dia " + dataAbertura;
		String valorInicial = "500.00";
		this.leilaoPageObject = cadastroLeilaoPage.cadastraLeilao(nome, valorInicial, dataAbertura);

		boolean leilaoCadastrado = this.leilaoPageObject.isLeilaoCadastrado(nome, valorInicial, dataAbertura);
		boolean contemMensagemSucesso = this.leilaoPageObject.contemTexto("Leilão salvo com sucesso");

		assertTrue(leilaoCadastrado);
		assertTrue(contemMensagemSucesso);

		System.out.println(">>> validaCriacaoLeilaoComSucesso executado...");

	}

	@Test
	void validaCriacaoLeilaoInvalido() {

		this.leilaoPageObject = cadastroLeilaoPage.cadastraLeilao(Strings.EMPTY, Strings.EMPTY, Strings.EMPTY);

		String[] mensagensNome = { "minimo 3 caracteres", "não deve estar em branco" };
		String[] mensagensValorInicial = { "deve ser um valor maior de 0.1" };
		String[] mensagensDataAbertura = { "deve ser uma data no formato dd/MM/yyyy" };

		boolean isNomeValido = this.cadastroLeilaoPage.isNomeValido(mensagensNome);
		boolean isvalorValido = this.cadastroLeilaoPage.isValorInicialValido(mensagensValorInicial);
		boolean isDataValida = this.cadastroLeilaoPage.isDataAberturaValida(mensagensDataAbertura);

		assertFalse(isNomeValido);
		assertFalse(isvalorValido);
		assertFalse(isDataValida);
		System.out.println(">>> validaCriacaoLeilaoInvalido executado...");

	}

	private CadastroLeilaoPageObject irParaCadastroLeilao() {
		this.leilaoPageObject = loginPageObject.efetuaLogin(USUARIO_FULANO, "pass");
		return leilaoPageObject.irParaPaginaCadastroComClickNoBotaoNovoLeilao();
	}

}
