package isp.lab8.airways;

import com.fasterxml.jackson.databind.ObjectMapper;
import isp.lab8.airways.Waypoint;
import examples.files.FileReadUtil;
import examples.files.FilesAndFoldersUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WaypointFileJsonRepository implements IWaypointRepository {

    private String workingFolder;
    private ObjectMapper objectMapper = new ObjectMapper();

    public WaypointFileJsonRepository(String workingFolder) {
        FilesAndFoldersUtil.createFolder(workingFolder);
        this.workingFolder = workingFolder;
    }

    @Override
    public void save(Waypoint waypoint) {
        try {
            objectMapper.writeValue(new FileWriter(workingFolder + "\\" + "waypoint_" + waypoint.getName() + "_" + System.currentTimeMillis() + ".json"), waypoint);
        } catch (IOException ex) {
            Logger.getLogger(WaypointFileJsonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveWaypoint(Waypoint waypoint, String routeName) {
        try {
            String routeFolderPath = workingFolder + "\\" + routeName;
            FilesAndFoldersUtil.createFolder(routeFolderPath);

            String fileName = "waypoint_" + waypoint.getName() + ".json";
            FileWriter writer = new FileWriter(routeFolderPath + "\\" + fileName);

            objectMapper.writeValue(writer, waypoint);
        } catch (IOException ex) {
            Logger.getLogger(WaypointFileJsonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Waypoint> readAll() {
        List<Waypoint> allWaypoints = new ArrayList<>();

        try {
            // Găsește toate subfolderele (rutele)
            List<String> routeFolders = FilesAndFoldersUtil.getFolders(workingFolder);

            for (String routeFolderName : routeFolders) {
                String routeFolderPath = workingFolder + "\\" + routeFolderName;
                List<String> waypointFiles = FilesAndFoldersUtil.getFilesInFolder(routeFolderPath);

                for (String fileName : waypointFiles) {
                    String filePath = routeFolderPath + "\\" + fileName;
                    String jsonContent = FileReadUtil.readAllFileLines(filePath).stream().collect(Collectors.joining("\n"));
                    Waypoint waypoint = objectMapper.readValue(jsonContent, Waypoint.class);
                    allWaypoints.add(waypoint);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(WaypointFileJsonRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allWaypoints;
    }

}