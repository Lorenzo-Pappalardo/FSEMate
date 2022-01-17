package it.health.fsemate.requests;

import it.health.fsemate.user.User;
import it.finanze.ini.fse.wsdl.ricercadocumenti.FseRicercaDocumenti;
import it.finanze.ini.fse.wsdl.ricercadocumenti.RicercaDocumentiPT;
import it.finanze.ini.fse.xsd.ricercadocumentiricevuta.RicercaDocumentiRicevuta;
import it.finanze.ini.fse.xsd.ricercadocumentirichiesta.RicercaDocumentiRichiesta;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class RicercaDocumenti extends SpecificRequest {
  private final User user;

  public RicercaDocumenti(User user) {
    super("RicercaDocumenti");
    this.user = user;
  }

  public RicercaDocumentiRicevuta start(RicercaDocumentiRichiesta request) throws IOException {
    httpsConnection.connect();

    URL wsdlURL = FseRicercaDocumenti.WSDL_LOCATION;

    FseRicercaDocumenti ss = new FseRicercaDocumenti(wsdlURL, SERVICE);
    RicercaDocumentiPT port = ss.getFseRicercaDocumenti();

    Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
    requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, getRequestHeaders(user));

    System.out.println("Invoking RicercaDocumenti...");
    RicercaDocumentiRicevuta ricercaDocumentiRicevuta = port.ricercaDocumenti(request);

    httpsConnection.disconnect();

    return ricercaDocumentiRicevuta;
  }
}
