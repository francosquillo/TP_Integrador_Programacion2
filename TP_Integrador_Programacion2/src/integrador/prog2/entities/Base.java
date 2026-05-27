package entities;

import java.time.LocalDateTime;

public abstract class Base {

    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    public Base() {
    }

    public Base(Long id, boolean eliminado, LocalDateTime createdAt) {
        this.id = id;
        this.eliminado = eliminado;
        this.createdAt = createdAt;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public boolean isEliminado() {return eliminado;}

    public void setEliminado(boolean eliminado) {this.eliminado = eliminado;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

}
