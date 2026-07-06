package eva1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ProductoController {
    public Set<Producto> filtrarporPrecio(List<Producto> productos, double precioMaximo){
        Set<String> vistos = new HashSet<>();
        List<Producto> sinMuplicar = new ArrayList<>();
        for(Producto pro : productos ){
            String clave = pro.getNombre().toLowerCase() + "|" + pro.getCategoria().toLowerCase();
            if(!vistos.contains(clave)){
                vistos.add(clave);
                sinMuplicar.add(pro);
            }
        }
        Set<Producto> proSet = new TreeSet<>(
            (c1,c2)->{
                int compPre = Double.compare(c1.getPrecio(), c2.getPrecio());
                if(compPre !=0){
                    return  compPre;
                }
                int compNom = c2.getNombre().compareToIgnoreCase(c1.getNombre());
                if(compNom !=0){
                    return compNom;
                }
                return c1.getCategoria().compareToIgnoreCase(c2.getCategoria());
            }
        );
        for(Producto p : sinMuplicar){
            if(p.getPrecio()<= precioMaximo){
                proSet.add(p);
            }
        }
        return  proSet;
        
    }

    public Map<String,List<String>> agruparPorCategoria(List<Producto> productos){
        Map<String,List<String>> resultado = new TreeMap<>();
        for(Producto p : productos){
            String categoria = p.getCategoria();
            if(!resultado.containsKey(categoria)){
                resultado.put(categoria, new ArrayList<>());
            }
            List<String> nGrupo = resultado.computeIfAbsent(categoria, k -> new ArrayList<>());
            boolean yaExiste = false;
            for(String b : nGrupo){
                if(b.equalsIgnoreCase(p.getNombre())){
                    yaExiste = true;
                    break;
                }
            }
            if(!yaExiste){
                nGrupo.add(p.getNombre());
            }
        }

        return resultado;

    }
    
}
