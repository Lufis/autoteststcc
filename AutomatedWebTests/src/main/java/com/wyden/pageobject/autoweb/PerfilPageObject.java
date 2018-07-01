package com.wyden.pageobject.autoweb;

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

public class PerfilPageObject {

	private WebDriver driver;
	private static final String BT_CADASTRO_UNIDADE = "#btn-cadastro-perfil > span";
	private static final String NOME = "nome";
	private static final String SITUACAO = "tipo";
	private static final String UNIDADE = "unidade";
	private static final String MENSAGEM_CRIACAO = "div.alert > span";
	private static final String BT_SALVAR_PERFIL = "form > div:nth-child(5) > button";
	private static final String PERFIL_LINHA = "tbody > tr";
	private static final String PERFIL_NOME = "td:nth-child(2) > a";
	private static final String PERFIL_SITUACAO = "td:nth-child(4)";
	private static final String BT_REMOVER_PERFIL = "td:nth-child(5) > form > button";
	final static Logger logger = Logger.getLogger(PerfilPageObject.class);

	public PerfilPageObject(WebDriver driver) {
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

	public void cadastrarPerfil(String nomePerfil, String situacao, String unidade) {
		logger.info("Criando Perfil com nome [" + nomePerfil + "]...");
		WebElement element = driver.findElement(By.cssSelector(BT_CADASTRO_UNIDADE));
		element.click();
		element = driver.findElement(By.id(NOME));
		element.sendKeys(nomePerfil);
		Select dropdown = new Select(driver.findElement(By.id(SITUACAO)));
		dropdown.selectByVisibleText(situacao);
		dropdown = new Select(driver.findElement(By.id(UNIDADE)));
		dropdown.selectByVisibleText(unidade);
		element = driver.findElement(By.cssSelector(BT_SALVAR_PERFIL));
		element.click();
		logger.info("Salvando...");
		aguardarMensagem();
	}

	public void editarPerfil(String nomePerfil, String novoNomePerfil, String novaSituacao) {
		logger.info("Editando o perfil [" + nomePerfil + "]...");
		List<WebElement> perfis = new ArrayList<WebElement>();
		perfis = driver.findElements(By.cssSelector(PERFIL_LINHA));

		for (WebElement perfil : perfis) {
			if (perfil.findElement(By.cssSelector(PERFIL_NOME)).getText().equals(nomePerfil)) {
				perfil = perfil.findElement(By.cssSelector(PERFIL_NOME));
				perfil.click();
				WebElement element;
				element = driver.findElement(By.id(NOME));
				element.clear();
				element.sendKeys(novoNomePerfil);
				Select dropdown = new Select(driver.findElement(By.id(SITUACAO)));
				dropdown.selectByVisibleText(novaSituacao);
				element = driver.findElement(By.cssSelector(BT_SALVAR_PERFIL));
				element.click();
				logger.info("Salvando...");
				aguardarMensagem();
				break;
			}

		}

	}

	public boolean validarPerfil(String nomePerfil, String situacao) {
		logger.info("Validando se o perfil [" + nomePerfil + "] existe...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		unidades = driver.findElements(By.cssSelector(PERFIL_LINHA));
		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(PERFIL_NOME)).getText().equals(nomePerfil)) {
				logger.info("Validação do nome efetuada com sucesso!");
				if (unidade.findElement(By.cssSelector(PERFIL_SITUACAO)).getText().equals(situacao)) {
					logger.info("Validação da situação efetuada com sucesso!");
					logger.info("Validação efetuada com sucesso!");
					return true;
				}
				logger.error("Erro ao validar situação do perfil [" + nomePerfil + "].");
				return false;

			}

		}
		logger.error("Erro ao validar nome do perfil [" + nomePerfil + ".");
		return false;
	}

	public boolean validarPerfil(String nomePerfil) {
		logger.info("Validando se o perfil [" + nomePerfil + "] existe...");
		List<WebElement> perfis = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		perfis = driver.findElements(By.cssSelector("tbody > tr > td:nth-child(2) > a"));
		for (WebElement perfil : perfis) {
			if (perfil.getText().equals(nomePerfil)) {
				logger.info("Validação do nome efetuada com sucesso!");
				return false;
			}
		}
		logger.error("O perfil [" + nomePerfil + "] não existe.");
		return false;
	}

	public void removerPerfil(String nomePerfil) {
		logger.info("Removendo o perfil [" + nomePerfil + "]...");
		List<WebElement> perfis = new ArrayList<WebElement>();
		perfis = driver.findElements(By.cssSelector(PERFIL_LINHA));
		for (WebElement perfil : perfis) {
			if (perfil.findElement(By.cssSelector(PERFIL_NOME)).getText().equals(nomePerfil)) {
				perfil = perfil.findElement(By.cssSelector(BT_REMOVER_PERFIL));
				perfil.click();
				break;
			}

		}
		Alert popup = driver.switchTo().alert();
		popup.accept();
		aguardarMensagem();

	}
}
