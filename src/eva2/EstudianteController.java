package eva2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EstudianteController {
   public Set<Estudiante> filtrarAprobados(List<Estudiante> estudiantes, double notaAprobacion){
        Set<Estudiante> eAprobados = new TreeSet<>(
            (c1,c2)->{
                int compP = Double.compare(c2.getPromedio(), c1.getPromedio());
                if(compP!=0){
                    return compP;
                }
                int compE = Double.compare(c1.getEdad(), c2.getEdad());
                if(compE !=0){
                    return compE;
                }
                return c1.getNombre().compareToIgnoreCase(c2.getNombre());
            }
        );
        Set<String> vistos= new HashSet<>();
        List<Estudiante> sinDuplicar = new ArrayList<>();
        for(Estudiante e : estudiantes){
            String clave = e.getNombre().toLowerCase() + "|" + e.getPromedio();
            if(!vistos.contains(clave)){
                vistos.add(clave);
                sinDuplicar.add(e);
            }            
        }
        for(Estudiante aprobado: sinDuplicar){
            if(aprobado.getPromedio()>= notaAprobacion){
                eAprobados.add(aprobado);
            }
        }
        
        return eAprobados;
    }
    
    public Map<Character, List<String>> agruparPorLetraInicial(List<Estudiante> estudiantes){
        Map<Character, List<String>> resultado = new TreeMap<>();
        for(Estudiante e : estudiantes){
            String nombre = e.getNombre();
            char primerLetra = Character.toUpperCase(nombre.charAt(0));
            if(!resultado.containsKey(primerLetra)){
                resultado.put(primerLetra, new ArrayList<>());
            }
            List<String> nGrupo = resultado.get(primerLetra);
            boolean yaExiste = false;
            for(String n : nGrupo){
                if(n.equalsIgnoreCase(nombre)){
                    yaExiste = true;
                    break;
                }
            }
            if(!yaExiste){
                nGrupo.add(nombre);
            }
        }
        return resultado;
    }
}
