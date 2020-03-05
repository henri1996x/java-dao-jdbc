package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department obj); //Responsável por inserir no banco, o objeto que foi passado.

    void update(Department obj); //Irá atualizar no banco com os dados que forem passados.

    void deleteById(Integer id); //Responsãvel por deletar no banco, o elemento com o ID passado.

    Department findById(Integer id); //pegar o id e consultar no no db um objeto com esse id, se existir, irá retornar, se não, retorna nulo.

    List<Department> findAll();//Retorna uma lista com todos os departamentos.
}
