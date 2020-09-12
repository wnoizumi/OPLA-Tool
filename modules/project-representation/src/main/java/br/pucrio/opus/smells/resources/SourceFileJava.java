package br.pucrio.opus.smells.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import br.pucrio.opus.smells.ast.visitors.TypeDeclarationCollector;

public class SourceFileJava {

	private File file;
	
	private transient CompilationUnit compilationUnit;
	
	private transient List<TypeJava> types;
	
	public SourceFileJava(File file, CompilationUnit compilationUnit) {
		this.file = file;
		this.compilationUnit = compilationUnit;
		this.searchForTypes();
	}
	
	private void searchForTypes() {
		this.types = new ArrayList<>();
		TypeDeclarationCollector visitor = new TypeDeclarationCollector();
		this.compilationUnit.accept(visitor);
		List<TypeDeclaration> typeDeclarations = visitor.getNodesCollected();
		for (TypeDeclaration typeDeclaration : typeDeclarations) {
			TypeJava type = new TypeJava(this, typeDeclaration);
			this.types.add(type);
		}
	}
	
	public List<TypeJava> getTypes() {
		return types;
	}

	public File getFile() {
		return file;
	}

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

}
