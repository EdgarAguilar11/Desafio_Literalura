package com.desafio.service;

public interface IConvierteDatos {
    public <T> T crearObjetoDeJson(String json, Class<T> clase);
}
