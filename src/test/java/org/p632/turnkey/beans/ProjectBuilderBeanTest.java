/**
 * Vivek Patani {FlipSwitch}
 * ProjectBuilderBeanTest.java
 * {Algorithms 0.: Living in Beta}
 */
package org.p632.turnkey.beans;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.p632.turnkey.helpers.Constants;
import org.p632.turnkey.model.TemplateModel;
import org.p632.turnkey.pom.Dependency;
import org.p632.turnkey.pom.Pom;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectBuilderBeanTest {
	
	@Mock
	ProjectBuilderBean projectBuilderMean;

	@Mock
	ResourceLoader ldr;

	@Mock
	File file;
	
	@Mock
	Resource resource;
	
	@Mock
	File xmlFile;

	@Mock
	ObjectMapper xmlMapper;

	TemplateModel templateModel;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(ProjectBuilderBeanTest.class);
	}
	
	@Test
	public void generatePomTest() throws Exception {
		
		ProjectBuilderBean spyBean = Mockito.spy(new ProjectBuilderBean());
		//Define Template Model
		templateModel = new TemplateModel();
		templateModel.setArtifact("Sujeet");
		templateModel.setProjectGroup("Panchat");
		templateModel.setPackagingType("jar");
		templateModel.setParentProjectGroup("Hello");
		templateModel.setParentArtifact("Parentwa");
		
		ReflectionTestUtils.setField(spyBean, "serverPath", "dummy_path");
		ReflectionTestUtils.setField(spyBean, "templatePath", "dummy_tmp_path");
		ReflectionTestUtils.setField(spyBean, "resourceLoader", ldr);
		
		Mockito.doNothing().when(spyBean).prepareDependencyXml(Mockito.any(ArrayList.class), Mockito.any(Pom.class));
		Mockito.when(ldr.getResource(Mockito.anyString())).thenReturn(resource);
		Mockito.when(resource.getFile()).thenReturn(file);
		Mockito.when(file.getAbsolutePath()).thenReturn("dummy-file_path");

		spyBean.generatePom(templateModel);
	}
	
	@Test
	public void prepareDependencyXmlTest() throws Exception{
		ProjectBuilderBean spyBean = Mockito.spy(new ProjectBuilderBean());
		
		ReflectionTestUtils.setField(spyBean, "serverPath", "dummy_path");
		ReflectionTestUtils.setField(spyBean, "resourceLoader", ldr);
		
		String path = ProjectBuilderBean.class.getClassLoader().getResource("dependency.xml").getPath().substring(1);
		
		Mockito.when(ldr.getResource(Mockito.anyString())).thenReturn(resource);
		Mockito.when(resource.getFile()).thenReturn(file);
		Mockito.when(file.getAbsolutePath()).thenReturn(path);

		Pom pom = new Pom();
		ArrayList<String> dependencyListlist = new ArrayList<String>();
		dependencyListlist.add("spring-core");
		
		spyBean.prepareDependencyXml(dependencyListlist, pom);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}
