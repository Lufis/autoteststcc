package com.wyden.pageobject.AutomatedWeb;

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

public class UsuarioPageObject {

	private WebDriver driver;
	private static final String MENSAGEM_CRIACAO = "div.alert > span";
	private static final String BT_CADASTRO_USUARIO = "#btn-cadastro-usuario > span";
	private static final String BT_SALVAR_UNIDADE = "form > div:nth-child(10	) > button";
	private static final String NOME = "nome";
	private static final String LOGIN = "login";
	private static final String EMAIL = "email";
	private static final String SENHA = "senha";
	private static final String CONFIRMAR_SENHA = "senha2";
	private static final String PERFIL = "perfil";
	private static final String UNIDADE = "unidade";
	private static final String SITUACAO = "estado";
	private static final String USUARIO_LINHA = "tbody > tr";
	private static final String USUARIO_NOME = "td:nth-child(2) > a";
	private static final String USUARIO_SITUACAO = "td:nth-child(7)";
	private static final String BT_REMOVER_USUARIO = "td:nth-child(8) > form > button > span";
	
	final static Logger logger = Logger.getLogger(UsuarioPageObject.class);
	
	public UsuarioPageObject(WebDriver driver) {
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

	public void cadastrarUsuario(String nomeUsuario, String loginUsuario, String emailUsuario, String senhaUsuario, String unidade, String perfil, String situacao) {
		logger.info("Criando Unidade com nome [" + nomeUsuario + "]...");
		WebElement element = driver.findElement(By.cssSelector(BT_CADASTRO_USUARIO));
		element.click();
		element = driver.findElement(By.id(NOME));
		element.sendKeys(nomeUsuario);
		element = driver.findElement(By.id(LOGIN));
		element.sendKeys(loginUsuario);
		element = driver.findElement(By.id(EMAIL));
		element.sendKeys(emailUsuario);
		element = driver.findElement(By.id(SENHA));
		element.sendKeys(senhaUsuario);
		element = driver.findElement(By.id(CONFIRMAR_SENHA));
		element.sendKeys(senhaUsuario);
		
		Select dropdown = new Select(driver.findElement(By.id(UNIDADE)));
		dropdown.selectByVisibleText(unidade);
		dropdown = new Select(driver.findElement(By.id(PERFIL)));
		dropdown.selectByVisibleText(perfil);
		dropdown = new Select(driver.findElement(By.id(SITUACAO)));
		dropdown.selectByVisibleText(situacao);
		element = driver.findElement(By.cssSelector(BT_SALVAR_UNIDADE));
		element.click();
		logger.info("Salvando...");
		aguardarMensagem();
	}	
	
	public void editarUsuario(String nomeUsuario, String novoNomeUsuario, String novaSituacao) {
		logger.info("Editando a unidade [" + nomeUsuario + "]...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		unidades = driver.findElements(By.cssSelector(USUARIO_LINHA));

		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(USUARIO_NOME)).getText().equals(nomeUsuario)) {
				unidade = unidade.findElement(By.cssSelector(USUARIO_NOME));
				unidade.click();
				WebElement element;
				element = driver.findElement(By.id(NOME));
				element.clear();
				element.sendKeys(novoNomeUsuario);
				Select dropdown = new Select(driver.findElement(By.id(SITUACAO)));
				dropdown.selectByVisibleText(novaSituacao);
				element = driver.findElement(By.cssSelector(BT_SALVAR_UNIDADE));
				element.click();
				logger.info("Salvando...");
				aguardarMensagem();
				break;
			}

		}

	}
	
	public void removerUsuario(String nomeUsuario) {
		logger.info("Removendo a unidade [" + nomeUsuario + "]...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		unidades = driver.findElements(By.cssSelector(USUARIO_LINHA));
		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(USUARIO_NOME)).getText().equals(nomeUsuario)) {
				unidade = unidade.findElement(By.cssSelector(BT_REMOVER_USUARIO));
				unidade.click();
				break;
			}

		}
		Alert popup = driver.switchTo().alert();
		popup.accept();
		aguardarMensagem();

	}
	
	public boolean validarUsuario(String nomeUsuario, String situacao) {
		logger.info("Validando se a unidade [" + nomeUsuario + "] existe...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		unidades = driver.findElements(By.cssSelector(USUARIO_LINHA));
		for (WebElement unidade : unidades) {
			if (unidade.findElement(By.cssSelector(USUARIO_NOME)).getText().equals(nomeUsuario)) {
				logger.info("Validação do nome efetuada com sucesso!");
				if (unidade.findElement(By.cssSelector(USUARIO_SITUACAO)).getText().equals(situacao)) {
					logger.info("Validação da situação efetuada com sucesso!");
					logger.info("Validação efetuada com sucesso!");
					return true;
				}
				logger.error("Erro ao validar situação da Unidade [" + nomeUsuario + "].");
				return false;

			}

		}
		logger.error("Erro ao validar nome da Unidade " + nomeUsuario + ".");
		return false;
	}

	public boolean validarUsuario(String nomeUsuario) {
		logger.info("Validando se a unidade [" + nomeUsuario + "] existe...");
		List<WebElement> unidades = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		unidades = driver.findElements(By.cssSelector("tbody > tr > td:nth-child(2) > a"));
		for (WebElement unidade : unidades) {
			if (unidade.getText().equals(nomeUsuario)) {
				logger.info("Validação do nome efetuada com sucesso!");
				return false;
			}
		}
		logger.error("A unidade [" + nomeUsuario + "] não existe.");
		return false;
	}
}
