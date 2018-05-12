package bw.effective.java.rule18.nonhierarchical;

import java.util.List;

/**
 * @author Byungwook Lee on 2018-04-21
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public class AircraftCarrier implements Ship, Airport {
    private List<Aircraft> aircrafts;
    private List<Cargo> cargos;

    @Override
    public void land(Aircraft aircraft) {
        aircrafts.add(aircraft);
    }

    @Override
    public void depart(Aircraft aircraft) {
        aircrafts.remove(aircraft);
    }

    @Override
    public void addCargo(Cargo cargo) {
        cargos.add(cargo);
    }

    @Override
    public void removeCargo(Cargo cargo) {
        cargos.remove(cargo);
    }
}
