package org.vaadin.mockapp.samples.filtering;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

public class HasCategoryFilter implements Container.Filter {

    private String categoryName;

    public HasCategoryFilter(String categoryName) {
        this.categoryName = categoryName.toLowerCase();
    }

    @Override
    public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
        if (itemId instanceof Product) {
            Product product = (Product) itemId;
            for (Category cat : product.getCategory()) {
                if (cat.getName().toLowerCase().contains(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        return "category".equals(propertyId);
    }
}
