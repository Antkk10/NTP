package listado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by antoniomfc90 on 15/3/17.
 */
public class Listado {

    /**
     * Dato miembro para almacenar a los empleados como diccionario con pares tipo (clave - valor) con el siguiente
     * contenido: < dni - Empleado >
     */
    Map<String, Empleado> lista;

    /**
     * Constructor de la clase, que recibe la localización del archivo de texto con los datos de los empleados
     * y con estos datos inicializa el atributo lista de tipo Map con todos los empleados del sistema.
     * @param archivo de tipo String, contiene la localización del archivo de datos de los empleados.
     */
    public Listado(String archivo){

        // Inicializamos el Map
        lista = new HashMap<String, Empleado>();

        try {
            // Abrimos el archivo
            Stream<String> lineas = Files.lines(Paths.get(archivo));

            // LLama a la función crearEmpleado pasandole todas las lineas, una a una.
            // Después obtienen el dni del empleado junto con el empleado.
            this.lista = lineas.map(linea ->
                crearEmpleado(linea)).collect(Collectors.toMap(empleado -> empleado.obtenerDNI(), Function.identity()));

        } catch (IOException e) {
            System.out.println("Error en la apertura del fichero.");
        }
    }

    /**
     * Método auxiliar para crear un Empleado y insertarlo en la lista de Empleados
     * @param linea contiene todos los datos del empleado a crear.
     */
    private Empleado crearEmpleado(String linea){

        // Se define el patrón para las comas que hacen de separadores
        Pattern pattern = Pattern.compile("(,)");

        // Se procesa la linea
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

        // Añadimos la identificación del empleado (dni) y creamos dicho empleado.
        return new Empleado(infos.get(0), infos.get(1), infos.get(2), infos.get(3));
    }

    /**
     * Método que asigna la división indicada dentro del archivo a todos los dnis que también vienen
     * en el archivo.
     * @param archivo ruta del archivo de División
     */
    public void cargarArchivoAsignacionDivision(String archivo){

        try{
            // Abrimos el archivo y obtenemos todas las lineas
            Stream<String> lineas = Files.lines(Paths.get(archivo));

            // Obtener la division de la primera linea
            Optional<String> lineaDivision = lineas.findFirst();
            String cadenaDivision=lineaDivision.orElse(null);

            // Se pasa de la cadena al enumerado
            Predicate<Division> condicion = division -> division.name().equals(cadenaDivision);
            Division enumeradoDivision=Arrays.stream(Division.values()).filter(condicion).findFirst().get();

            // Se genera de nuevo el flujo, que ya esta consumido. Nos saltamos las dos
            // primeras lineas
            lineas=Files.lines(Paths.get(archivo)).skip(2);

            // Se procesan las lineas con los dnis
            lineas.forEach(linea -> {
                Empleado empleado=lista.get(linea);
                empleado.setDivision(enumeradoDivision);
            });
        }
        catch (IOException e){
            System.out.println("Error en la apertura del archivo. ");
        }
    }

    public void cargarArchivoAsignacionDepartamento(String archivo){

        try{
            // Abrimos el archivo y obtenemos todas las lineas
            Stream<String> lineas = Files.lines(Paths.get(archivo));

            // Obtenemos la primera linea, que indica el departamento
            Optional<String> lineaDepartamento = lineas.findFirst();
            String departamento = lineaDepartamento.orElse(null);

            // Se crea la condición
            Predicate<Departamento> condicion = depart -> depart.name().equals(departamento);
            // Se pasa al enumerado
            Departamento enumeradoDepartamento = Arrays.stream(Departamento.values()).filter(condicion).findFirst().get();

            // Generamos de nuevo el flujo y saltamos las dos primeras lineas
            lineas = Files.lines(Paths.get(archivo)).skip(2);

            // Se procesan las lineas con los dnis
            lineas.forEach(linea -> {
                Empleado empleado = lista.get(linea);
                empleado.setDepartamento(enumeradoDepartamento);
            });


        }
        catch (IOException e){
            System.out.println("Error en la apertura del fichero.");
        }
        
    }

