/**
 *
 */
package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.alura.leilao.PageObject;

/**
 * @author Izabel Rodrigues
 *
 */
public class CadastroLeilaoPageObject extends PageObject {

	public CadastroLeilaoPageObject(WebDriver browser, String systemDriver) {
		super(browser, systemDriver);
	}

	public LeilaoPageObject cadastraLeilao(String nome, String valorInicial, String dataAbertura) {

		preencheFormulario(nome, valorInicial, dataAbertura);
		browser.findElement(By.id("button-submit")).click();

		return new LeilaoPageObject(browser, this.systemDriver);

	}

	private void preencheFormulario(String nome, String valorInicial, String dataAbertura) {
		browser.findElement(By.id("nome")).sendKeys(nome);
		browser.findElement(By.id("valorInicial")).sendKeys(valorInicial);
		browser.findElement(By.id("dataAbertura")).sendKeys(dataAbertura);
	}

	public boolean isNomeValido(String[] mensagensNome) {
		return validaCampo(mensagensNome);
	}

	public boolean isValorInicialValido(String[] mensagensValorInicial) {
		return validaCampo(mensagensValorInicial);
	}

	public boolean isDataAberturaValida(String[] mensagensDataAbertura) {
		return validaCampo(mensagensDataAbertura);
	}

	private boolean validaCampo(String[] mensagens) {

		for (String msg : mensagens) {
			browser.getPageSource().contains(msg);
			return false;
		}

		return true;
	}


}
