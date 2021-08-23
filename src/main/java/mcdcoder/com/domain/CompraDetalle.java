package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompraDetalle.
 */
@Entity
@Table(name = "compra_detalle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compraDetalles" }, allowSetters = true)
    private Compra compra;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compraDetalles", "ventaDetalles", "imagens", "variantes", "subCategoria" }, allowSetters = true)
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompraDetalle id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public CompraDetalle cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public CompraDetalle precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getSubTotal() {
        return this.subTotal;
    }

    public CompraDetalle subTotal(Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Compra getCompra() {
        return this.compra;
    }

    public CompraDetalle compra(Compra compra) {
        this.setCompra(compra);
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public CompraDetalle producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompraDetalle)) {
            return false;
        }
        return id != null && id.equals(((CompraDetalle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompraDetalle{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            ", subTotal=" + getSubTotal() +
            "}";
    }
}
