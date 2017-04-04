package listado;

/**
 * Created by antoniomfc90 on 15/3/17.
 */
public class Empleado {

    // Apellidos del empleado
    private String apellidos;

    // Nombre del empleado
    private String nombre;

    // Correo del empleado
    private String correo;

    // Departamento al que pertenece el empleado
    private Departamento departamento;

    // División al que pertenece el empleado
    private Division division;

    // Almacena el dni del empleado
    private String dni;
    /**
     * Constructor de la clase que inicializa los datos del Empleado.
     * @param nombre de tipo String y contiene el nombre del empleado
     * @param apellidos de tipo String y contiene los apellidos del empleado
     * @param correo de tipo String y contiene el correo del empleado.
     */
    public Empleado(String dni, String nombre, String apellidos, String correo){

        this.dni = dni;
        this.apellidos=apellidos;
        this.nombre=nombre;
        this.correo=correo;

        // Indicamos que de momento no tienen ni división ni departamento
        this.departamento = Departamento.DEPNA;
        this.division = Division.DIVNA;
    }

    /**
     * Devuelve un String con el dni del empleado.
     * @return
     */
    public String obtenerDNI(){
        return this.dni;
    }

    /**
     * Actualiza la Division a la que pertenece el empleado
     * @param d de tipo Division, contiene la nueva Division
     */
    public void setDivision(Division d){
        this.division = d;
    }

    /**
     * Actualiza el Departamento al que pertenece el empleado
     * @param d de tipo Departamento, contiene el nuevo Departamento.
     */
    public void setDepartamento(Departamento d){
        this.departamento = d;
    }

    /**
     * Devuelve la division actual del empleado.
     * @return
     */
    public Division getDivision(){
        return this.division;
    }

    /**
     * Devuelve el departamento al que pertenece el empleado
     * @return
     */
    public Departamento getDepartamento(){
        return this.departamento;
    }

    /**
     * Devuelve un String con el correo del empleado
     * @return
     */
    public String obtenerCorreo(){
        return this.correo;
    }

    /**
     * Devuelve un String con todos los datos del empleado (nombre, apellido, correo, division, departamento)
     * @return
     */
    public String toString(){
        return this.nombre + " " + this.apellidos + " " + this.correo + " División: " + this.division
                + " Departamento: " + this.departamento;
    }
}
