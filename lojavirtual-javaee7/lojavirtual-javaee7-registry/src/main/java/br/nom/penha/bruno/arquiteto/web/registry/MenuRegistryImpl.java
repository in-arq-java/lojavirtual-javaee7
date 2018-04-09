package br.nom.penha.bruno.arquiteto.web.registry;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.ejb.Singleton;

/**
 *
 * @author Arquiteto
 */
@Singleton
public class MenuRegistryImpl implements MenuRegistry {

    private final Set<String> menus = new LinkedHashSet<>();

    @Override
    public void addMenu(String menu) {
        menus.add(menu);
    }

    @Override
    public Set<String> getMenus() {
        return menus;
    }

    @Override
    public void removeMenu(String menu) {
        menus.remove(menu);
    }
}
