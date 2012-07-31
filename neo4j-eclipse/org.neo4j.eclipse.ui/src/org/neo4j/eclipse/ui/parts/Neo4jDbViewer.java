/**
 * 
 */
package org.neo4j.eclipse.ui.parts;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;


/**
 * @author Ricker
 *
 */
public class Neo4jDbViewer extends ViewPart {

	private GraphViewer graph;
	
	
	@Override
	public void createPartControl(Composite parent) {
		 graph = new GraphViewer(parent, SWT.NONE);


		
	}

	@Override
	public void setFocus() {
		graph.getControl().setFocus();
	}
	


}
