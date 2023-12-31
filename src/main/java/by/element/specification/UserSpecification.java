package by.element.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import by.element.security.User;

public final class UserSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<User> usernameEqual(String expression) {
        if (expression == null || expression.isBlank())
            return null;
        return (root, query, builder) -> builder.equal(root.get("username"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<User> isActiveEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("isActive"), expression.equals("Активный"));
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<User> emailLike(String expression) {
        if (expression == null || expression.isBlank())
            return null;
        return (root, query, builder) -> builder.like(root.get("email"), "%" + expression + "%");
    }
}