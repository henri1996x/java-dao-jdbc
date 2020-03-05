package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj); //Responsável por inserir no banco, o objeto que foi passado.

    void update(Seller obj); //Irá atualizar no banco com os dados que forem passados.

    void deleteById(Integer id); //Responsãvel por deletar no banco, o elemento com o ID passado.

    Seller findById(Integer id); //pegar o id e consultar no no db um objeto com esse id, se existir, irá retornar, se não, retorna nulo.

    List<Seller> findAll();//Retorna uma lista com todos os departamentos.

    List<Seller> findByDepartment(Department department);//Retorna uma lista de vendedores por departamento
}
