package com.netent.databasetracing.deadlocks;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.netent.databasetracing.deadlocks.enums.LockModes;
import com.netent.databasetracing.deadlocks.enums.LockRequest;

public class ShowDeadlockApplet extends JApplet {

    private static final long serialVersionUID = 2202072534703043194L;

    private JGraphXAdapter<DatabaseProcess, DefaultEdge> jgxAdapter;

    private ListenableGraph<DatabaseProcess, DefaultEdge> graph;
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);


    /**
     * An alternative starting point for this demo, to also allow running this applet as an application.
     * 
     * @param args ignored.
     */
    public static void main(String[] args) {
        ListenableDirectedGraph<DatabaseProcess, DefaultEdge> g = new ListenableDirectedGraph<DatabaseProcess, DefaultEdge>(
                DefaultEdge.class);

        LockAction l1 = new LockAction("Customer", LockModes.S, 1000, LockRequest.Aqcuired);
        LockAction l2 = new LockAction("Order", LockModes.S, 2000, LockRequest.Aqcuired);
        LockAction l3 = new LockAction("Order", LockModes.X, 1000, LockRequest.Aqcuired);
        LockAction l4 = new LockAction("Customer", LockModes.X, 2000, LockRequest.Aqcuired);

        LockManager manager = new LockManager(g);
        manager.performLockAction(l1);
        manager.performLockAction(l4);

        manager.performLockAction(l2);
        manager.performLockAction(l3);

        showGraph(g);
    }


    public static void showGraph(ListenableDirectedGraph<DatabaseProcess, DefaultEdge> g) {
        ShowDeadlockApplet applet = new ShowDeadlockApplet(g);
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private ShowDeadlockApplet(ListenableGraph<DatabaseProcess, DefaultEdge> g) {
        super();
        this.graph = g;
    }


    public void init() {
        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<DatabaseProcess, DefaultEdge>(this.graph);

        getContentPane().add(new mxGraphComponent(jgxAdapter));
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
    }

}