package it.health.fsemate.requests.types;

import it.finanze.ini.fse.xsd.tipodatiricercadocumenti.OpzioniType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RicercaDocumentiRequestFields {
  @NotBlank
  private String contestoOperativo;

  @NotBlank
  private String identificativoAssistito;

  @NotBlank
  private String presaInCarico;

  private String dataRicercaDA;

  private String dataRicercaA;

  private String identificativoDocumento;

  private String opzioniRisposta;

  private OpzioniType opzioniRequest;
}
