package Interfaces;

import Enumeradores.TipoDeVisualizacion;

public interface I_GestionTurnos {

    public boolean agendaTurnos();

    public boolean confirmaTurnos();

    public boolean cancelaTurnos();
    public boolean reprogramaTurnos();

    public boolean soloAutoGestion();

    public TipoDeVisualizacion modalidadVisualizacionDeTurnos();

}
