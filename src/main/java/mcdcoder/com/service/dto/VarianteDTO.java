package mcdcoder.com.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mcdcoder.com.domain.Variante} entity.
 */
public class VarianteDTO implements Serializable {

    private Long id;

    private String sku;

    @NotNull
    private String talla;

    @NotNull
    private String color;

    @NotNull
    private Integer stock;

    private Double precio;

    private ProductoDTO producto;

    private ImagenDTO imagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public ImagenDTO getImagen() {
        return imagen;
    }

    public void setImagen(ImagenDTO imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VarianteDTO)) {
            return false;
        }

        VarianteDTO varianteDTO = (VarianteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, varianteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VarianteDTO{" +
            "id=" + getId() +
            ", sku='" + getSku() + "'" +
            ", talla='" + getTalla() + "'" +
            ", color='" + getColor() + "'" +
            ", stock=" + getStock() +
            ", precio=" + getPrecio() +
            ", producto=" + getProducto() +
            ", imagen=" + getImagen() +
            "}";
    }
}
