package by.element.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import by.element.model.Display;

public final class DisplaySpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Display> modelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.get("model"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> typeEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("type"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> diagonalEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("diagonal"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> resolutionEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("resolution"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> priceEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("price"), expression);
    }
}
