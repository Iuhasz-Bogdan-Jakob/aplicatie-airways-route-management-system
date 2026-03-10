package isp.lab8.airways;

import isp.lab8.airways.WaypointFileJsonRepository;
import isp.lab8.airways.WaypointService;
import isp.lab8.airways.WaypointNotFoundException;
import examples.workTrucksMonitoring.view.MapViewerJFrame; // Reusing the map viewer
import org.jxmapviewer.viewer.GeoPosition;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WaypointApp {

    public static void main(String[] args) {
        WaypointFileJsonRepository repository = new WaypointFileJsonRepository("E:\\ISP\\isp-2025-hm-Iuhasz-Bogdan-30125\\isp-lab-8\\Routes");
        WaypointService service = new WaypointService(repository);

        // Add some waypoints
        String routeName = "LRCL-TASOD";  // numele rutei actuale

        // Add waypoints to this route
        service.addWaypoint(1, "Cluj", 46.7712, 23.6236, 300, routeName);
        service.addWaypoint(2, "Tasnad", 47.4667, 22.5833, 200, routeName);

        // Afișare, etc.
        System.out.println("Waypoints saved for route: " + routeName);

        // Add some waypoints
        String routeName1 = "BIRGU-LROP";
        String routename2 = "LRCL-TASOD";// numele rutei actuale

        // Add waypoints to this route
        service.addWaypoint(1, "Birligi", 45.9467, 26.0217, 10200, routeName1);
        service.addWaypoint(2, "Henri Coandă International Airport", 44.5711, 26.0858, 106, routeName1);

        // Afișare, etc.
        System.out.println("Waypoints saved for route: " + routeName1);

        String basePath = "E:\\ISP\\isp-2025-hm-Iuhasz-Bogdan-30125\\isp-lab-8\\Routes";
        RouteManager routeManager = new RouteManager(basePath);

        // 1. Listare rute
        System.out.println("Rute disponibile:");
        List<String> routes = routeManager.listRoutes();
        routes.forEach(System.out::println);

        if (!routes.isEmpty()) {
            // 2. Alegem prima rută pentru test
            String routeName2 = routes.get(0);
            System.out.println("\nÎncărcare rută: " + routeName2);

            // 3. Încarcă waypointurile
            List<Waypoint> waypoints = routeManager.loadRoute(routeName2);

            // 4. Afișează waypoints
            System.out.println("Waypoints:");
            for (Waypoint wp : waypoints) {
                System.out.println(wp);
            }

            // 5. Calculează distanța
            double totalDistance = routeManager.calculateRouteDistance(waypoints);
            System.out.println("Distanța totală: " + totalDistance + " km");

        } else {
            System.out.println("Nu există rute în folderul specificat.");
        }


        // Get and print all waypoints
        System.out.println("All Waypoints:");
        List<Waypoint> allWaypoints = service.getAllWaypoints();
        allWaypoints.forEach(System.out::println);
        System.out.println("----");

        // Get a waypoint by name
        try {
            Waypoint clujWaypoint = service.getWaypointByName("Cluj");
            System.out.println("Waypoint Cluj: " + clujWaypoint);
        } catch (WaypointNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("----");

        // Display waypoints on the map
        MapViewerJFrame viewer = new MapViewerJFrame();
        List<GeoPosition> waypointPositions = allWaypoints.stream()
                .map(waypoint -> new GeoPosition(waypoint.getLatitude(), waypoint.getLongitude()))
               .collect(Collectors.toList());
        viewer.updateRoute(waypointPositions); // Reusing the route drawing for points
        viewer.setVisible(true);

    }
}