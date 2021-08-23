package mcdcoder.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tarjeta.
 */
@Entity
@Table(name = "tarjeta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "vigencia", nullable = false)
    private String vigencia;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

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

    public Tarjeta id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public Tarjeta numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVigencia() {
        return this.vigencia;
    }

    public Tarjeta vigencia(String vigencia) {
        this.vigencia = vigencia;
        return this;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Tarjeta codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Tarjeta cliente(Cliente cliente) {
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
        if (!(o instanceof Tarjeta)) {
            return false;
        }
        return id != null && id.equals(((Tarjeta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tarjeta{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", vigencia='" + getVigencia() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
