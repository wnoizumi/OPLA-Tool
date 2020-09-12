package br.pucrio.opus.smells.resources;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldJava extends ResourceJava {

	private String name;

	public FieldJava(SourceFileJava sourceFile, VariableDeclarationFragment node) {
		super(sourceFile, node);
		String fieldName = node.getName().toString();
		this.setName(fieldName);
		
		IBinding binding = node.resolveBinding();
		if (binding != null) {
			IVariableBinding variableBinding = (IVariableBinding)binding;
			String classFqn = variableBinding.getDeclaringClass().getQualifiedName();
			setFullyQualifiedName(classFqn + "." + fieldName);
		}
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	protected void identifyKind() {
		VariableDeclarationFragment declaration = (VariableDeclarationFragment)this.getNode();
		StringBuffer buffer = new StringBuffer();
		FieldDeclaration parent = (FieldDeclaration) declaration.getParent();
		int modifiers = parent.getModifiers(); 
		
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
		
		buffer.append("field");
		this.setKind(buffer.toString());		
	}

	public String getVisibility() {
		VariableDeclarationFragment declaration = (VariableDeclarationFragment)this.getNode();
		FieldDeclaration parent = (FieldDeclaration) declaration.getParent();
		int modifiers = parent.getModifiers(); 
		
		if (Modifier.isPublic(modifiers)) {
			return "public";
		}
		
		if (Modifier.isPrivate(modifiers)) {
			return "private";
		}
		
		if (Modifier.isProtected(modifiers)) {
			return "protected";
		}
		
		return "default";
	}

	public String getType() {
		VariableDeclarationFragment declaration = (VariableDeclarationFragment)this.getNode();
		FieldDeclaration parent = (FieldDeclaration) declaration.getParent();
		Type type = parent.getType();
		int extraDimensions = declaration.getExtraDimensions();
		String typeName = type.toString();
		if (extraDimensions > 0) {
			for (int i = 1; i <= extraDimensions; i++) {
				typeName += "[]";
			}
		}
		return typeName;
	}

	public String getName() {
		return name;
	}
}
