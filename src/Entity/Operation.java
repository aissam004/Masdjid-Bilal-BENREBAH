/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o"),
    @NamedQuery(name = "Operation.findByIdOperation", query = "SELECT o FROM Operation o WHERE o.idOperation = :idOperation"),
    @NamedQuery(name = "Operation.findByDescription", query = "SELECT o FROM Operation o WHERE o.description = :description"),
    @NamedQuery(name = "Operation.findByMontantOperation", query = "SELECT o FROM Operation o WHERE o.montantOperation = :montantOperation"),
    @NamedQuery(name = "Operation.findByDateOperation", query = "SELECT o FROM Operation o WHERE o.dateOperation = :dateOperation"),
    @NamedQuery(name = "Operation.findByReference", query = "SELECT o FROM Operation o WHERE o.reference = :reference"),
    @NamedQuery(name = "Operation.findByTimestampOperation", query = "SELECT o FROM Operation o WHERE o.timestampOperation = :timestampOperation"),
    @NamedQuery(name = "Operation.findByIdCompte", query = "SELECT o FROM Operation o WHERE o.idCompte = :idCompte")})
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_operation")
    @TableGenerator(name="sqlite_operation", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="operation",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_operation")
    private Integer idOperation;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_operation")
    private Double montantOperation;
    @Column(name = "date_operation")
    private String dateOperation;
    @Column(name = "reference")
    private String reference;
    @Column(name = "timestamp_operation")
    private String timestampOperation;
    @Column(name = "id_compte")
    private Integer idCompte;
    @JoinColumn(name = "id_type_operation", referencedColumnName = "id_type_transaction")
    @ManyToOne
    private TypeTransaction idTypeOperation;
    @JoinColumn(name = "id_personne", referencedColumnName = "id_personne")
    @ManyToOne
    private Personne idPersonne;

    public Operation() {
    }

    public Operation(Integer idOperation) {
        this.idOperation = idOperation;
    }

    public Integer getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Integer idOperation) {
        this.idOperation = idOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontantOperation() {
        return montantOperation;
    }

    public void setMontantOperation(Double montantOperation) {
        this.montantOperation = montantOperation;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTimestampOperation() {
        return timestampOperation;
    }

    public void setTimestampOperation(String timestampOperation) {
        this.timestampOperation = timestampOperation;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public TypeTransaction getIdTypeOperation() {
        return idTypeOperation;
    }

    public void setIdTypeOperation(TypeTransaction idTypeOperation) {
        this.idTypeOperation = idTypeOperation;
    }

    public Personne getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Personne idPersonne) {
        this.idPersonne = idPersonne;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperation != null ? idOperation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.idOperation == null && other.idOperation != null) || (this.idOperation != null && !this.idOperation.equals(other.idOperation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Operation[ idOperation=" + idOperation + " ]";
    }
    
}
