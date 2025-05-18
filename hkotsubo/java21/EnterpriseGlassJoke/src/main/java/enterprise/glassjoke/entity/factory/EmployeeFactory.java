package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.repository.NamesRepository;
import enterprise.glassjoke.entity.Employee;

/**
 * Factory to create new instances of an employee
 *
 * @author hkotsubo
 */
public class EmployeeFactory implements GenericOfficeEntityFactory<Employee> {

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    @Override
    public GenericOfficeEntityFactory<Employee> newFactory() {
        return new EmployeeFactory();
    }

    /**
     * Create an employee with a random name
     *
     * @return The new employee
     */
    @Override
    public Employee createEntity() {
        return new Employee(NamesRepository.getRandomName());
    }

    /**
     * Create an employee with the specified name
     *
     * @param name The employee's name
     *
     * @return The new employee
     */
    @Override
    public Employee createEntity(String name) {
        return new Employee(name);
    }
}
