package br.pucrio.opus.smells.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.smells.metrics.MetricName;
import br.pucrio.opus.smells.resources.MethodJava;
import br.pucrio.opus.smells.resources.ResourceJava;
import br.pucrio.opus.smells.resources.TypeJava;

/**
 * Complex class: A class having at least one method for which McCabe cyclomatic complexity is higher than 10.
 * @author Diego Cedrim
 */
public class ComplexClass  extends SmellDetector {
	
	@Override
	public List<Smell> detect(ResourceJava resource) {
		TypeJava type = (TypeJava)resource;
		for (MethodJava method : type.getMethods()) {
			Double cc = method.getMetricValue(MetricName.CC);
			if (cc != null && cc > 10) {
				StringBuilder builder = new StringBuilder();
				builder.append("CC = " + cc);
				
				Smell smell = super.createSmell(resource);
				smell.setReason(builder.toString());
				return Arrays.asList(smell);
			}
		}
		
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.ComplexClass;
	}

}