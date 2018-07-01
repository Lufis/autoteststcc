package com.wyden.test.AutomatedWeb;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wyden.pageobject.AutomatedWeb.MenuPageObject;
import com.wyden.pageobject.AutomatedWeb.PerfilPageObject;

public class PerfilTest extends BaseTest {

	@Test(priority = 0, groups = "perfilTest", dependsOnGroups = "unidadeTest")
	public void acessarMenuPerfilTest() {
		MenuPageObject menuPage = new MenuPageObject(getDriver());
		menuPage.menuPerfil();
		Assert.assertTrue(menuPage.validarMenu("Gerenciamento de Perfil"),
				"Não foi possível acessar o menu Gerenciamento de Perfil");

	}

	@Test(priority = 1, groups = "perfilTest", dependsOnGroups = "unidadeTest")
	public void cadastrarEValidarPerfilInativoTest() {
		PerfilPageObject perfilPage = new PerfilPageObject(getDriver());
		perfilPage.cadastrarPerfil("PERFIL", "Inativo", "UNIDADE_EDITADA");
		Assert.assertTrue(perfilPage.validarPerfil("PERFIL", "Inativo"),
		"Houve um erro ao validar este Perfil!");

	}

	@Test(priority = 2, groups = "perfilTest",dependsOnGroups = "unidadeTest")
	public void editarEValidarPerfilTest() {
		PerfilPageObject perfilPage = new PerfilPageObject(getDriver());
		perfilPage.editarPerfil("PERFIL", "PERFIL_EDITADO", "Ativo");
		Assert.assertTrue(perfilPage.validarPerfil("PERFIL_EDITADO", "Ativo"),
				"Houve um erro ao validar este Perfil!");
	}

	@Test(priority = 3, groups = "removerPerfilTest",dependsOnGroups = "perfilTest")
	public void removerEValidarPerfilTest() {
		PerfilPageObject perfilPage = new PerfilPageObject(getDriver());
		perfilPage.removerPerfil("PERFIL_EDITADO");
		Assert.assertFalse(perfilPage.validarPerfil("PERFIL_EDITADO"),"Houve um erro ao remover o perfil");
	}
}
