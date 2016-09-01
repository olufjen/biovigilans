package no.naks.biovigilans.web.server.resource;

import org.restlet.resource.ServerResource;

/**
 * En test resurs
 * @author olj
 *
 */
public class RootServerResource extends ServerResource implements RootResource {

	@Override
	public String represent() {
		// TODO Auto-generated method stub
		return "Welcome to the " + getApplication().getName() + " !";
	}

}
