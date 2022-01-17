package it.health.fsemate.requests;

import it.health.fsemate.user.User;
import it.finanze.ini.fse.wsdl.esitocaricamentodocumento.EsitoCaricamentoDocumentoPT;
import it.finanze.ini.fse.wsdl.esitocaricamentodocumento.FseEsitoCaricamentoDocumento;
import it.finanze.ini.fse.xsd.esitocaricamentodocumentoricevuta.EsitoCaricamentoDocumentoRicevuta;
import it.finanze.ini.fse.xsd.esitocaricamentodocumentorichiesta.EsitoCaricamentoDocumentoRichiesta;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class EsitoCaricamentoDocumento extends SpecificRequest {
  private final User user;

  public EsitoCaricamentoDocumento(User user) {
    super("EsitoCaricamentoDocumento");
    this.user = user;
  }

  public EsitoCaricamentoDocumentoRicevuta start(EsitoCaricamentoDocumentoRichiesta request) throws IOException {
    httpsConnection.connect();

    URL wsdlURL = FseEsitoCaricamentoDocumento.WSDL_LOCATION;

    FseEsitoCaricamentoDocumento ss = new FseEsitoCaricamentoDocumento(wsdlURL, SERVICE);
    EsitoCaricamentoDocumentoPT port = ss.getFseEsitoCaricamentoDocumento();

    Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
    requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, getRequestHeaders(user));

    System.out.println("Invoking EsitoCaricamentoDocumento...");
    EsitoCaricamentoDocumentoRicevuta esitoCaricamentoDocumentoRicevuta = port.esitoCaricamentoDocumento(request);

    httpsConnection.disconnect();

    return esitoCaricamentoDocumentoRicevuta;
  }
}
