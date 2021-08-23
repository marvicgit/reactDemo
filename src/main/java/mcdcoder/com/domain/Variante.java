package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Variante.
 */
@Entity
@Table(name = "variante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Variante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sku")
    private String sku;

    @NotNull
    @Column(name = "talla", nullable = false)
    private String talla;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compraDetalles", "ventaDetalles", "imagens", "variantes", "subCategoria" }, allowSetters = true)
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "variantes", "producto" }, allowSetters = true)
    private Imagen imagen;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Variante id(Long id) {
        this.id = id;
        return this;
    }

    public String getSku() {
        return this.sku;
    }

    public Variante sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTalla() {
        return this.talla;
    }

    public Variante talla(String talla) {
        this.talla = talla;
        return this;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return this.color;
    }

    public Variante color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getStock() {
        return this.stock;
    }

    public Variante stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Variante precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public Variante producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Imagen getImagen() {
        return this.imagen;
    }

    public Variante imagen(Imagen imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variante)) {
            return false;
        }
        return id != null && id.equals(((Variante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Variante{" +
            "id=" + getId() +
            ", sku='" + getSku() + "'" +
            ", talla='" + getTalla() + "'" +
            ", color='" + getColor() + "'" +
            ", stock=" + getStock() +
            ", precio=" + getPrecio() +
            "}";
    }
}
