/**
 *
 */
package br.com.alura.leilao.login;

import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Izabel Rodrigues
 *
 */
public class LoginPageObject {

	private static final String URL_EDITAR_LEILAO_UM = "http://localhost:8080/leiloes/1/form";
	private static final String URL_DAR_LANCE_LEILAO_DOIS = "http://localhost:8080/leiloes/2";
	private static final String URL_LOGIN = "http://localhost:8080/login";
	public WebDriver browser;

	public LoginPageObject(OS osSystem) {

		switch (osSystem) {
		case WINDOWS:
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			this.browser = new ChromeDriver();
			break;
		default:
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
			this.browser = new ChromeDriver();
			break;
		}
	}

	public void fechar() {
		browser.quit();
	}

	public void efetuaLogin(String user, String pass) {
		// Abre a tela de login
		irParaPaginaLogin();

		// Preenche os campos do formulario (sendKeys)
		browser.findElement(By.id("username")).sendKeys(user);
		browser.findElement(By.id("password")).sendKeys(pass);

		// Envia o formulário de login
		browser.findElement(By.id("login-form")).submit();

	}

	private void irParaPaginaLogin() {
		irParaPagina(URL_LOGIN);
	}

	public void efetuaLogout() {
		browser.findElement(By.id("btn-logout")).click();
	}

	public String recuperaNomeUsuarioLogado() {
		return browser.findElement(By.id("usuario-logado")).getText();
	}

	public boolean isPaginaLogin() {
		return URL_LOGIN.equals(browser.getCurrentUrl());
	}

	public String obterTituloPagina() {
		return browser.findElement(By.id("titulo-pagina")).getText();
	}

	public void irParaPaginaDarLance() {
		irParaPagina(URL_DAR_LANCE_LEILAO_DOIS);
	}

	public void irParaPaginaEdicaoLeilao() {
		irParaPagina(URL_EDITAR_LEILAO_UM);
	}

	private void irParaPagina(String paginaDestino) {
		browser.navigate().to(paginaDestino);
	}

	// getPageSource : obtém o código fonte da página
	public boolean contemMensagemLoginInvalido() {
		return browser.getPageSource().contains("Usuário e senha inválidos.");
	}

	public boolean isPaginaLoginErro() {
		return (URL_LOGIN + "?error").equals(browser.getCurrentUrl());
	}
}
