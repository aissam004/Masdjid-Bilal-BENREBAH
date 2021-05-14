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
@Table(name = "cotisation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotisation.findAll", query = "SELECT c FROM Cotisation c"),
    @NamedQuery(name = "Cotisation.findByIdCotisation", query = "SELECT c FROM Cotisation c WHERE c.idCotisation = :idCotisation"),
    @NamedQuery(name = "Cotisation.findByDateCotisation", query = "SELECT c FROM Cotisation c WHERE c.dateCotisation = :dateCotisation"),
    @NamedQuery(name = "Cotisation.findByMontantCotisation", query = "SELECT c FROM Cotisation c WHERE c.montantCotisation = :montantCotisation"),
    @NamedQuery(name = "Cotisation.findByRefTransaction", query = "SELECT c FROM Cotisation c WHERE c.refTransaction = :refTransaction"),
    @NamedQuery(name = "Cotisation.findByTimestampCotisation", query = "SELECT c FROM Cotisation c WHERE c.timestampCotisation = :timestampCotisation")})
public class Cotisation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_cotisation")
    @TableGenerator(name="sqlite_cotisation", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="cotisation",allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_cotisation")
    private Integer idCotisation;
    @Column(name = "date_cotisation")
    private String dateCotisation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_cotisation")
    private Double montantCotisation;
    @Column(name = "ref_transaction")
    private Integer refTransaction;
    @Column(name = "timestamp_cotisation")
    private String timestampCotisation;
    @JoinColumn(name = "id_personne", referencedColumnName = "id_personne")
    @ManyToOne
    private Personne idPersonne;

    public Cotisation() {
    }

    public Cotisation(Integer idCotisation) {
        this.idCotisation = idCotisation;
    }

    public Integer getIdCotisation() {
        return idCotisation;
    }

    public void setIdCotisation(Integer idCotisation) {
        this.idCotisation = idCotisation;
    }

    public String getDateCotisation() {
        return dateCotisation;
    }

    public void setDateCotisation(String dateCotisation) {
        this.dateCotisation = dateCotisation;
    }

    public Double getMontantCotisation() {
        return montantCotisation;
    }

    public void setMontantCotisation(Double montantCotisation) {
        this.montantCotisation = montantCotisation;
    }

    public Integer getRefTransaction() {
        return refTransaction;
    }

    public void setRefTransaction(Integer refTransaction) {
        this.refTransaction = refTransaction;
    }

    public String getTimestampCotisation() {
        return timestampCotisation;
    }

    public void setTimestampCotisation(String timestampCotisation) {
        this.timestampCotisation = timestampCotisation;
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
        hash += (idCotisation != null ? idCotisation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotisation)) {
            return false;
        }
        Cotisation other = (Cotisation) object;
        if ((this.idCotisation == null && other.idCotisation != null) || (this.idCotisation != null && !this.idCotisation.equals(other.idCotisation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Cotisation[ idCotisation=" + idCotisation + " ]";
    }
    
}
