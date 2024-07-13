package com.g4.university.services.modules;
import java.util.List;
public interface IService <T>{

    T Create (T session);

    List<T> Retrieve();
    void Delete (int id);
}
