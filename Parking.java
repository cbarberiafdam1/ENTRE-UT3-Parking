/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un n� elevado de horas y se benefician de esta 
 * tarifa m�s econ�mica
 * (leer enunciado)
 * 
 */
public class Parking
{
    //Constantes
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 = 5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA  = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.00;
    private final double PRECIO_MEDIA_COMERCIAL = 3.00;

    //Atributos
    private String nombre;
    private int cliente;
    private double importeTotal;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clienteMaximoComercial;
    private double importeMaximoComercial;

    /**
     * Inicializa el parking con el nombre indicada por el par�metro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
        nombre = queNombre;
        cliente = 0;
        importeTotal = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clienteMaximoComercial = 0;
        importeMaximoComercial = 0;
    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
        return nombre;

    }

    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre(String queNombre) {
        nombre = queNombre;

    }

    /**
     *  Recibe cuatro par�metros que supondremos correctos:
     *    tipoTarifa - un car�cter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida � hora de salida del parking
     *    dia � n� de d�a de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos par�metros el m�todo debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizar� adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro m�todo) las estad�sticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo d�a
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        cliente ++;
        String tipoDeTarifa = "";
        double precioTarifa = 0;
        //Conversi�n de las horas
        int horaEntra = entrada / 100;
        int minutoEntra = entrada % 100;
        int horaSal = salida / 100; 
        int minutoSal = salida % 100;

        int minutosEntrada = horaEntra * 60 + minutoEntra;
        int minutosSalida = horaSal * 60 + minutoSal;
        String minutosEntradas;
        String minutosSalidas;

        if(minutoEntra < 10){
            minutosEntradas = "0" + minutoEntra;
        }
        else{
            minutosEntradas = "" + minutoEntra;
        }
        if(minutoSal < 10){
            minutosSalidas = "0" + minutoSal;
        }
        else{
            minutosSalidas = "" + minutoSal;
        }

        String horaEntrada = horaEntra + ":" + minutosEntradas;
        String horaSalida = horaSal + ":" + minutosSalidas;
        
        switch (tipoTarifa){
            case 'R': if(minutosEntrada >= HORA_INICIO_ENTRADA_TEMPRANA && minutosEntrada <= HORA_FIN_ENTRADA_TEMPRANA && minutosSalida >= HORA_INICIO_SALIDA_TEMPRANA && minutosSalida <= HORA_FIN_SALIDA_TEMPRANA){
                precioTarifa = PRECIO_TARIFA_PLANA_REGULAR;
                tipoDeTarifa = "REGULAR Y TEMPRANA";
            }
            else{
                precioTarifa = PRECIO_BASE_REGULAR;
                if (entrada < 1100) {
                    if (salida > 1100) {
                        precioTarifa += (11 * 60 - minutosEntrada) / 30 * PRECIO_MEDIA_REGULAR_HASTA11 + (minutosSalida - 11 * 60) / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                    }
                    else {
                        precioTarifa += (minutosSalida - minutosEntrada) /30  * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else {
                    precioTarifa += (minutosSalida - minutosEntrada) /30  * PRECIO_MEDIA_REGULAR_DESPUES11;
                }
                tipoDeTarifa = "REGULAR";
            }
            regular++;
            break;
            default:
            precioTarifa = PRECIO_PRIMERAS3_COMERCIAL;
            if(minutosSalida - minutosEntrada > 180){
                precioTarifa += (minutosSalida - minutosEntrada - 180) / 30 * PRECIO_MEDIA_COMERCIAL;
            }
            tipoDeTarifa = "COMERCIAL";
            comercial++;
            break;
        }
        System.out.println ("******************************************");
        System.out.println ("Cliente N�: " + cliente);
        System.out.println ("Hora entrada: " + horaEntrada);
        System.out.println ("Hora salida: " + horaSalida);
        System.out.println ("Tarifa a aplicar: " + tipoDeTarifa);
        System.out.println ("Importe a pagar: " + precioTarifa + "�");
        System.out.println ("******************************************");
        importeTotal = importeTotal + precioTarifa;
        if (importeMaximoComercial < precioTarifa) {
            importeMaximoComercial = precioTarifa;
            clienteMaximoComercial = cliente;
        }
    }

    /**
     * Muestra en pantalla las estad�sticas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstad�sticas() {
        System.out.println ("******************************************");
        System.out.println ("Importe total entre los clientes: " + importeTotal );
        System.out.println ("N� clientes tarifa regular: " + regular);
        System.out.println ("N� clientes tarifa comercial: " + comercial);
        System.out.println ("Cliente tarifa COMERCIAL con factura m�xima fue el n� "+ clienteMaximoComercial + " y pag� " + importeMaximoComercial + "�");
        System.out.println ("******************************************");
    }

    /**      
     *  Calcula y devuelve un String que representa el nombre del d�a      
     *  en el que m�s clientes han utilizado el parking - "S�BADO"   "DOMINGO" o  "LUNES"      
     */ 
    public String diaMayorNumeroClientes() { 
        if(clientesDomingo > clientesLunes && clientesDomingo > clientesSabado){
            return "DOMINGO";
        }
        else if(clientesSabado > clientesLunes){
            return "S�BADO";
        }else{
            return "LUNES";
        }
    } 
}