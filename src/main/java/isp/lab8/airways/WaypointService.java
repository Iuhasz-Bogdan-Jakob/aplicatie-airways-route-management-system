package isp.lab8.airways;

import isp.lab8.airways.Waypoint;


import java.util.List;

public class WaypointService implements IWaypointService {

    private IWaypointRepository waypointRepository;

    public WaypointService(IWaypointRepository waypointRepository) {
        this.waypointRepository = waypointRepository;
    }

    @Override
    public void addWaypoint(int index, String name, double latitude, double longitude, int altitude) {
        Waypoint waypoint = new Waypoint(index, name, latitude, longitude, altitude);
        waypointRepository.save(waypoint);
    }

    public void addWaypoint(int index, String name, double latitude, double longitude, int altitude, String routeName) {
        Waypoint waypoint = new Waypoint(index, name, latitude, longitude, altitude);
        if (waypointRepository instanceof WaypointFileJsonRepository) {
            ((WaypointFileJsonRepository) waypointRepository).saveWaypoint(waypoint, routeName);
        } else {
            waypointRepository.save(waypoint);
        }
    }


    @Override
    public List<Waypoint> getAllWaypoints() {
        return waypointRepository.readAll();
    }

    @Override
    public Waypoint getWaypointByName(String name) throws WaypointNotFoundException {
        return waypointRepository.readAll().stream()
                .filter(waypoint -> waypoint.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new WaypointNotFoundException("Waypoint with name '" + name + "' not found."));
    }
}