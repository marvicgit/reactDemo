package mcdcoder.com.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mcdcoder.com.domain.Imagen} entity.
 */
public class ImagenDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer indice;

    @NotNull
    private String nombre;

    @NotNull
    private Double peso;

    @NotNull
    private String extension;

    @Lob
    private byte[] imagen;

    private String imagenContentType;
    private ProductoDTO producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagenDTO)) {
            return false;
        }

        ImagenDTO imagenDTO = (ImagenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imagenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagenDTO{" +
            "id=" + getId() +
            ", indice=" + getIndice() +
            ", nombre='" + getNombre() + "'" +
            ", peso=" + getPeso() +
            ", extension='" + getExtension() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", producto=" + getProducto() +
            "}";
    }
}
