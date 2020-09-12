package br.pucrio.opus.smells.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import br.pucrio.opus.smells.ast.visitors.MethodInvocationVisitor;
import br.pucrio.opus.smells.graph.CallGraph;

public class MethodJava extends ResourceJava {
	
	private List<String> parametersTypes;
	
	public IMethodBinding getBinding() {
		MethodDeclaration declaration = (MethodDeclaration)this.getNode(); 
		IMethodBinding binding = declaration.resolveBinding();
		return binding;
	}
	
	protected void identifyKind() {
		MethodDeclaration declaration = (MethodDeclaration)this.getNode();
		StringBuffer buffer = new StringBuffer();
		int modifiers = declaration.getModifiers(); 
		
		if (Modifier.isPublic(modifiers)) {
			buffer.append("public ");
		}
		
		if (Modifier.isPrivate(modifiers)) {
			buffer.append("private ");
		}
		
		if (Modifier.isProtected(modifiers)) {
			buffer.append("protected ");
		}
		
		if (Modifier.isStatic(modifiers)) {
			buffer.append("static ");
		}
		
		if (Modifier.isAbstract(modifiers)) {
			buffer.append("abstract ");
		}
		
		if (Modifier.isFinal(modifiers)) {
			buffer.append("final ");
		}
		
		buffer.append("method");
		this.setKind(buffer.toString());
	}

	/**
	 * Every time a new method is declared, it must be
	 * registered in the call Graph
	 */
	private void registerOnCallGraph(MethodDeclaration node) {
		CallGraph graph = CallGraph.getInstance();
		IMethodBinding thisBinding = this.getBinding();
		if (thisBinding == null) {
			//TODO LOG!
			return;
		}
		
		/*
		 * Retrieves the list of method calls made by the new
		 * declared method
		 */
		MethodInvocationVisitor invocationVisitor = new MethodInvocationVisitor();
		node.accept(invocationVisitor);
		for (IMethodBinding methodBinding : invocationVisitor.getCalls()) {
			graph.addMethodCall(thisBinding, methodBinding);
		}
		
	}
	
	public MethodJava(SourceFileJava sourceFile, MethodDeclaration node) {
		super(sourceFile, node);
		this.registerOnCallGraph(node);
		
		this.parametersTypes = new ArrayList<>();
		for(Object obj : node.parameters()) {
			SingleVariableDeclaration declaration = (SingleVariableDeclaration)obj;
			declaration.getName();
			parametersTypes.add(declaration.getType().toString());
		}
		
		IBinding binding = node.resolveBinding();
		if (binding != null) {
			IMethodBinding methodBinding = (IMethodBinding)binding;
			String classFqn = methodBinding.getDeclaringClass().getQualifiedName();
			setFullyQualifiedName(classFqn + "." + node.getName());
		}
	}
	
	public List<String> getParametersTypes() {
		return parametersTypes;
	}
	
	public List<SingleVariableDeclaration> getParametersJava() {
		return ((MethodDeclaration)getNode()).parameters();
	}
	
	@Override
	public String toString() {
		return "Method [fqn=" + getFullyQualifiedName() + "]";
	}

	public String getName() {
		MethodDeclaration declaration = (MethodDeclaration)this.getNode();
		return declaration.getName().toString();
	}

	public String getReturnType() {
		MethodDeclaration declaration = (MethodDeclaration)this.getNode();
		if (declaration.getReturnType2() != null) {
			ITypeBinding typeBinding = declaration.getReturnType2().resolveBinding();
			return typeBinding.getQualifiedName();
		}
		else
			return "void";
	}

	public boolean isAbstract() {
		MethodDeclaration declaration = (MethodDeclaration)this.getNode();
		
		if (declaration.getBody() == null)
			return true;
		
		int modifiers = declaration.getModifiers(); 
		
		if (Modifier.isAbstract(modifiers)) {
			return true;
		}
		
		return false;
	}
	
}
