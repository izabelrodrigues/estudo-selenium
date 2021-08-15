/**
 *
 */
package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Izabel Rodrigues
 *
 */
public class LeilaoPageObject {

	private WebDriver browser;

	/**
	 * @param browser
	 */
	public LeilaoPageObject(WebDriver browser) {
		this.browser = browser;
	}

	public void fechar() {
		this.browser.quit();
	}

	public CadastroLeilaoPageObject irParaPaginaCadastroComClickNoBotaoNovoLeilao() {
		browser.findElement(By.id("novo_leilao_link")).click();
		return new CadastroLeilaoPageObject(browser);
	}

	public boolean isLeilaoCadastrado(String nome, String valorInicial, String dataAbertura) {
		// Recupera a ultima linha da tabela
		WebElement linhaDaTabela = this.browser.findElement(By.cssSelector("#tabela-leiloes tbody tr:last-child"));

		// Recupera a primeira td da ultima linha da tabela
		WebElement colunaNome = linhaDaTabela.findElement(By.cssSelector("td:nth-child(1)"));
		// Recupera a segunda td da ultima linha da tabela
		WebElement colunaDataAbertura = linhaDaTabela.findElement(By.cssSelector("td:nth-child(2)"));
		// Recupera a terceira td da ultima linha da tabela
		WebElement colunaValorInicial = linhaDaTabela.findElement(By.cssSelector("td:nth-child(3)"));

		return nome.equals(colunaNome.getText()) && dataAbertura.equals(colunaDataAbertura.getText()) && valorInicial.equals(colunaValorInicial.getText());
	}

	public boolean contemTexto(String mensagem) {
		return this.browser.getPageSource().contains(mensagem);
	}

}
