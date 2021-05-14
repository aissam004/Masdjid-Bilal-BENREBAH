/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "type_transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeTransaction.findAll", query = "SELECT t FROM TypeTransaction t"),
    @NamedQuery(name = "TypeTransaction.findByIdTypeTransaction", query = "SELECT t FROM TypeTransaction t WHERE t.idTypeTransaction = :idTypeTransaction"),
    @NamedQuery(name = "TypeTransaction.findByTypeTransaction", query = "SELECT t FROM TypeTransaction t WHERE t.typeTransaction = :typeTransaction")})
public class TypeTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     @GeneratedValue(strategy = GenerationType.TABLE,generator="sqlite_type_transaction")
    @TableGenerator(name="sqlite_type_transaction", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="type_transaction", allocationSize = 1,initialValue = 1)
    @Basic(optional = false)
    @Column(name = "id_type_transaction")
    private Integer idTypeTransaction;
    @Column(name = "type_transaction")
    private String typeTransaction;
    @OneToMany(mappedBy = "idTypeOperation")
    private Collection<Operation> operationCollection;

    public TypeTransaction() {
    }

    public TypeTransaction(Integer idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public Integer getIdTypeTransaction() {
        return idTypeTransaction;
    }

    public void setIdTypeTransaction(Integer idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeTransaction != null ? idTypeTransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeTransaction)) {
            return false;
        }
        TypeTransaction other = (TypeTransaction) object;
        if ((this.idTypeTransaction == null && other.idTypeTransaction != null) || (this.idTypeTransaction != null && !this.idTypeTransaction.equals(other.idTypeTransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.TypeTransaction[ idTypeTransaction=" + idTypeTransaction + " ]";
    }
    
}