    /**
     * Método que devuelve la información en forma de cadena de todos los empleados
     * @return String con los datos de los empleados.
     */
    public String toString(){
        return lista.toString();
    }

    /**
     * Método que obtiene la cantidad de empleados asociados a un departamento dentro de cada división
     * @return Devuelve un Map<Departamento, Map<Departamento, long>> con la cantidad de empleados por cada departamento
     * de cada división.
     */
    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento(){

        // Obtenemos el flujo de todas las Divisiones
        Stream<Division> divisones = Arrays.stream(Division.values());

        // Declaramos el Map que posteriormente devuelve el método.
        Map<Division, Map<Departamento, Long>> mapa = new HashMap<>();



        // Por cada división almacenamos en el map como clave la division y como contenido otro mapa con el par
        // <Departamento - Long> que representa los Departamentos de la Division y la cantidad de empleados que hay para ese
        // departamento dentro de la propipa Division.
        divisones.forEach(division -> {
            mapa.put(division, obtenerContadoresDepartamento(division));
        });

        return mapa;


    }

    /**
     * Método que crea un map con el par de valores <Departamento, Long>, en el que obtiene todos los empleados que
     * pertenenecen a cada departamento de una división concreta.
     * @param div de tipo Division, contiene la division de la que se quiere obtener cuantos empleados pertenencen a cada uno de
     *            los departamentos de la division
     * @return Devuelve el mapa que se ha creado con clave departamento y valor la cantidad de empleados que pertenece a dicho departamento
     */
    public Map<Departamento, Long> obtenerContadoresDepartamento(Division div){

        // Obtenemos todos los Departamentos
        Stream<Departamento> departamentos = Arrays.stream(Departamento.values());

        // Inicializamos el mapa a devolver
        Map<Departamento, Long> mapa = new HashMap<>();

        // Construimos el mapa. Para cada departamento obtenemos el número de empleados que pertenece a ese departamento
        // con la Division que se recibe como parámetro.
        /*Stream<Departamento> departamentosOrdenados=departamentos.sorted(Comparator.naturalOrder());

        departamentosOrdenados.forEach(departamento -> {
            mapa.put(departamento, this.lista.values().stream().filter(empleado ->
                    empleado.getDepartamento().equals(departamento) && empleado.getDivision().equals(div)).count());

        });
*/
        mapa=lista.values().stream().filter(empleado -> empleado.getDivision() == div).
                map(empleado->empleado.getDepartamento()).sorted(Comparator.naturalOrder()).
                collect(Collectors.groupingBy(Function.identity(),TreeMap::new, Collectors.counting()));

        return mapa;
    }

    /**
     * Método para buscar los empleados sin división asignada: es decir, en el dato miembro división tendrá el valor DIVNA
     * @return Devuelve la Lista<Empleado> creada
     */
    public List<Empleado> buscarEmpleadoSinDivision(){

        return this.lista.entrySet().stream()
                .filter(empleado -> empleado.getValue().getDivision().equals(Division.DIVNA))
                .map(Map.Entry::getValue).collect(Collectors.toList());

    }

    /**
     * Método para buscar empleados con división asignada pero sin departamento: el valor del dato miembro departamento es DEPNA.
     * @return Devuelve la Lista<Empleado> creada
     */
    public List<Empleado> buscarEmpleadoConDivisionSinDepartamento(){


        return this.lista.entrySet().stream().filter(empleado ->
                (!empleado.getValue().getDivision().equals(Division.DIVNA)
                        && empleado.getValue().getDepartamento().equals(Departamento.DEPNA)))
                .map(Map.Entry::getValue).collect(Collectors.toList());

    }

