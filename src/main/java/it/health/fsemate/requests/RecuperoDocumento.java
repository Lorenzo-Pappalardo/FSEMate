package it.health.fsemate.requests;

import it.health.fsemate.user.User;
import it.finanze.ini.fse.wsdl.recuperodocumento.FseRecuperoDocumento;
import it.finanze.ini.fse.wsdl.recuperodocumento.RecuperoDocumentoPT;
import it.finanze.ini.fse.xsd.recuperodocumentoricevuta.RecuperoDocumentoRicevuta;
import it.finanze.ini.fse.xsd.recuperodocumentorichiesta.RecuperoDocumentoRichiesta;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class RecuperoDocumento extends SpecificRequest {
  private final User user;

  public RecuperoDocumento(User user) {
    super("RecuperoDocumento");
    this.user = user;
  }

  public RecuperoDocumentoRicevuta start(RecuperoDocumentoRichiesta request) throws IOException {
    httpsConnection.connect();

    URL wsdlURL = FseRecuperoDocumento.WSDL_LOCATION;

    FseRecuperoDocumento ss = new FseRecuperoDocumento(wsdlURL, SERVICE);
    RecuperoDocumentoPT port = ss.getFseRecuperoDocumento();

    Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
    requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, getRequestHeaders(user));

    System.out.println("Invoking RecuperoDocumento...");
    RecuperoDocumentoRicevuta recuperoDocumentoRicevuta = port.recuperoDocumento(request);

    httpsConnection.disconnect();

    return recuperoDocumentoRicevuta;
  }
}
