package by.element.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import by.element.model.SSD;

public final class SSDSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<SSD> modelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.get("model"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<SSD> memoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<SSD> priceEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("memory"), expression);
    }
}