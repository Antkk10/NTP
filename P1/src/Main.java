import com.sun.javafx.geom.transform.Identity;
import listado.Departamento;
import listado.Division;
import listado.Empleado;
import listado.Listado;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Listado listado = new Listado("./data/datos.txt");
        listado.cargarArchivoAsignacionDivision("./data/asignacionDIVNA.txt");
        listado.cargarArchivoAsignacionDivision("./data/asignacionDIVID.txt");
        listado.cargarArchivoAsignacionDivision("./data/asignacionDIVSW.txt");
        listado.cargarArchivoAsignacionDivision("./data/asignacionDIVHW.txt");
        listado.cargarArchivoAsignacionDivision("./data/asignacionDIVSER.txt");
        listado.cargarArchivoAsignacionDepartamento("./data/asignacionDEPNA.txt");
        listado.cargarArchivoAsignacionDepartamento("./data/asignacionDEPSB.txt");
        listado.cargarArchivoAsignacionDepartamento("./data/asignacionDEPSM.txt");
        listado.cargarArchivoAsignacionDepartamento("./data/asignacionDEPSA.txt");


        


    }
}
