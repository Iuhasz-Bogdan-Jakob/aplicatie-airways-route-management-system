package isp.lab8.airways;


import isp.lab8.airways.Waypoint;
import java.util.List;

public interface IWaypointService {
    void addWaypoint(int index, String name, double latitude, double longitude, int altitude);
    List<Waypoint> getAllWaypoints();
    Waypoint getWaypointByName(String name) throws WaypointNotFoundException;
}