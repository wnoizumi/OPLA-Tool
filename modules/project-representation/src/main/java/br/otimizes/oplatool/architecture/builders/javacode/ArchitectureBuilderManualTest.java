package br.otimizes.oplatool.architecture.builders.javacode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.otimizes.oplatool.architecture.representation.Architecture;
import br.otimizes.oplatool.architecture.representation.Class;

public class ArchitectureBuilderManualTest {

	public static void main(String[] args) {
		ArchitectureBuilderJava builder  = new ArchitectureBuilderJava();
		
		List<String> sourcePaths = Arrays.asList("C:\\Users\\willi\\Documents\\repositories\\JDeodorant\\src");
		try {
			Architecture architecture = builder.create("JDeodorant", sourcePaths);
			
			System.out.println("Packages:");
			for (br.otimizes.oplatool.architecture.representation.Package pkg : architecture.getAllPackages()) {
				System.out.println(pkg.getName());
			}
			System.out.println("------------------------------------------------");
			System.out.println("Classes:");
			for (Class c : architecture.getAllClasses()) {
				System.out.println(c.getName());
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
