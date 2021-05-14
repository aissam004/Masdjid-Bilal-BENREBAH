/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author pc
 */
public final class Database {
    private String persistanceUnit = "MasdjidPU";
    private static EntityManagerFactory entityManagerFactory=null;

    
  
    private Database(){
        entityManagerFactory=Persistence.createEntityManagerFactory(persistanceUnit);
    }
    public static EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null) {
           new Database();
        }
        return entityManagerFactory;
      
    }
}
