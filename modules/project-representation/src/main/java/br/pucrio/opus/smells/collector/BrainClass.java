package br.pucrio.opus.smells.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.pucrio.opus.smells.metrics.MetricName;
import br.pucrio.opus.smells.metrics.Thresholds;
import br.pucrio.opus.smells.resources.MethodJava;
import br.pucrio.opus.smells.resources.ResourceJava;
import br.pucrio.opus.smells.resources.TypeJava;

/**
 * This design disharmony is about complex classes that tend to accumulate 
 * an excessive amount of intelligence, usually in form of several methods 
 * affected by Brain Method
 * 
 * For performance reasons, the class assumes that all methods' smells were collected before. 
 * 
 * @author Diego Cedrim
 */
public class BrainClass extends SmellDetector {
	
	private List<MethodJava> getBrainMethods(TypeJava type) {
		List<MethodJava> brainMethods = new ArrayList<>();
		for (MethodJava method : type.getMethods()) {
			if (method.hasSmell(SmellName.BrainMethod)) {
				brainMethods.add(method);
			}
		}
		return brainMethods;
	}
	
	@Override
	public List<Smell> detect(ResourceJava resource) {
		Double veryHighCloc = Thresholds.getVeryHighThreshold(MetricName.CLOC);
		Double veryHighWMC = Thresholds.getVeryHighThreshold(MetricName.WMC);
		
		
		TypeJava type = (TypeJava)resource;
		Integer brainMethodCount = this.getBrainMethods(type).size();
		Double loc = resource.getMetricValue(MetricName.CLOC);
		Double wmc = resource.getMetricValue(MetricName.WMC);
		Double tcc = resource.getMetricValue(MetricName.TCC);
		
		boolean moreThanOneBrainMethodAndVeryLarge = brainMethodCount > 1 && loc >= veryHighCloc;
		boolean oneBrainMethodAndVeryComplex = brainMethodCount == 1 && loc >= 2*veryHighCloc && wmc >= 2*veryHighWMC;
		boolean veryComplexAndNonCohesive =  wmc >= veryHighWMC && tcc < Thresholds.HALF;
		
		if ((moreThanOneBrainMethodAndVeryLarge || oneBrainMethodAndVeryComplex) &&  veryComplexAndNonCohesive) {
			StringBuilder builder = new StringBuilder();
			builder.append("BRAIN_METHODS = " + brainMethodCount);
			builder.append(", WMC = " + wmc);
			builder.append(", TCC = " + tcc);
			
			Smell smell = super.createSmell(resource);
			smell.setReason(builder.toString());
			return Arrays.asList(smell);
		}
		return new ArrayList<>();
	}
	
	@Override
	protected SmellName getSmellName() {
		return SmellName.BrainClass;
	}

}
