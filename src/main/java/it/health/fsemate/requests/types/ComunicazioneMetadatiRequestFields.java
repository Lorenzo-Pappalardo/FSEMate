package it.health.fsemate.requests.types;

import it.finanze.ini.fse.types.POCDMT000040ClinicalDocument;
import it.finanze.ini.fse.xsd.tipodaticomunicazionemetadati.MetadatoType;
import it.finanze.ini.fse.xsd.tipodaticomunicazionemetadati.OpzioniType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ComunicazioneMetadatiRequestFields {
  @NotBlank
  private String contestoOperativo;

  @NotBlank
  private String identificativoAssistito;

  @NotBlank
  private String presaInCarico;

  @NotBlank
  private String tipoAttivita;

  private MetadatoType metadato;

  private POCDMT000040ClinicalDocument documento;

  private OpzioniType opzioniRequest;
}
