package it.health.fsemate.requests;

import it.health.fsemate.requests.types.ComunicazioneMetadatiRequestFields;
import it.health.fsemate.requests.types.EsitoCaricamentoDocumentoRequestFields;
import it.health.fsemate.requests.types.RecuperoDocumentoRequestFields;
import it.health.fsemate.requests.types.RicercaDocumentiRequestFields;
import it.health.fsemate.user.User;
import it.health.fsemate.user.UserService;
import it.finanze.ini.fse.types.POCDMT000040ClinicalDocument;
import it.finanze.ini.fse.xsd.comunicazionemetadatiricevuta.ComunicazioneMetadatiRicevuta;
import it.finanze.ini.fse.xsd.comunicazionemetadatirichiesta.ComunicazioneMetadatiRichiesta;
import it.finanze.ini.fse.xsd.esitocaricamentodocumentoricevuta.EsitoCaricamentoDocumentoRicevuta;
import it.finanze.ini.fse.xsd.esitocaricamentodocumentorichiesta.EsitoCaricamentoDocumentoRichiesta;
import it.finanze.ini.fse.xsd.recuperodocumentoricevuta.RecuperoDocumentoRicevuta;
import it.finanze.ini.fse.xsd.recuperodocumentorichiesta.RecuperoDocumentoRichiesta;
import it.finanze.ini.fse.xsd.ricercadocumentiricevuta.RicercaDocumentiRicevuta;
import it.finanze.ini.fse.xsd.ricercadocumentirichiesta.RicercaDocumentiRichiesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Base64;

@Service
public class RequestsService {
  @Autowired
  private UserService userService;

  @Value("${xsl.location}")
  private String xslLocation;

  private ComunicazioneMetadatiRichiesta createComunicazioneMetadatiRichiesta(User user, ComunicazioneMetadatiRequestFields fields, byte[] encodedDocument) {
    ComunicazioneMetadatiRichiesta request = new ComunicazioneMetadatiRichiesta();
    request.setIdentificativoUtente(user.getCf());
    request.setPinCode(user.getPinCode());
    request.setIdentificativoOrganizzazione(user.getIdentificativoOrganizzazione());
    request.setStrutturaUtente(user.getStrutturaUtente());
    request.setRuoloUtente(user.getRuoloUtente());
    request.setContestoOperativo(fields.getContestoOperativo());
    request.setIdentificativoAssistito(fields.getIdentificativoAssistito());
    request.setPresaInCarico(fields.getPresaInCarico());
    request.setTipoAttivita(fields.getTipoAttivita());
    request.getMetadato().add(fields.getMetadato());
    request.setDocumento(encodedDocument);
    request.getOpzioniRequest().add(fields.getOpzioniRequest());
    return request;
  }

  private EsitoCaricamentoDocumentoRichiesta createEsitoCaricamentoDocumentoRichiesta(User user, EsitoCaricamentoDocumentoRequestFields fields) {
    EsitoCaricamentoDocumentoRichiesta request = new EsitoCaricamentoDocumentoRichiesta();
    request.setIdentificativoUtente(user.getCf());
    request.setPinCode(user.getPinCode());
    request.setIdentificativoOrganizzazione(user.getIdentificativoOrganizzazione());
    request.setStrutturaUtente(user.getStrutturaUtente());
    request.setRuoloUtente(user.getRuoloUtente());
    if (fields.getIdentificativiTemporaneiDocumenti() != null) {
      request.getIdentificativiTemporaneiDocumenti().addAll(fields.getIdentificativiTemporaneiDocumenti());
    }
    request.setDataRicercaDA(fields.getDataRicercaDA());
    request.setDataRicercaA(fields.getDataRicercaA());
    return request;
  }

  private RicercaDocumentiRichiesta createRicercaDocumentiRichiesta(User user, RicercaDocumentiRequestFields fields) {
    RicercaDocumentiRichiesta request = new RicercaDocumentiRichiesta();
    request.setIdentificativoUtente(user.getCf());
    request.setPinCode(user.getPinCode());
    request.setIdentificativoOrganizzazione(user.getIdentificativoOrganizzazione());
    request.setStrutturaUtente(user.getStrutturaUtente());
    request.setRuoloUtente(user.getRuoloUtente());
    request.setContestoOperativo(fields.getContestoOperativo());
    request.setIdentificativoAssistito(fields.getIdentificativoAssistito());
    request.setPresaInCarico(fields.getPresaInCarico());
    request.setDataRicercaDA(fields.getDataRicercaDA());
    request.setDataRicercaA(fields.getDataRicercaA());
    request.getIdentificativoDocumento().add(fields.getIdentificativoDocumento());
    request.setOpzioniRisposta(fields.getOpzioniRisposta());
    request.getOpzioniRequest().add(fields.getOpzioniRequest());
    return request;
  }

