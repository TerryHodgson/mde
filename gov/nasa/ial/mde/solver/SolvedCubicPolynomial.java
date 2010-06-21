package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.math.PNom;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedCubicPolynomial extends SolvedConic {


	protected String[] newFeatures = {};

	public SolvedCubicPolynomial(AnalyzedEquation equation) {
		super(equation);
		
		//??
		double[] coeffs = QC.getNormalizedCoefficients();
		
		IntervalXY D, R; // domain and range
		//PNom.getPNom(p, v) PNom.getDegree
		
		D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        D.setEndPointExclusions(IntervalXY.EXCLUDE_LOW_X | IntervalXY.EXCLUDE_HIGH_X);
        R = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        R.setEndPointExclusions(IntervalXY.EXCLUDE_LOW_X | IntervalXY.EXCLUDE_HIGH_X);
        putFeature("domain", D);
        putFeature("range", R);
        
	}
	
	public static void main(String[] args){
		MdeSettings currentSettings = new MdeSettings("myAppsMdeProperties");
        Solver solver = new Solver();
        Describer describer = new Describer(solver, currentSettings);
        describer.setOutputFormat(Describer.TEXT_OUTPUT);
        
        String inputEquation = "y = x^3 + 2x^2 + x - 2";
     // Give Solver equation and solve
        solver.add(inputEquation);
        solver.solve();
	}
}