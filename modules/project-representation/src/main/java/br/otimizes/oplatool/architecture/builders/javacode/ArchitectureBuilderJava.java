package br.otimizes.oplatool.architecture.builders.javacode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.otimizes.oplatool.architecture.representation.Architecture;
import br.otimizes.oplatool.architecture.representation.Attribute;
import br.otimizes.oplatool.architecture.representation.Class;
import br.otimizes.oplatool.architecture.representation.Element;
import br.otimizes.oplatool.architecture.representation.Interface;
import br.otimizes.oplatool.architecture.representation.Package;
import br.pucrio.opus.smells.resources.FieldJava;
import br.pucrio.opus.smells.resources.JavaFilesFinder;
import br.pucrio.opus.smells.resources.SourceFileJava;
import br.pucrio.opus.smells.resources.SourceFilesLoader;
import br.pucrio.opus.smells.resources.TypeJava;

public class ArchitectureBuilderJava {

	public Architecture create(String projectName, List<String> sourcePaths) throws IOException {

		Architecture architecture = new Architecture(projectName);
		architecture.setProjectName(projectName);

		List<TypeJava> allTypes = getAllTypes(sourcePaths);

		createPackages(architecture, allTypes);
		createClassesAndInterfaces(architecture, allTypes);


		return architecture;
	}

	private void createClassesAndInterfaces(Architecture architecture, List<TypeJava> allTypes) {
		for (TypeJava type : allTypes) {
			if (type.isInterface()) {
				createInterface(architecture, type);
			} else {
				createClass(architecture, type);
			}
		}
	}

	private void createClass(Architecture architecture, TypeJava type) {
		Class klass = new Class(null, type.getFullyQualifiedName(), type.isAbstract());

		String packageName = type.getPackageName();
		if (packageName.isEmpty()) {
			architecture.addExternalClass(klass);
		} else {
			Package _package = architecture.findPackageByName(packageName);
			_package.addExternalClass(klass);
		}
		
		for (FieldJava field : type.getFields()) {
			Attribute attribute = new Attribute(field.getName(), field.getVisibility(), field.getType(), null, field.getFullyQualifiedName()); 
			klass.addExternalAttribute(attribute);
		}
	}

	private void createInterface(Architecture architecture, TypeJava type) {
		Interface _interface = new Interface(null, type.getFullyQualifiedName());

		String packageName = type.getPackageName();
		if (packageName.isEmpty()) {
			architecture.addExternalInterface(_interface);
		} else {
			Package _package = architecture.findPackageByName(packageName);
			_package.addExternalInterface(_interface);
		}
		
		for (FieldJava field : type.getFields()) {
			Attribute attribute = new Attribute(field.getName(), field.getVisibility(), field.getType(), null, field.getFullyQualifiedName()); 
			_interface.addExternalAttribute(attribute);
		}
	}

	private void createPackages(Architecture architecture, List<TypeJava> allTypes) {
		for (TypeJava type : allTypes) {
			String packageName = type.getPackageName();
			if (!packageName.isEmpty()) {
				Package _package = architecture.findPackageByName(packageName);
				if (_package == null) {
					_package = new Package(null, packageName);
					architecture.addPackage(_package);
				}
			}
		}
	}

	private List<TypeJava> getAllTypes(List<String> sourcePaths) throws IOException {
		List<TypeJava> allTypes = new ArrayList<>();
		JavaFilesFinder sourceLoader = new JavaFilesFinder(sourcePaths);
		SourceFilesLoader compUnitLoader = new SourceFilesLoader(sourceLoader);
		List<SourceFileJava> sourceFiles = compUnitLoader.getLoadedSourceFiles();
		for (SourceFileJava sourceFile : sourceFiles) {
			for (TypeJava type : sourceFile.getTypes()) {
				allTypes.add(type);
			}
		}
		return allTypes;
	}

}
