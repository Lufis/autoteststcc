package com.wyden.pageobject.autoweb;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BasePageObject {

	public void printEvidence(String testName, WebDriver driver) throws IOException {

		Screenshot testEvidence = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
				.takeScreenshot(driver);
		
		String fileName = testName + ".jpeg";
		ImageIO.write(testEvidence.getImage(), "JPEG", new File("TestEvidences/" + fileName));
	}

}
