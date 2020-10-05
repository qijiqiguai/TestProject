package code.example.extension.javaspi.impl;

import code.example.extension.javaspi.IJavaRepository;

public class MongoRepository implements IJavaRepository {
    @Override
    public void save(String data) {
        System.out.println("Save " + data + " to Mongo");
    }
}
