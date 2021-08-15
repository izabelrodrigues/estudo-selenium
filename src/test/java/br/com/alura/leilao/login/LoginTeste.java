/**
 * 
 */
package br.com.alura.leilao.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Izabel Rodrigues
 *
 */
public class LoginTeste {

	private static final String USUARIO_FULANO = "fulano";
	private static final String URL_LOGIN = "http://localhost:8080/login";

	private WebDriver browser;

	@EnabledOnOs({ OS.WINDOWS })
	@BeforeEach
	public void beforeEachIfWin() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		browser = new ChromeDriver();
	}

	@EnabledOnOs({ OS.OTHER })
	public void beforeEachIfNotWin() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		browser = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {

		try {
			Thread.sleep(5000);
			browser.quit();
		} catch (InterruptedException e) {
			System.out.println("Falha ao fechar o navegador... ");
		}

	}

	@Test
	public void validaLoginComSucesso() {
		
		efetuaLogin(USUARIO_FULANO, "pass");
		String textoUsuarioLogado = browser.findElement(By.id("usuario-logado")).getText();
		String currentUrl = browser.getCurrentUrl();

		assertFalse(URL_LOGIN.equals(currentUrl));
		assertEquals(USUARIO_FULANO, textoUsuarioLogado);
		
		System.out.println(">>> validaLoginComSucesso executado...");
		
	}


	@Test
	public void validaLogoutComSucesso() {
		
		efetuaLogin(USUARIO_FULANO, "pass");
		browser.findElement(By.id("btn-logout")).click();
		
		assertThrows(NoSuchElementException.class, () -> browser.findElement(By.id("usuario-logado")));
		
		System.out.println(">>> validaLogoutComSucesso executado...");
		
	}
	
	@Test
	public void verificaLoginInvalido() {
		
		efetuaLogin(USUARIO_FULANO, "senha_invalida");
		String currentUrl = browser.getCurrentUrl();

		//getPageSource : obtém o código fonte da página
		assertTrue(browser.getPageSource().contains("Usuário e senha inválidos."));
		assertEquals(URL_LOGIN +"?error", currentUrl);
		
		System.out.println(">>> verificaLoginInvalido executado...");
		
	}
	
	private void efetuaLogin(String user, String pass) {
		
		// Abre a tela de login
		browser.navigate().to(URL_LOGIN);
		
		// Preenche os campos do formulario (sendKeys)
		browser.findElement(By.id("username")).sendKeys(user);
		browser.findElement(By.id("password")).sendKeys(pass);
		
		// Envia o formulário de login
		browser.findElement(By.id("login-form")).submit();
		
	}
}
