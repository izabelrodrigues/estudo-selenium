/**
 *
 */
package br.com.alura.leilao.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.NoSuchElementException;

/**
 * @author Izabel Rodrigues
 *
 */
class LoginTest {

	private static final String USUARIO_FULANO = "fulano";
	private LoginPageObject loginPageObject;


	@EnabledOnOs({ OS.WINDOWS })
	@BeforeEach
	public void beforeEachIfWin() {
		loginPageObject = new LoginPageObject(OS.WINDOWS);
	}

	@EnabledOnOs({ OS.OTHER })
	public void beforeEachIfNotWin() {
		loginPageObject = new LoginPageObject(OS.OTHER);
	}

	@AfterEach
	public void afterEach() {
		this.loginPageObject.fechar();
	}

	@Test
	void validaLoginComSucesso() {

		efetuaLogin(USUARIO_FULANO, "pass");

		assertFalse(this.loginPageObject.isPaginaLogin());
		assertEquals(USUARIO_FULANO, this.loginPageObject.recuperaNomeUsuarioLogado());

		System.out.println(">>> validaLoginComSucesso executado...");

	}

	@Test
	void validaLogoutComSucesso() {

		efetuaLogin(USUARIO_FULANO, "pass");
		loginPageObject.efetuaLogout();

		assertThrows(NoSuchElementException.class, () -> loginPageObject.recuperaNomeUsuarioLogado());

		System.out.println(">>> validaLogoutComSucesso executado...");

	}

	@Test
	void verificaLoginInvalido() {

		efetuaLogin(USUARIO_FULANO, "senha_invalida");

		assertTrue(loginPageObject.contemMensagemLoginInvalido());
		assertTrue(loginPageObject.isPaginaLoginErro());

		System.out.println(">>> verificaLoginInvalido executado...");

	}

	@Test
	void verificaRedirecionamentoUsuarioNaoLogadoEmPaginaRestritaEdicao() {

		loginPageObject.irParaPaginaEdicaoLeilao();

		assertTrue(loginPageObject.isPaginaLogin());
		assertNotEquals("Novo Leilão",loginPageObject.obterTituloPagina());

		System.out.println(">>> verificaRedirecionamentoUsuarioNaoLogadoEmPaginaRestritaEdicao executado...");
	}

	@Test
	void verificaRedirecionamentoUsuarioNaoLogadoEmPaginaRestritaDarLance() {

		loginPageObject.irParaPaginaDarLance();

		assertTrue(loginPageObject.isPaginaLogin());
		assertNotEquals("Dados do Leilão",loginPageObject.obterTituloPagina());

		System.out.println(">>> verificaRedirecionamentoUsuarioNaoLogadoEmPaginaRestritaDarLance executado...");
	}

	private void efetuaLogin(String user, String pass) {
		loginPageObject.efetuaLogin(user, pass);
	}
}
