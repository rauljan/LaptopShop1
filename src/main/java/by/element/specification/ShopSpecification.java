package by.element.specification;

import by.element.model.Shop;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

public final class ShopSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Shop> addressLike(String expression) {
        if (expression == null || expression.isBlank())
            return null;
        return (root, query, builder) -> builder.like(root.get("address"), "%" + expression + "%");
    }
}