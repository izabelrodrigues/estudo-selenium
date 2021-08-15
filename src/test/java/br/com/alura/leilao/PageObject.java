/**
 *
 */
package br.com.alura.leilao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Izabel Rodrigues
 *
 */
public class PageObject {

	public WebDriver browser;
	public String systemDriver;

	/**
	 * @param browser
	 */
	public PageObject(WebDriver browser, String systemDriver) {

		this.systemDriver = systemDriver;
		System.setProperty("webdriver.chrome.driver", systemDriver);
		if (null == browser) {
			this.browser = new ChromeDriver();
		} else {
			this.browser = browser;
		}

	}

	public void fechar() {
		browser.quit();
	}

}
