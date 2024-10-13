import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация технического задания компании Absolut Pos.
 * Основной идеей выступает расчет предков для каждого ингредиента.
 * Возможны проблемы при изменении состава продукта, но данная возможность не была указана в тз.
 *
 * @author <a href="http://surl.li/zczdsc">Stanislav Prokopev</a>
 */
public class Product {

    private final String name;
    private final Set<Product> content = new HashSet<>();
    private final Set<Product> ancestors = new HashSet<>();

    public Product(String name) {
        this.name = name;
    }

    /**
     * Добавить ингредиент
     * @param product ингредиент для добавления
     * @return true/false
     */
    public boolean addProduct(Product product) {
        if(product == null)
            throw new IllegalArgumentException("Product must not be null");

        if (isCanBeAdded(product)) {
            content.add(product);
            product.ancestors.addAll(this.ancestors);
            product.ancestors.add(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Этот метод проверяет, можно ли добавить продукт в качестве ингредиента,
     * основываясь на списке предков, чтобы избежать циклов.
     * Предыдущая реализация (закомментирована ниже) была заменена для повышения производительности:
     *
     * <pre>{@code
     * private boolean isCanBeAdded(Product product) {
     *     Product current = this;
     *     while (current != null) {
     *         if (current.equals(product)) {
     *             return false;
     *         }
     *         current = current.getRootProduct();
     *     }
     *     return true;
     * }
     * }</pre>
     *
     * Эта реализация была менее эффективной, так как каждый раз требовала обход всех предков.
     * Теперь мы используем кэширование предков для оптимизации времени выполнения.
     */
    private boolean isCanBeAdded(Product product) {
        return !ancestors.contains(product) && !this.equals(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
