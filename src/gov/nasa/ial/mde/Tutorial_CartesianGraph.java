package gov.nasa.ial.mde;
/* 
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 * 
 * Created on Sep 3, 2004
 *
 * @author Terry Hodgson
 */

import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;
import gov.nasa.ial.mde.ui.graph.CartesianGraph;

import javax.swing.JFrame;

public class Tutorial_CartesianGraph {

    public static void main(String[] args) {
        // MDE Init:
        MdeSettings currentSettings = new MdeSettings("myAppsMdeProperties");
        Solver solver = new Solver();

        // Create a Java Swing window for our graph:
        JFrame window = new JFrame("Tutorial_CartesianGraph");
        // Create an MDE CartesianGraph instance:
        CartesianGraph grapher = new CartesianGraph(solver, currentSettings);

        // Add our graph panel to the window.
        window.getContentPane().add(grapher);
        window.pack();
        window.setVisible(true);
        window.toFront();

        // Give Solver an equation attempt to solve:
        String equation = "y=10*sin(0.01*x)";
        solver.add(equation);
        solver.solve();

        // If our equation is graphable, draw the graph.
        if (solver.anyGraphable()) {
            grapher.drawGraph();
        } else {
            System.out.println("MDE could not generate a graph for " + equation + ".");
        }
    } // end main

} // end class Tutorial_CartesianGraph
