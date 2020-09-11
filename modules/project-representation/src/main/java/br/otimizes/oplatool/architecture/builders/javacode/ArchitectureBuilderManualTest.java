package br.otimizes.oplatool.architecture.builders.javacode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import br.otimizes.oplatool.architecture.representation.Architecture;
import br.otimizes.oplatool.architecture.representation.Class;

public class ArchitectureBuilderManualTest {

	public static void main(String[] args) {
		ArchitectureBuilderJava builder  = new ArchitectureBuilderJava();
		
		List<String> sourcePaths = Arrays.asList("C:\\Users\\willi\\Documents\\repositories\\Exemplos-POO-IFPR\\Basicos-Java\\src");
		try {
			Architecture architecture = builder.create("organic", sourcePaths);
			for (Class c : architecture.getAllClasses()) {
				System.out.println(c.getName());
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
