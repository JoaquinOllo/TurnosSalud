package Citas;

public class EstadoCita {

    private boolean confirmado = false;
    private boolean cancelado = false;
    private boolean pospuesto = false;
    private String razonCancelado;
    private String razonPospuesto;

    public boolean isConfirmado() {
        return confirmado;
    }

    public void confirmar() {
        this.confirmado = true;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void cancelar(String motivo) {
        this.cancelado = true;
        this.razonCancelado = motivo;
    }

    public boolean isPospuesto() {
        return pospuesto;
    }

    public void posponer(String motivo) {
        this.pospuesto = true;
        this.razonPospuesto = motivo;
    }

    public String getRazonCancelado() {
        return razonCancelado;
    }

    public String getRazonPospuesto() {
        return razonPospuesto;
    }

    public EstadoCita() {
    }

}