  private RecuperoDocumentoRichiesta createRecuperoDocumentoRichiesta(User user, RecuperoDocumentoRequestFields fields) {
    RecuperoDocumentoRichiesta request = new RecuperoDocumentoRichiesta();
    request.setIdentificativoUtente(user.getCf());
    request.setPinCode(user.getPinCode());
    request.setIdentificativoOrganizzazione(user.getIdentificativoOrganizzazione());
    request.setStrutturaUtente(user.getStrutturaUtente());
    request.setRuoloUtente(user.getRuoloUtente());
    request.setContestoOperativo(fields.getContestoOperativo());
    request.setIdentificativoAssistito(fields.getMetadato().getIdentificativoAssistito());
    request.setPresaInCarico(fields.getPresaInCarico());
    request.setIdentificativoOrgDoc(fields.getMetadato().getIdentificativoOrgDoc());
    request.setIdentificativoRepository(fields.getMetadato().getIdentificativoRepository());
    request.setIdentificativoDocumento(fields.getMetadato().getIdentificativoDocumento());
    return request;
  }

  public ComunicazioneMetadatiRicevuta sendDocument(ComunicazioneMetadatiRequestFields fields) throws Exception {
    StringWriter sw = new StringWriter();

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(POCDMT000040ClinicalDocument.class);

      Marshaller marshaller = jaxbContext.createMarshaller();

      // enable pretty-print XML output
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      // won't generate an xml declaration
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

      sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n").append("<?xml-stylesheet type=\"text/xsl\" href=\"").append(xslLocation).append("\"?>\n");

      marshaller.marshal(fields.getDocumento(), sw);
    } catch (Exception e) {
      e.printStackTrace();
    }

    byte[] encodedReport = Base64.getEncoder().encode(sw.toString().trim().getBytes());

    User user = userService.getUsers().get(0);
    ComunicazioneMetadati comunicazioneMetadati = new ComunicazioneMetadati(user);
    return comunicazioneMetadati.start(createComunicazioneMetadatiRichiesta(user, fields, encodedReport));
  }

  public EsitoCaricamentoDocumentoRicevuta getOutcome(EsitoCaricamentoDocumentoRequestFields fields) throws IOException {
    User user = userService.getUsers().get(0);
    EsitoCaricamentoDocumento esitoCaricamentoDocumento = new EsitoCaricamentoDocumento(user);
    return esitoCaricamentoDocumento.start(createEsitoCaricamentoDocumentoRichiesta(user, fields));
  }

  public RicercaDocumentiRicevuta getDocuments(RicercaDocumentiRequestFields fields) throws IOException {
    User user = userService.getUsers().get(0);
    RicercaDocumenti ricercaDocumenti = new RicercaDocumenti(user);
    return ricercaDocumenti.start(createRicercaDocumentiRichiesta(user, fields));
  }

  public Object getDocument(RecuperoDocumentoRequestFields fields) throws Exception {
    User user = userService.getUsers().get(0);
    RecuperoDocumento recuperoDocumento = new RecuperoDocumento(user);
    RecuperoDocumentoRicevuta recuperoDocumentoRicevuta = recuperoDocumento.start(createRecuperoDocumentoRichiesta(user, fields));
    return parseHL7Document(recuperoDocumentoRicevuta);
  }

  private Object parseHL7Document(RecuperoDocumentoRicevuta response) throws Exception {
    if (response.getDocumento() == null) return response;

    String document = new String(response.getDocumento());

    JAXBContext jaxbContext = JAXBContext.newInstance(POCDMT000040ClinicalDocument.class);

    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    unmarshaller.setEventHandler(event -> {
      throw new RuntimeException(event.getMessage(), event.getLinkedException());
    });

    return unmarshaller.unmarshal(new StringReader(document));
  }
}
