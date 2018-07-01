package com.wyden.test.AutomatedWeb;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wyden.pageobject.AutomatedWeb.MenuPageObject;
import com.wyden.pageobject.AutomatedWeb.UsuarioPageObject;

public class UsuarioTest extends BaseTest {

	@Test(priority = 0, groups = "usuarioTest", dependsOnGroups = "perfilTest")
	public void acessarMenuUsuarioTest() {
		MenuPageObject menuPage = new MenuPageObject(getDriver());
		menuPage.menuUsuario();
		Assert.assertTrue(menuPage.validarMenu("Gerenciamento de Usuário"),
				"Não foi possível acessar o menu Gerenciamento de Usuario");

	}

	@Test(priority = 1, groups = "usuarioTest", dependsOnGroups = "perfilTest")
	public void cadastrarEValidarUsuarioInativoTest() {
		UsuarioPageObject usuarioPage = new UsuarioPageObject(getDriver());
		usuarioPage.cadastrarUsuario("USUARIO", "usuarioauto", "user@auto.com", "selenium123", "UNIDADE_EDITADA",
				"PERFIL_EDITADO", "Inativo");
		Assert.assertTrue(usuarioPage.validarUsuario("USUARIO", "Inativo"),
				"Houve um erro ao validar o nome e situação desta Unidade!");

	}

	@Test(priority = 2, groups = "usuarioTest", dependsOnGroups = "perfilTest")
	public void editarEValidarUsuarioTest() {
		UsuarioPageObject usuarioPage = new UsuarioPageObject(getDriver());
		usuarioPage.editarUsuario("USUARIO", "USUARIO_EDITADO", "Ativo");
		Assert.assertTrue(usuarioPage.validarUsuario("USUARIO_EDITADO", "Ativo"),
				"Houve um erro ao validar o nome e situação desta Unidade!");
	}

	@Test(priority = 3, groups = "removerUsuarioTest", dependsOnGroups = "usuarioTest")
	public void removerEValidarUsuarioTest() {
		UsuarioPageObject usuarioPage = new UsuarioPageObject(getDriver());
		usuarioPage.removerUsuario("USUARIO_EDITADO");
		Assert.assertFalse(usuarioPage.validarUsuario("USUARIO_EDITADO"), "Houve um erro ao remover o usuário");
	}
}
