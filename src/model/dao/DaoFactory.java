package model.dao;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    //contém operações estáticas para instanciar os Dao's

    public static SellerDao createSellerDao(){ //expoe um metodo que retorna o tipo da interface
        return new SellerDaoJDBC(); //mas internamente ela vai instanciar uma implementação
        //dessa forma o programa não reconhece a implementação, apenas a interface
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC();
    }
}
