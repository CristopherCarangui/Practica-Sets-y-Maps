package eva3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class LibroController {
    public Set<Libro> filtrarPorAnio(List<Libro> libros, int anioMinimo){
        Set<Libro> rSet = new TreeSet<>(
            (c1,c2)->{
                int compA = Integer.compare(c2.getAnioPublicacion(), c1.getAnioPublicacion());
                if(compA !=0){
                    return compA;
                }
                return c1.getTitulo().compareToIgnoreCase(c2.getTitulo());
            }
        );
        Set<String> vinculo = new HashSet<>();
        List<Libro> sinDuplicar = new ArrayList<>();
        for(Libro el: libros){
            String clave = el.getTitulo().toLowerCase() + "|" + el.getAutor().toLowerCase();
            if(!vinculo.contains(clave)){
                vinculo.add(clave);
                sinDuplicar.add(el);
            }
        }

        for(Libro eLibro: sinDuplicar){
            if(eLibro.getAnioPublicacion()>= anioMinimo ){
                rSet.add(eLibro);
            }
        }
        return rSet;
        
    }

    public Map<String, List<String>> agruparPorDecada(List<Libro> libros){
        Map<String,List<String>> resultado = new TreeMap<>();
        for(Libro el: libros){
            int anio = el.getAnioPublicacion();
            int decada =( anio/10) *10;
            String claveDecada = decada + "s";
            if(!resultado.containsKey(claveDecada)){
                resultado.put(claveDecada, new ArrayList<>());
            }
            List<String> nGrupo= resultado.get(claveDecada);
            String autor = el.getAutor();
            boolean yaExiste = false;
            for(String n : nGrupo){
                if(n.equalsIgnoreCase(autor)){
                    yaExiste = true;
                    break;
                }
            }
            if(!yaExiste){
                nGrupo.add(autor);
            }
            

        }
        return resultado;
    }
}
