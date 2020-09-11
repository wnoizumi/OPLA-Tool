package br.otimizes.oplatool.architecture.builders.javacode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import br.otimizes.oplatool.architecture.representation.Architecture;
import br.otimizes.oplatool.architecture.representation.Class;
import br.pucrio.opus.smells.resources.JavaFilesFinder;
import br.pucrio.opus.smells.resources.SourceFile;
import br.pucrio.opus.smells.resources.SourceFilesLoader;
import br.pucrio.opus.smells.resources.Type;

public class ArchitectureBuilderJava {
	
	public ArchitectureBuilderJava() {
		
	}
	

	public Architecture create(String projectName, List<String> sourcePaths) throws IOException {
		
		Architecture architecture = new Architecture(projectName);
		//TODO: Caso sejam desncessários, remover os tres comandos a seguir
		architecture.setProjectID(projectName);
		architecture.setProjectName(projectName);
		architecture.setProjectVersion("1");
		
		
		List<Type> allTypes = getAllTypes(sourcePaths);
		
		for (Type type : allTypes) {
			Class klass = new Class(null, type.getFullyQualifiedName(),type.isAbstract());
			architecture.addExternalClass(klass);
		}
		
		
		return architecture;
	}


	private List<Type> getAllTypes(List<String> sourcePaths) throws IOException {
		List<Type> allTypes = new ArrayList<>();
		JavaFilesFinder sourceLoader = new JavaFilesFinder(sourcePaths);
		SourceFilesLoader compUnitLoader = new SourceFilesLoader(sourceLoader);
		List<SourceFile> sourceFiles = compUnitLoader.getLoadedSourceFiles();
		for (SourceFile sourceFile : sourceFiles) {
			for (Type type : sourceFile.getTypes()) {
				allTypes.add(type);
			}
		}
		return allTypes;
	}
	
	

}
