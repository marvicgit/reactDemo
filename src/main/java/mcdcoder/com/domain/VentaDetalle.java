package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VentaDetalle.
 */
@Entity
@Table(name = "venta_detalle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VentaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "talla")
    private String talla;

    @Column(name = "color")
    private String color;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "precio")
    private Double precio;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ventaDetalles", "cliente" }, allowSetters = true)
    private Venta venta;

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

    public VentaDetalle id(Long id) {
        this.id = id;
        return this;
    }

    public String getTalla() {
        return this.talla;
    }

    public VentaDetalle talla(String talla) {
        this.talla = talla;
        return this;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return this.color;
    }

    public VentaDetalle color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrlImagen() {
        return this.urlImagen;
    }

    public VentaDetalle urlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
        return this;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public VentaDetalle precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public VentaDetalle cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return this.subTotal;
    }

    public VentaDetalle subTotal(Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public VentaDetalle venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public VentaDetalle producto(Producto producto) {
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
        if (!(o instanceof VentaDetalle)) {
            return false;
        }
        return id != null && id.equals(((VentaDetalle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDetalle{" +
            "id=" + getId() +
            ", talla='" + getTalla() + "'" +
            ", color='" + getColor() + "'" +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", precio=" + getPrecio() +
            ", cantidad=" + getCantidad() +
            ", subTotal=" + getSubTotal() +
            "}";
    }
}
