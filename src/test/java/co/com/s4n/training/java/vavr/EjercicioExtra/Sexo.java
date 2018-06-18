package co.com.s4n.training.java.vavr.EjercicioExtra;

enum Sexo {
    MASCULINO("Masculino"),FEMENINO("Femenino"),OTRO("Otro");

    private String sexo;

    private Sexo(String sexo){
        this.sexo = sexo;
    }

    public String sexo(){
        return this.sexo;
    }
}
