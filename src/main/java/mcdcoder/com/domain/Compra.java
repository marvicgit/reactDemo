package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "compra")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compra", "producto" }, allowSetters = true)
    private Set<CompraDetalle> compraDetalles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Compra fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return this.total;
    }

    public Compra total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<CompraDetalle> getCompraDetalles() {
        return this.compraDetalles;
    }

    public Compra compraDetalles(Set<CompraDetalle> compraDetalles) {
        this.setCompraDetalles(compraDetalles);
        return this;
    }

    public Compra addCompraDetalle(CompraDetalle compraDetalle) {
        this.compraDetalles.add(compraDetalle);
        compraDetalle.setCompra(this);
        return this;
    }

    public Compra removeCompraDetalle(CompraDetalle compraDetalle) {
        this.compraDetalles.remove(compraDetalle);
        compraDetalle.setCompra(null);
        return this;
    }

    public void setCompraDetalles(Set<CompraDetalle> compraDetalles) {
        if (this.compraDetalles != null) {
            this.compraDetalles.forEach(i -> i.setCompra(null));
        }
        if (compraDetalles != null) {
            compraDetalles.forEach(i -> i.setCompra(this));
        }
        this.compraDetalles = compraDetalles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
