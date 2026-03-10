package isp.lab8.airways;

import com.fasterxml.jackson.databind.ObjectMapper;
import examples.files.FilesAndFoldersUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteManager {
    private final String routesFolder;

    public RouteManager(String routesFolder) {
        this.routesFolder = routesFolder;
    }

    // Listează toate rutele (subfolderele)
    public List<String> listRoutes() {
        List<String> routes = new ArrayList<>();
        File folder = new File(routesFolder);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    routes.add(file.getName());
                }
            }
        }
        return routes;
    }

    // Încarcă waypoint-urile dintr-o rută (subfolder)
    public List<Waypoint> loadRoute(String routeName) {
        List<Waypoint> waypoints = new ArrayList<>();
        String routePath = routesFolder + File.separator + routeName;
        List<String> files = FilesAndFoldersUtil.getFilesInFolder(routePath);
        for (String file : files) {
            if (file.endsWith(".json")) {
                Waypoint wp = readWaypointFromFile(routePath + File.separator + file);
                if (wp != null) {
                    waypoints.add(wp);
                }
            }
        }
        return waypoints;
    }

    // Citește un fișier JSON și întoarce un Waypoint
    private Waypoint readWaypointFromFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(filePath), Waypoint.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Calculează distanța totală a unei rute
    public double calculateRouteDistance(List<Waypoint> waypoints) {
        double totalDistance = 0.0;
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint wp1 = waypoints.get(i);
            Waypoint wp2 = waypoints.get(i + 1);
            totalDistance += calculateDistance(wp1.getLatitude(), wp1.getLongitude(), wp2.getLatitude(), wp2.getLongitude());
        }
        return totalDistance;
    }

    // Formula Haversine
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
