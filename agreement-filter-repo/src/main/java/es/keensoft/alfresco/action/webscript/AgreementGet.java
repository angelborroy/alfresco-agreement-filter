package es.keensoft.alfresco.action.webscript;

import java.io.IOException;
import java.io.Serializable;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.json.simple.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.MediaType;

public class AgreementGet extends AbstractWebScript {
	
	private PersonService personService;
	private NodeService nodeService;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
		
		String username = req.getExtensionPath();
		
		if (username == null || username.equals("")) {
			throw new WebScriptException("User name is required!");
		}

		// Accessing agreementChecked node property as residual
		NodeRef userNodeRef = personService.getPerson(username, false);
		Serializable agreementChecked = nodeService.getProperty(userNodeRef, QName.createQName("agreementChecked"));

    	JSONObject objProcess = new JSONObject();
    	objProcess.put("agreementChecked", agreementChecked);
		
    	String jsonString = objProcess.toString();
    	res.setContentType(MediaType.APPLICATION_JSON.toString());
    	res.setContentEncoding("UTF-8");
    	res.getWriter().write(jsonString);
		
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}