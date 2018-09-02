package com.wyden.pageobject.autoweb;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MenuPageObject extends BasePageObject {
	final static Logger logger = Logger.getLogger(UnidadePageObject.class);
	private static final String MENU_GERENC_UNIDADE = "gunidade";
	private static final String MENU_GERENC_PERFIL = "gperfil";
	private static final String MENU_GERENC_USUARIO = "gusuario";
	private static final String MENU_ATUAL = "div.col-xs-8 > h1";
	private WebDriver driver;

	public MenuPageObject(WebDriver driver) {
		this.driver = driver;

	}

	public void menuUnidade() {

		logger.info("Acessando o menu [Gerenciamento de Unidade]...");
		WebElement element = driver.findElement(By.id(MENU_GERENC_UNIDADE));
		element.click();
		try {
			printEvidence("menuUnidades", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void menuPerfil() {
		logger.info("Acessando o menu [Geranciamento de Perfil]...");

		WebElement element = driver.findElement(By.id(MENU_GERENC_PERFIL));
		element.click();
		try {
			printEvidence("menuPerfil", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void menuUsuario() {

		logger.info("Acessando o menu [Geranciamento de Usuario]...");
		WebElement element = driver.findElement(By.id(MENU_GERENC_USUARIO));
		try {
			printEvidence("menuUsuario", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.click();
	}

	public boolean validarMenu(String menu) {
		WebElement nomeMenu = driver.findElement(By.cssSelector(MENU_ATUAL));
		if (nomeMenu.getText().equals(menu)) {
			logger.info("Menu " + menu + " acessado com sucesso!");
			return true;
		}
		logger.info("Houve um erro ao acessar o menu " + menu + "...");
		return false;
	}

}
