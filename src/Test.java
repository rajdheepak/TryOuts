import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        MenuItem menuItem = new MenuItem();
        menuItem.Price = null;
        menuItem.name = "mayo";

        MenuItem menuItem1 = new MenuItem();
        menuItem1.Price = 25.0;
        menuItem1.name = "shwarma";

        MenuItem menuItem2 = new MenuItem();
        menuItem2.Price = 35.0;
        menuItem2.name = "kubos";

        MenuItem menuItem3 = new MenuItem();
        menuItem3.Price = null;
        menuItem3.name = "juice";

        MenuGroup menuGroup = new MenuGroup();

        MenuGroup menuGroup1 = new MenuGroup();
        menuGroup1.items = new ArrayList<>();
        menuGroup1.items.add(menuItem);
        menuGroup1.items.add(menuItem1);
        menuGroup1.groups = new ArrayList<>();
        menuGroup1.price = null;

        menuGroup.groups = new ArrayList<>();
        menuGroup.groups.add(menuGroup1);
        menuGroup.items = new ArrayList<>();
        menuGroup.items.add(menuItem2);
        menuGroup.items.add(menuItem3);
        menuGroup.price = null;

        Menu menu = new Menu();
        menu.groups = new ArrayList<>();
        menu.groups.add(menuGroup);
        menu.name = "toast";
        menu.price = 75.0;

        ArrayList<Menu> menus = new ArrayList<>();
        menus.add(menu);

        Double pri = getPrice(menus,"juice");
        System.out.println("price: "+pri);
    }

    public static Double getPrice(Collection<Menu> menus, String itemName) {
        Double itemPrice = 0.0;
        for(int i =0 ; i < menus.size() ; i++) {
            for (Menu next : menus) {
                for (MenuGroup nextMenuGroup : next.groups) {
                    itemPrice = findItemPriceInGroup(nextMenuGroup, itemName);
                }
                if(itemPrice == null) {
                    itemPrice = next.price;
                    break;
                }
            }
        }
        return itemPrice;
    }

    public static Double findItemPriceInGroup(MenuGroup menuGroup, String item) {
        Double price = 0.0;
        for(MenuItem menuItem: menuGroup.items) {
            if(Objects.equals(menuItem.name, item)) {
                price = menuItem.Price;
            }
            if(price == null) {
                price = menuGroup.price;
                break;
            }
            if(price != 0.0) {
                break;
            }
        }
        if(price != null && price == 0.0) {
            for(MenuGroup menuGroup1: menuGroup.groups) {
                price = findItemPriceInGroup(menuGroup1, item);
            }
        }
        return price;
    }
}

final class Menu {
    String name;
    Collection<MenuGroup> groups;
    Double price;
}

final class MenuGroup {
    String name;
    Collection<MenuGroup> groups;
    Collection<MenuItem> items;
    Double price;
}

class MenuItem {
    String name;
    Double Price;
}
