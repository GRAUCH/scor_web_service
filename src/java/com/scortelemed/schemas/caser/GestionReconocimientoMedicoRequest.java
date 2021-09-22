
package com.scortelemed.schemas.caser;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <xs:complexType name="gestionReconocimientoMedicoRequest">
 * 	<xs:sequence>
 * 		<xs:element maxOccurs="unbounded" name="ReconocimientosMedicos" type="tns:reconocimientoMedico"/>
 * 	</xs:sequence>
 * </xs:complexType>
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gestionReconocimientoMedicoRequest", propOrder = {
    "reconocimientosMedicos"
})
public class GestionReconocimientoMedicoRequest {
	@XmlElement(name = "ReconocimientosMedicos", required = true)
    protected List<ReconocimientoMedico> reconocimientosMedicos;

	/**
	 * Returns a list of ReconocimientoMedico (for caserInfantil)
	 * 
	 * @return List of {@link ReconocimientoMedico }
	 */
	public List<ReconocimientoMedico> getReconocimientosMedicos() {
		return reconocimientosMedicos;
	}
	
}