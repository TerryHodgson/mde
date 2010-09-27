package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.solver.features.GraphFeature;

public interface PhaseFeature extends GraphFeature{
	public static String PATH = GraphFeature.GRAPH_DATA_PATH;
	public static String KEY = "phase";
	
	public String getPhase();
}
