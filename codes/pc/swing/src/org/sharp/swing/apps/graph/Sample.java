package org.sharp.swing.apps.graph;

import java.awt.Container;

import org.sharp.intf.Pluggable;
import org.sharp.utils.legacy.JGraphUtils;

public class Sample implements Pluggable {

	public Container getUi(){
		/*GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		
		DefaultGraphCell[] cells = new DefaultGraphCell[3];
		cells[0] = new DefaultGraphCell(new String("Hello"));
		GraphConstants.setBounds(cells[0].getAttributes(),
				new Rectangle2D.Double(20, 20, 40, 20));
		GraphConstants.setGradientColor(cells[0].getAttributes(), Color.orange);
		GraphConstants.setOpaque(cells[0].getAttributes(), true);
		
		DefaultPort port0 = new DefaultPort();
		cells[0].add(port0);
		
		cells[1] = new DefaultGraphCell(new String("World"));
		GraphConstants.setBounds(cells[1].getAttributes(),
				new Rectangle2D.Double(140, 140, 40, 20));
		GraphConstants.setGradientColor(cells[1].getAttributes(), Color.red);
		GraphConstants.setOpaque(cells[1].getAttributes(), true);
		
		DefaultPort port1 = new DefaultPort();
		cells[1].add(port1);
		
		DefaultEdge edge = new DefaultEdge();
		edge.setSource(cells[0].getChildAt(0));
		edge.setTarget(cells[1].getChildAt(0));
		cells[2] = edge;
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edge.getAttributes(), arrow);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		
		graph.getGraphLayoutCache().insert(cells);
		
		return new JScrollPane(graph);*/
		return JGraphUtils.helloWorldGraphComponent();
	}
	
	public AppLifeCycle lifeCycle() {
		return null;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI() {
			public Container getUI() {
				return getUi();
			}
			public String tabName() {
				return "Graph";
			}
			public String tabDescription() {
				return "Graph";
			}
		};
	}
}