package br.nom.penha.bruno.arquiteto.exception;

public class LojavirtualException extends Exception {
  public LojavirtualException() {
  }

  public LojavirtualException(String msg) {
    super(msg);
  }
  public LojavirtualException(String msg, Throwable t) {
    super(msg, t);
  }
}