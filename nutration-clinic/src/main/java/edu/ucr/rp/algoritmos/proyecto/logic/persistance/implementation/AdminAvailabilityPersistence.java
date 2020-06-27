package edu.ucr.rp.algoritmos.proyecto.logic.persistance.implementation;

import edu.ucr.rp.algoritmos.proyecto.logic.domain.AdminAvailability;
import edu.ucr.rp.algoritmos.proyecto.logic.persistance.interfaces.Persistence;
import edu.ucr.rp.algoritmos.proyecto.util.files.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class AdminAvailabilityPersistence implements Persistence<AdminAvailability, List> {
    private final String path = "files/adminAvailability.json";
    private final JsonUtil jsonUtil = new JsonUtil();

    /**
     * Para guardar la disponibilidad de un doctor.
     * @param list que se quiere guardar
     * @return true si se guardó, si no, false
     */
    @Override
    public boolean write(List list) {
        if (list == null) return false;
        return saveDoctorAvailability(list);
    }

    private boolean saveDoctorAvailability(List<AdminAvailability> list) {
        jsonUtil.toFile(new File(path), list);
        return true;
    }

    /**
     * Para leer una lista de disponibilidad de doctores.
     * @return lista de disponibilidad de doctores
     */
    @Override
    public List read() {
        return readDoctorAvailability();
    }

    private List readDoctorAvailability(){
        File file = new File(path);
        if(file.exists()){
            try {
                return jsonUtil.asObject(file.toURI().toURL(), List.class);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public List<AdminAvailability> convert(List<AdminAvailability> list){
        try {
            return jsonUtil.jsonArrayToObjectList(jsonUtil.asJson(list), AdminAvailability.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Elimina toda la lista de disponibilidad de doctores.
     * @return true si se eliminaron todos los usuarios, si no, false
     */
    @Override
    public boolean deleteAll() {
        try {
            FileUtils.forceDelete(new File(path));
            return true;
        }catch (IOException e){
            return false;
        }
    }

}
