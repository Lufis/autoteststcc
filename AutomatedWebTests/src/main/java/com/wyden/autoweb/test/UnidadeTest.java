package com.wyden.autoweb.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wyden.pageobject.autoweb.MenuPageObject;
import com.wyden.pageobject.autoweb.UnidadePageObject;

public class UnidadeTest extends BaseTest {

	@Test(priority = 0, groups = "unidadeTest")
	public void acessarMenuUnidadeTest() {
		MenuPageObject menuPage = new MenuPageObject(getDriver());
		menuPage.menuUnidade();
		Assert.assertTrue(menuPage.validarMenu("Gerenciamento de Unidade"),
				"Não foi possível acessar o menu Gerenciamento de Unidade");

	}

	@Test(priority = 1, groups = "unidadeTest")
	public void cadastrarEValidarUnidadeInativaTest() {
		UnidadePageObject unidadePage = new UnidadePageObject(getDriver());
		unidadePage.cadastrarUnidade("UNIDADE", "Inativo");
		Assert.assertTrue(unidadePage.validarUnidade("UNIDADE", "Inativo"),
				"Houve um erro ao validar o nome e situação desta Unidade!");

	}

	@Test(priority = 2, groups = "unidadeTest")
	public void editarEValidarUnidadeTest() {
		UnidadePageObject unidadePage = new UnidadePageObject(getDriver());
		unidadePage.editarUnidade("UNIDADE", "UNIDADE_EDITADA", "Ativo");
		Assert.assertTrue(unidadePage.validarUnidade("UNIDADE_EDITADA", "Ativo"),
				"Houve um erro ao validar o nome e situação desta Unidade!");
	}

	@Test(priority = 3, groups = "removerUnidadeTest", dependsOnGroups = "removerPerfilTest")
	public void removerEValidarUnidadeTest() {
		UnidadePageObject unidadePage = new UnidadePageObject(getDriver());
		unidadePage.removerUnidade("UNIDADE_EDITADA");
		Assert.assertFalse(unidadePage.validarUnidade("UNIDADE_EDITADA"), "Houve um erro ao remover a unidade");
	}
}
