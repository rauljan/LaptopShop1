package by.element.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import by.element.model.RAM;

public final class RAMSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<RAM> modelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.get("model"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<RAM> memoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<RAM> priceEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("memory"), expression);
    }
}