    /**
     * Método para buscar todos los empleados no asignado a departamento que pertenezcan a una determinada división.
     * @param divisionObjetivo división de interes
     * @return lista de empleados sin departamento asignado
     */
    public List<Empleado> buscarEmpleadosSinDepartamento(Division divisionObjetivo){



        return this.lista.entrySet().stream()
                .filter(empleado -> empleado.getValue().getDepartamento().equals(Departamento.DEPNA)
                        && empleado.getValue().equals(divisionObjetivo))
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * Método para determinar si hay dnis repetidos
     * @return true en caso de que haya dnis repetidos, false lo contrario.
     */
    public boolean hayDnisRepetidos(){

        Stream<String> flujo = this.lista.values().stream().map(Empleado::obtenerDNI);

        return flujo.distinct().count() != this.numeroEmpleados();
    }



    /**
     * Método para determinar si hay correos repetidos
     * @return true en caso de que haya correos repetidos, false lo contrario.
     */
    public boolean hayCorreosRepetidos(){


        Stream<String> flujoCorreos = this.lista.values().stream().map(Empleado::obtenerCorreo);

        return flujoCorreos.distinct().count() != numeroEmpleados();


    }

    private long numeroEmpleados(){
        return this.lista.size();
    }

    /**
     * Método para obtener una lista con los correos repetidos, junto con la lista de trabajadores asociados a cada correo
     * repetido (en caso de haberlos).
     * @return Devuelve un map con clave de tipo String que representa el correo y como valor la lista de empleados que tienen
     * ese correo.
     */
    public Map<String, List<Empleado>> obtenerCorreosRepetidos(){

        TreeMap<String, List<Empleado>> mapa=new TreeMap<>();

        // Obtenemos un flujo con todos los correos únicos.
        Stream<String> flujoCorreos = this.lista.values().stream().map(Empleado::obtenerCorreo).distinct();

        flujoCorreos.forEach(correo -> {
                mapa.put(correo, lista.values().stream().filter(empleado ->
                         correo.equals(empleado.obtenerCorreo())).collect(Collectors.toList()));
            });


        return mapa;
    }

    /**
     * Método para obtener una lista de dnis repetidos, junto con la lista de trabajadores asociados a cada dni repetido
     * (en caso de haberlos).
     * @return Devuelve un map con clave de tipo String que representa el dni y como valor la lista de empleados que tienen
     * ese dni.
     */
    public Map<String, List<Empleado> >obtenerDnisRepetidos(){

        TreeMap<String, List<Empleado>> mapa = new TreeMap<>();

        // Obtenemos un flujo con todos los dnis únicos
        Stream<String> flujoDNIS = this.lista.values().stream().map(Empleado::obtenerDNI).distinct();

        flujoDNIS.forEach(dni -> {
            mapa.put(dni, lista.values().stream().filter(empleado ->
                    dni.equals(empleado.obtenerDNI())).collect(Collectors.toList()));
        });

        return mapa;
    }

    /**
     * Método que devuelve el número de empleados de la lista.
     * @return entero con el número de empleados
     */
    public int obtenerNumeroEmpleados(){
        return this.lista.size();
    }

    /**
     * Método que devuelve si un empleado tiene asignado una División. En el caso de que el empleado no tenga asignado
     * el código DIVNA, el empleado tiene división asignada.
     * @param emp de tipo Empleado, es el empleado que se quiere comprobar si tiene divisón asignada.
     * @return de tipo boolean, true cuando el empleado tiene división asignada, false cuando no.
     */
    public boolean empleadoTieneDivision(Empleado emp){
        return !emp.getDivision().equals(Division.DIVNA);
    }
    /**
     * Método que devuelve si un empleado tiene asignado un departamento. En el caso de que el empleado no tenga asignado
     * el código DEPNA, el empleado tiene departamento asignado.
     * @param emp de tipo Empleado, es el empleado que se quiere comprobar si tiene departamento asignado.
     * @return de tipo boolean, true cuando el empleado tiene departamento asignado, false cuando no.
     */
    public boolean empleadoTieneDepartamento(Empleado emp){
        return !emp.getDepartamento().equals(Departamento.DEPNA);

    }

}
