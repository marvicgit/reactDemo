package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "tallas")
    private String tallas;

    @Column(name = "colores")
    private String colores;

    @Column(name = "tags")
    private String tags;

    @Column(name = "venta")
    private Boolean venta;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "nuevo")
    private Boolean nuevo;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compra", "producto" }, allowSetters = true)
    private Set<CompraDetalle> compraDetalles = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "producto" }, allowSetters = true)
    private Set<VentaDetalle> ventaDetalles = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "variantes", "producto" }, allowSetters = true)
    private Set<Imagen> imagens = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "imagen" }, allowSetters = true)
    private Set<Variante> variantes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "productos", "categoria" }, allowSetters = true)
    private SubCategoria subCategoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Producto precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Producto stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTallas() {
        return this.tallas;
    }

    public Producto tallas(String tallas) {
        this.tallas = tallas;
        return this;
    }

    public void setTallas(String tallas) {
        this.tallas = tallas;
    }

    public String getColores() {
        return this.colores;
    }

    public Producto colores(String colores) {
        this.colores = colores;
        return this;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public String getTags() {
        return this.tags;
    }

    public Producto tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getVenta() {
        return this.venta;
    }

    public Producto venta(Boolean venta) {
        this.venta = venta;
        return this;
    }

    public void setVenta(Boolean venta) {
        this.venta = venta;
    }

    public Double getDescuento() {
        return this.descuento;
    }

    public Producto descuento(Double descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Boolean getNuevo() {
        return this.nuevo;
    }

    public Producto nuevo(Boolean nuevo) {
        this.nuevo = nuevo;
        return this;
    }

    public void setNuevo(Boolean nuevo) {
        this.nuevo = nuevo;
    }

    public Set<CompraDetalle> getCompraDetalles() {
        return this.compraDetalles;
    }

    public Producto compraDetalles(Set<CompraDetalle> compraDetalles) {
        this.setCompraDetalles(compraDetalles);
        return this;
    }

    public Producto addCompraDetalle(CompraDetalle compraDetalle) {
        this.compraDetalles.add(compraDetalle);
        compraDetalle.setProducto(this);
        return this;
    }

    public Producto removeCompraDetalle(CompraDetalle compraDetalle) {
        this.compraDetalles.remove(compraDetalle);
        compraDetalle.setProducto(null);
        return this;
    }

    public void setCompraDetalles(Set<CompraDetalle> compraDetalles) {
        if (this.compraDetalles != null) {
            this.compraDetalles.forEach(i -> i.setProducto(null));
        }
        if (compraDetalles != null) {
            compraDetalles.forEach(i -> i.setProducto(this));
        }
        this.compraDetalles = compraDetalles;
    }

    public Set<VentaDetalle> getVentaDetalles() {
        return this.ventaDetalles;
    }

    public Producto ventaDetalles(Set<VentaDetalle> ventaDetalles) {
        this.setVentaDetalles(ventaDetalles);
        return this;
    }

    public Producto addVentaDetalle(VentaDetalle ventaDetalle) {
        this.ventaDetalles.add(ventaDetalle);
        ventaDetalle.setProducto(this);
        return this;
    }

    public Producto removeVentaDetalle(VentaDetalle ventaDetalle) {
        this.ventaDetalles.remove(ventaDetalle);
        ventaDetalle.setProducto(null);
        return this;
    }

    public void setVentaDetalles(Set<VentaDetalle> ventaDetalles) {
        if (this.ventaDetalles != null) {
            this.ventaDetalles.forEach(i -> i.setProducto(null));
        }
        if (ventaDetalles != null) {
            ventaDetalles.forEach(i -> i.setProducto(this));
        }
        this.ventaDetalles = ventaDetalles;
    }

    public Set<Imagen> getImagens() {
        return this.imagens;
    }

    public Producto imagens(Set<Imagen> imagens) {
        this.setImagens(imagens);
        return this;
    }

    public Producto addImagen(Imagen imagen) {
        this.imagens.add(imagen);
        imagen.setProducto(this);
        return this;
    }

    public Producto removeImagen(Imagen imagen) {
        this.imagens.remove(imagen);
        imagen.setProducto(null);
        return this;
    }

    public void setImagens(Set<Imagen> imagens) {
        if (this.imagens != null) {
            this.imagens.forEach(i -> i.setProducto(null));
        }
        if (imagens != null) {
            imagens.forEach(i -> i.setProducto(this));
        }
        this.imagens = imagens;
    }

    public Set<Variante> getVariantes() {
        return this.variantes;
    }

    public Producto variantes(Set<Variante> variantes) {
        this.setVariantes(variantes);
        return this;
    }

    public Producto addVariante(Variante variante) {
        this.variantes.add(variante);
        variante.setProducto(this);
        return this;
    }

    public Producto removeVariante(Variante variante) {
        this.variantes.remove(variante);
        variante.setProducto(null);
        return this;
    }

    public void setVariantes(Set<Variante> variantes) {
        if (this.variantes != null) {
            this.variantes.forEach(i -> i.setProducto(null));
        }
        if (variantes != null) {
            variantes.forEach(i -> i.setProducto(this));
        }
        this.variantes = variantes;
    }

    public SubCategoria getSubCategoria() {
        return this.subCategoria;
    }

    public Producto subCategoria(SubCategoria subCategoria) {
        this.setSubCategoria(subCategoria);
        return this;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", stock=" + getStock() +
            ", tallas='" + getTallas() + "'" +
            ", colores='" + getColores() + "'" +
            ", tags='" + getTags() + "'" +
            ", venta='" + getVenta() + "'" +
            ", descuento=" + getDescuento() +
            ", nuevo='" + getNuevo() + "'" +
            "}";
    }
}
