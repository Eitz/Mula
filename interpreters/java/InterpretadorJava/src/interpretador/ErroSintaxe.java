package interpretador;
class ErroSintaxe extends RuntimeException {

    public ErroSintaxe(Throwable e) {
        super(e);
    }
    
}
