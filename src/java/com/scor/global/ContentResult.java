package com.scor.global;


import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.WebServiceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

import org.apache.commons.io.IOUtils;

public class ContentResult {
	
	public byte[] obtenerFichero(String nodoAlfrescoDocumento) throws AuthenticationFault {

		WebServiceFactory.setEndpointAddress("http://172.17.0.32:8080/alfresco/api");
		AuthenticationUtils.startSession("generico", "pR8x5hqD");

		Store store = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
		byte[] contentByte = null;
		
		try {
			ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
			String uuid = "549eb5e6-7242-4adc-b8f8-27a4bb29556f";

			Reference contentReference = new Reference(store, uuid, null);
			Content[] readResult = null;

			try {
				readResult = contentService.read(new Predicate(new Reference[] { contentReference }, store, null), Constants.PROP_CONTENT);
			} catch (ContentFault e) {
				e.printStackTrace();
				System.out.println(e.toString());
			} catch (RemoteException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}


			if ((readResult != null) && (readResult[0] != null)) {
				Content content = readResult[0];
				ContentFormat cnf = content.getFormat();
				Reference ref = content.getNode();

				String[] splitedUrl = content.getUrl().split("/");
				String name = splitedUrl[splitedUrl.length - 1];

				InputStream is = ContentUtils.getContentAsInputStream(content);
				contentByte = IOUtils.toByteArray(is);

				try {
					Path path = Paths.get("c:/temp/"+name);
					Files.write(path, contentByte);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e.toString());
				}

				System.out.println(contentByte);

				System.out.println(" document has been retrieved");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			// End the session
			AuthenticationUtils.endSession();

			// System.exit(0);
		}
		
		return contentByte;
	}

}
