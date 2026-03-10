package isp.lab8.airways;

import java.util.List;

public interface IWaypointRepository {

    void save(Waypoint waypoint);
    List<Waypoint> readAll();
}
