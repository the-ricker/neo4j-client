/**
 * 
 */
package org.neo4j.eclipse.ui.parts;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.neo4j.client.rest.RestGraphDatabaseFactory;


/**
 * @author Ricker
 *
 */
public class Neo4jDbViewer {
	

	@Inject
	private RestGraphDatabaseFactory dbFactory;
	
	@Inject
	public void init(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("Eclipse 4");
	}
	

	
	@Focus
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	



}
