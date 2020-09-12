package br.pucrio.opus.smells.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import br.pucrio.opus.smells.resources.SourceFileJava;

public class SourceFileASTRequestor extends FileASTRequestor {
	
	private List<SourceFileJava> sourceFiles;
	
	public SourceFileASTRequestor() {
		sourceFiles = new ArrayList<>();
	}

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		this.sourceFiles.add(new SourceFileJava(new File(sourceFilePath), ast));
	}
	
	public List<SourceFileJava> getSourceFiles() {
		return sourceFiles;
	}
}
