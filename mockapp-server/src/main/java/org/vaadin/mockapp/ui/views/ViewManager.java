package org.vaadin.mockapp.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vaadin.mockapp.backend.authentication.Authentication;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;

import java.io.Serializable;
import java.util.*;

/**
 * @author petter@vaadin.com
 */
public class ViewManager implements ViewProvider {

    private Map<String, ViewDefinitionEntry> viewDefinitions = new HashMap<String, ViewDefinitionEntry>();

    /**
     * @param viewClass
     */
    public void addView(@NotNull Class<? extends View> viewClass) {
        ViewDefinition viewDefinition = viewClass.getAnnotation(ViewDefinition.class);
        if (viewDefinition == null) {
            throw new IllegalArgumentException("View class must have the @ViewDefinition annotation");
        }
        viewDefinitions.put(viewDefinition.name(), new ViewDefinitionEntry(viewClass, viewDefinition));
    }

    /**
     * @param viewName
     * @param viewCaption
     */
    public void setViewCaption(@NotNull String viewName, @Nullable String viewCaption) {
        getViewDefinitionEntry(viewName).setViewCaption(viewCaption);
    }

    /**
     * @param viewName
     * @param viewIcon
     */
    public void setViewIcon(@NotNull String viewName, @Nullable Resource viewIcon) {
        getViewDefinitionEntry(viewName).setViewIcon(viewIcon);
    }

    /**
     * @param viewName
     * @param allowedRoles
     */
    public void setAllowedRoles(@NotNull String viewName, @NotNull String... allowedRoles) {
        getViewDefinitionEntry(viewName).setAllowedRoles(allowedRoles);
    }

    /**
     * @return
     */
    @NotNull
    public List<ViewDefinitionEntry> getViewDefinitionsForMenu() {
        Authentication currentUser = AuthenticationHolder.getAuthentication();
        List<ViewDefinitionEntry> viewList = new ArrayList<ViewDefinitionEntry>();
        for (ViewDefinitionEntry entry : viewDefinitions.values()) {
            if (entry.isMenuEntry() && entry.isAllowed(currentUser)) {
                viewList.add(entry);
            }
        }
        Collections.sort(viewList);
        return viewList;
    }

    @NotNull
    private ViewDefinitionEntry getViewDefinitionEntry(@NotNull String viewName) {
        ViewDefinitionEntry entry = viewDefinitions.get(viewName);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown view name");
        }
        return entry;
    }

    @Override
    @Nullable
    public String getViewName(@NotNull String viewAndParameters) {
        int indexOfSlash = viewAndParameters.indexOf('/');
        String viewName;

        if (indexOfSlash == -1) {
            viewName = viewAndParameters;
        } else {
            viewName = viewAndParameters.substring(0, indexOfSlash);
        }

        if (viewDefinitions.containsKey(viewName) && viewDefinitions.get(viewName).isAllowed(AuthenticationHolder.getAuthentication())) {
            return viewName;
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public View getView(@NotNull String viewName) {
        ViewDefinitionEntry viewDefinitionEntry = viewDefinitions.get(viewName);
        if (viewDefinitionEntry == null || !viewDefinitionEntry.isAllowed(AuthenticationHolder.getAuthentication())) {
            return null;
        } else {
            return viewDefinitionEntry.getViewInstance();
        }
    }

    /**
     *
     */
    public static class ViewDefinitionEntry implements Serializable, Comparable<ViewDefinitionEntry> {

        private final Class<? extends View> viewClass;
        private final String viewName;
        private final boolean cache;
        private final int order;
        private final Set<String> allowedRoles = new HashSet<String>();
        private View cachedViewInstance;
        private Resource viewIcon;
        private String viewCaption;

        private ViewDefinitionEntry(@NotNull Class<? extends View> viewClass, @NotNull ViewDefinition viewDefinition) {
            this.viewClass = viewClass;
            viewName = viewDefinition.name();
            cache = viewDefinition.cache();
            if (!viewDefinition.caption().isEmpty()) {
                viewCaption = viewDefinition.caption();
            }
            if (!viewDefinition.iconThemeResource().isEmpty()) {
                viewIcon = new ThemeResource(viewDefinition.iconThemeResource());
            }
            allowedRoles.addAll(Arrays.asList(viewDefinition.allowedRoles()));
            order = viewDefinition.order();
        }

        @NotNull
        private View createViewInstance() {
            try {
                return viewClass.newInstance();
            } catch (RuntimeException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException("Could not create new View instance", ex);
            }
        }

        /**
         * @return
         */
        @NotNull
        public String getViewName() {
            return viewName;
        }

        /**
         * @return
         */
        @Nullable
        public Resource getViewIcon() {
            return viewIcon;
        }

        private void setViewIcon(@Nullable Resource viewIcon) {
            this.viewIcon = viewIcon;
        }

        /**
         * @return
         */
        @Nullable
        public String getViewCaption() {
            return viewCaption;
        }

        private void setViewCaption(@Nullable String viewCaption) {
            this.viewCaption = viewCaption;
        }

        /**
         * @return
         */
        public boolean isMenuEntry() {
            return viewCaption != null || viewIcon != null;
        }

        private void setAllowedRoles(@NotNull String... allowedRoles) {
            this.allowedRoles.clear();
            this.allowedRoles.addAll(Arrays.asList(allowedRoles));
        }

        /**
         * @param authentication
         * @return
         */
        public boolean isAllowed(@NotNull Authentication authentication) {
            if (allowedRoles.isEmpty()) {
                return true;
            } else {
                for (String role : allowedRoles) {
                    if (authentication.hasRole(role)) {
                        return true;
                    }
                }
                return false;
            }
        }

        @NotNull
        private View getViewInstance() {
            if (cache) {
                if (cachedViewInstance == null) {
                    cachedViewInstance = createViewInstance();
                }
                return cachedViewInstance;
            } else {
                return createViewInstance();
            }
        }

        @Override
        public int compareTo(ViewDefinitionEntry o) {
            return order - o.order;
        }
    }
}
