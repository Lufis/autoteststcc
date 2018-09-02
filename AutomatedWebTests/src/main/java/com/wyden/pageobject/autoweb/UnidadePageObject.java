package com.wyden.pageobject.autoweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnidadePageObject extends BasePageObject {

	private WebDriver driver;
	private static final String BT_CADASTRO_UNIDADE = "#btn-cadastro-unidade > span";
	private static final String NOME = "nome";
	private static final String SITUACAO = "tipo";
	private static final String BT_SALVAR_UNIDADE = "form > div:nth-child(4) > button";
	private static final String UNIDADE_LINHA = "tbody > tr";
	private static final String UNIDADE_NOME = "td:nth-child(2) > a";
	private static final String UNIDADE_SITUACAO = "td:nth-child(3)";
	private static final String BT_REMOVER_UNIDADE = "td:nth-child(4) > form > button";
	private static final String MENSAGEM_CRIACAO = "div.alert > span";
	final static Logger logger = Logger.getLogger(UnidadePageObject.class);

	public UnidadePageObject(WebDriver driver) {
		this.driver = driver;

	}

	public void aguardarMensagem() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MENSAGEM_CRIACAO))) != null) {
			WebElement alerta = driver.findElement(By.cssSelector(MENSAGEM_CRIACAO));
			logger.info(alerta.getText().toString());
		} else {
			logger.info("Nenhuma mensagem exibida!");
		}
	}

	public void cadastrarUnidade(String nomeUnidade, String situacao) {
		logger.info("Criando Unidade com nome [" + nomeUnidade + "]...");
		WebElement element = driver.findElement(By.cssSelector(BT_CADASTRO_UNIDADE));
		element.click();
		element = driver.findElement(By.id(NOME));
		element.sendKeys(nomeUnidade);
		Select dropdown = new Select(driver.findElement(By.id(SITUACAO)));
		dropdown.selectByVisibleText(situacao);
		element = driver.findElement(By.cssSelector(BT_SALVAR_UNIDADE));
		try {
			printEvidence("cadastrarUnidade", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.click();
		logger.info("Salvando...");
		aguardarMensagem();
		try {
			printEvidence("mensagemCadastroUnidade", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validarUnidade(String nomeUnidade, String situacao) {
		logger.info("Validando se a unidade [" + nomeUnidade + "] existe...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		unidades = driver.findElements(By.cssSelector(UNIDADE_LINHA));
		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(UNIDADE_NOME)).getText().equals(nomeUnidade)) {
				logger.info("Validação do nome efetuada com sucesso!");
				if (unidade.findElement(By.cssSelector(UNIDADE_SITUACAO)).getText().equals(situacao)) {
					logger.info("Validação da situação efetuada com sucesso!");
					logger.info("Validação efetuada com sucesso!");
					return true;
				}
				logger.error("Erro ao validar situação da Unidade [" + nomeUnidade + "].");
				return false;

			}

		}
		logger.error("Erro ao validar nome da Unidade " + nomeUnidade + ".");
		return false;
	}

	public boolean validarUnidade(String nomeUnidade) {
		logger.info("Validando se a unidade [" + nomeUnidade + "] existe...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		unidades = driver.findElements(By.cssSelector("tbody > tr > td:nth-child(2) > a"));
		for (WebElement unidade : unidades) {
			if (unidade.getText().equals(nomeUnidade)) {
				logger.info("Validação do nome efetuada com sucesso!");
				return false;
			}
		}
		logger.error("A unidade [" + nomeUnidade + "] não existe.");
		return false;
	}

	public void editarUnidade(String nomeUnidade, String novoNomeUnidade, String novaSituacao) {
		logger.info("Editando a unidade [" + nomeUnidade + "]...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		unidades = driver.findElements(By.cssSelector(UNIDADE_LINHA));

		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(UNIDADE_NOME)).getText().equals(nomeUnidade)) {
				unidade = unidade.findElement(By.cssSelector(UNIDADE_NOME));
				unidade.click();
				WebElement element;
				element = driver.findElement(By.id(NOME));
				element.clear();
				element.sendKeys(novoNomeUnidade);
				Select dropdown = new Select(driver.findElement(By.id(SITUACAO)));
				dropdown.selectByVisibleText(novaSituacao);
				element = driver.findElement(By.cssSelector(BT_SALVAR_UNIDADE));
				try {
					printEvidence("editarUnidade", driver);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				element.click();
				logger.info("Salvando...");
				aguardarMensagem();
				try {
					printEvidence("mensagemEdiçãoUnidade", driver);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

		}

	}

	public void removerUnidade(String nomeUnidade) {
		logger.info("Removendo a unidade [" + nomeUnidade + "]...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		unidades = driver.findElements(By.cssSelector(UNIDADE_LINHA));
		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(UNIDADE_NOME)).getText().equals(nomeUnidade)) {
				unidade = unidade.findElement(By.cssSelector(BT_REMOVER_UNIDADE));
				unidade.click();
				break;
			}

		}
		Alert popup = driver.switchTo().alert();
		popup.accept();
		aguardarMensagem();
		try {
			printEvidence("mensagemUnidadeRemovida", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
