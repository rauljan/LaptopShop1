package by.element.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import by.element.model.Employee;

public final class EmployeeSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> firstNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("firstName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> secondNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("secondName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> shopAddressLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("shop")
                .get("address"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> isWorkingEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("isWorking"), expression.equals("Работает"));
    }
}
