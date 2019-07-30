/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MaquinaDeEstado;

import java.util.ArrayList;

/**
 *
 * @author S
 */
    
public enum MaqEstado {

    E0{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            switch(variable){
                case 0:
                    registro.add(variable);
                    return E11;
                case 1:
                    registro.add(variable);
                    return E12;
                case 2:
                    registro.add(variable);
                    return E13;
                case 22:
                    registro.add(variable);
                    return E4;
                default:
                    return E0;
            }
        }
    },
    E11{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if (variable == 3){registro.add(variable); return E21;}
            else return E11;
        }
    },
    E21{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 6){registro.add(variable); return E31;}
            else return E21;
        }
    },
    E31{   
        @Override
        public MaqEstado siguienteEstado(int variable){
            switch(variable){
                case 9:
                    registro.add(variable);
                    return E41;
                case 10:
                    registro.add(variable);
                    return E42;
                default:
                    return E31;
            }
        }
    },
    E12{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 4){registro.add(variable); return E22;}
            else return E12;
        }
    },
    E22{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 7){registro.add(variable); return E32;}
            else return E22;
        }
    },
    E32{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            switch(variable){
                case 9:
                    registro.add(variable);
                    return E41;
                case 10:
                    registro.add(variable);
                    return E42;
                default:
                    return E32;
            }
        }
    },
    E13{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 5){registro.add(variable); return E23;}
            else return E13;
        }
    },
    E23{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 8){registro.add(variable); return E33;}
            else return E23;
        }
    },
    E33{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            switch(variable){
                case 9:
                    registro.add(variable);
                    return E41;
                case 10:
                    registro.add(variable);
                    return E42;
                default:
                    return E33;
            }
        }
    },
    E4{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 23){registro.add(variable); return EFinal;}
            else return E4;
        }
    },
    E41{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 11){registro.add(variable); return E51;}
            else return E41;
        }
    },
    E51{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if (variable == 13){registro.add(variable); return E61;}
            else return E51;
        }
    },
    E61{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 15){registro.add(variable); return E71;}
            else return E61;
        }
    },
    E71{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 17){registro.add(variable); return E1;}
            else return E71;
        }
    },
    E42{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 12){registro.add(variable); return E52;}
            else return E42;
        }
    },
    E52{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if (variable == 14){registro.add(variable); return E62;}
            else return E52;
        }
    },
    E62{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 16){registro.add(variable); return E72;}
            else return E62;
        }
    },
    E72{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 17){registro.add(variable); return E1;}
            else return E72;
        }
    },
    E1{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 18){registro.add(variable); return E2;}
            else return E1;
        }
    },
    E2{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 19){registro.add(variable); return E3;}
            else return E2;
        }
    },
    E3{
        @Override
        public MaqEstado siguienteEstado(int variable) {
            if(variable == 20 || variable == 21){registro.add(variable); return EFinal;}
            else return E3;
        }
    },
    EFinal{
        @Override
        public MaqEstado siguienteEstado(int variable){
            return EFinal;
        }
    };
    
    public abstract MaqEstado siguienteEstado(int variable);
    static ArrayList<Integer> registro = new ArrayList<>();
    
    /**
     * reinicia el registro de transiciones disparadas.
     */
    static public void reiniciarRegistro(){
        registro.clear();
    }
    
    /**
     * getter del registro
     * @return ArrayList<Integer> registro
     */
    static public ArrayList<Integer> getRegistro(){
        return registro;
    }
    
}


