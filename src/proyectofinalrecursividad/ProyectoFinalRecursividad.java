package proyectofinalrecursividad;

public class ProyectoFinalRecursividad {

    public static void main(String[] args) {
        System.out.println("----------PROYECTO FINAL RECURSIVIDAD----------");
        System.out.println("");

        String[] pruebas = {
            "7+10+2",
            "1+5*(7-1)+10",
            "5+7+(1+2*(8-9)+2)-10",
            "9-2*(75-9+(6+3+(1+2)*1)+7)+9",
            "5++1+10",
            "-5+10",
            "74-7/7",
            "10+9-A"
        };

        for (String texto : pruebas) {
            System.out.print("Evaluando: " + texto + " = ");
            try {
                int res = resolver(texto);
                System.out.println(res);
            } catch (Exception e) {
                System.out.println("Fallo: " + e.getMessage());
            }
        }
        
        System.out.println("");
        System.out.println("----------FIN DE LA EJECUCION----------");
    }

    public static int resolver(String texto) throws Exception {
        texto = texto.replace(" ", "");

        if (texto.isEmpty()) {
            throw new Exception("La expresion esta vacia");
        }

        int parentesis = 0;
        int pos = -1;
        int prioridad = 100; 

        for (int i = texto.length() - 1; i >= 0; i--) {
            char c = texto.charAt(i);

            if (c == ')') {
                parentesis++;
            } else if (c == '(') {
                parentesis--;
                if (parentesis < 0) {
                    throw new Exception("Error en los parentesis");
                }
            } else if (parentesis == 0) {
                if (c == '+' || c == '-') {
                    if (prioridad > 1) {
                        prioridad = 1;
                        pos = i;
                    }
                } else if (c == '*') {
                    if (prioridad > 2) {
                        prioridad = 2;
                        pos = i;
                    }
                } else if (c == '/') {
                    throw new Exception("No se permite dividir");
                } else if (!Character.isDigit(c)) {
                    throw new Exception("Caracter no valido: " + c);
                }
            }
        }

        if (parentesis != 0) {
            throw new Exception("Faltan o sobran parentesis");
        }

        if (pos != -1) {
            if (pos == 0) {
                throw new Exception("Hay un operador al principio");
            }

            char op = texto.charAt(pos);
            String izq = texto.substring(0, pos);
            String der = texto.substring(pos + 1);

            int valIzq = resolver(izq);
            int valDer = resolver(der);

            if (op == '+') return valIzq + valDer;
            if (op == '-') return valIzq - valDer;
            if (op == '*') return valIzq * valDer;
        }

        if (texto.startsWith("(") && texto.endsWith(")")) {
            return resolver(texto.substring(1, texto.length() - 1));
        }

        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                throw new Exception("Formato raro cerca de: " + texto);
            }
        }

        return Integer.parseInt(texto);
    }
    
}
