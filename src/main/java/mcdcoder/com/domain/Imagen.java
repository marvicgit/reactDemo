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
 * A Imagen.
 */
@Entity
@Table(name = "imagen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Imagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "indice", nullable = false)
    private Integer indice;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "extension", nullable = false)
    private String extension;

    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @Column(name = "imagen_content_type", nullable = false)
    private String imagenContentType;

    @OneToMany(mappedBy = "imagen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "imagen" }, allowSetters = true)
    private Set<Variante> variantes = new HashSet<>();

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

    public Imagen id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getIndice() {
        return this.indice;
    }

    public Imagen indice(Integer indice) {
        this.indice = indice;
        return this;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Imagen nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Imagen peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getExtension() {
        return this.extension;
    }

    public Imagen extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getImagen() {
        return this.imagen;
    }

    public Imagen imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return this.imagenContentType;
    }

    public Imagen imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<Variante> getVariantes() {
        return this.variantes;
    }

    public Imagen variantes(Set<Variante> variantes) {
        this.setVariantes(variantes);
        return this;
    }

    public Imagen addVariante(Variante variante) {
        this.variantes.add(variante);
        variante.setImagen(this);
        return this;
    }

    public Imagen removeVariante(Variante variante) {
        this.variantes.remove(variante);
        variante.setImagen(null);
        return this;
    }

    public void setVariantes(Set<Variante> variantes) {
        if (this.variantes != null) {
            this.variantes.forEach(i -> i.setImagen(null));
        }
        if (variantes != null) {
            variantes.forEach(i -> i.setImagen(this));
        }
        this.variantes = variantes;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public Imagen producto(Producto producto) {
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
        if (!(o instanceof Imagen)) {
            return false;
        }
        return id != null && id.equals(((Imagen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Imagen{" +
            "id=" + getId() +
            ", indice=" + getIndice() +
            ", nombre='" + getNombre() + "'" +
            ", peso=" + getPeso() +
            ", extension='" + getExtension() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
