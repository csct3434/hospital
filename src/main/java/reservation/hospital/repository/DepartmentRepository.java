package reservation.hospital.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reservation.hospital.domain.Department;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final EntityManager em;

    public void save(Department department) {
        if(department.getId() == null) {
            em.persist(department);
        } else {
            em.merge(department);
        }
    }

    public Department findOne(Long id) {
        return em.find(Department.class, id);
    }

    public List<Department> findAll() {
        return em.createQuery("select d from Department d", Department.class)
                .getResultList();
    }

    public List<Department> findByName(String name) {
        return em.createQuery("select d from Department d where d.name = :name", Department.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void remove(Long departmentId) {
        Department department = em.find(Department.class, departmentId);
        em.remove(department);
    }
}
