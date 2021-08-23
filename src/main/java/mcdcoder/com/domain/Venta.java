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
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "venta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "producto" }, allowSetters = true)
    private Set<VentaDetalle> ventaDetalles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "tarjetas", "ventas" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Venta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getDescuento() {
        return this.descuento;
    }

    public Venta descuento(Double descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return this.total;
    }

    public Venta total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<VentaDetalle> getVentaDetalles() {
        return this.ventaDetalles;
    }

    public Venta ventaDetalles(Set<VentaDetalle> ventaDetalles) {
        this.setVentaDetalles(ventaDetalles);
        return this;
    }

    public Venta addVentaDetalle(VentaDetalle ventaDetalle) {
        this.ventaDetalles.add(ventaDetalle);
        ventaDetalle.setVenta(this);
        return this;
    }

    public Venta removeVentaDetalle(VentaDetalle ventaDetalle) {
        this.ventaDetalles.remove(ventaDetalle);
        ventaDetalle.setVenta(null);
        return this;
    }

    public void setVentaDetalles(Set<VentaDetalle> ventaDetalles) {
        if (this.ventaDetalles != null) {
            this.ventaDetalles.forEach(i -> i.setVenta(null));
        }
        if (ventaDetalles != null) {
            ventaDetalles.forEach(i -> i.setVenta(this));
        }
        this.ventaDetalles = ventaDetalles;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Venta cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descuento=" + getDescuento() +
            ", total=" + getTotal() +
            "}";
    }
}
