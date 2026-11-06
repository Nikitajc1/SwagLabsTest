public class Info {
    private final String siteName = "https://www.saucedemo.com/";
    private final String loginField = "user-name";
    private final String userName = "standard_user";
    private final String passField = "password";
    private final String passValue = "secret_sauce";
    private final String loginButton = "login-button";
    private final String backpackAddButton = "add-to-cart-sauce-labs-backpack";
    private final String removeButtonForBackpack = "remove-sauce-labs-backpack";
    private final String cartButtonID = "shopping_cart_link";
    private final String inventoryURL = "inventory";
    private final String ariaLabel = "aria-label";
    private final String backpackName = "Sauce Labs Backpack";
    private final String errorXpath = "//h3[contains(@attribute, data-test)]";
    private final String oneItemInCart = "Cart, 1 items";
    private final String twoItemsInCart = "Cart, 2 items";
    private final String emptyCart = "Cart, empty";
    private final String cartURL = "cart";
    private final String inventoryNameField = "inventory_item_name";
    private final String bikeLightName = "Sauce Labs Bike Light";
    private final String bikeLightAddButton = "add-to-cart-sauce-labs-bike-light";
    private final String removeBikeLight = "remove-sauce-labs-bike-light";
    private final String sortingAtoZ =
            "//select[@class='product_sort_container']/option[@value='az']";
    private final String sortingZtoA =
            "//select[@class='product_sort_container']/option[@value='za']";
    private final String sortingLoHi =
            "//select[@class='product_sort_container']/option[@value='lohi']";
    private final String sortingHiLo =
            "//select[@class='product_sort_container']/option[@value='hilo']";
    private final String inventoryNameList = "//div[@class='inventory_item_name ']";
    private final String inventoryPriceList = "//div[@class='inventory_item_price']";
    private final String cartBadge = "shopping_cart_badge";
    private final String cartItem = "cart_item";
    private final String numberOfItems = "//div[@class='cart_item']";
    private final Double priceOfBackpack = 29.99;
    private final String shirtName = "Sauce Labs Bolt T-Shirt";

    public String getSite() {
        return siteName;
    }

    public String getLogin() {
        return loginField;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassField() {
        return passField;
    }

    public String getPassValue() {
        return passValue;
    }

    public String getLoginButton() {
        return loginButton;
    }

    public String getBackpackAddButton() {
        return backpackAddButton;
    }
    public String getCartButtonID() {return cartButtonID;}
    public String getInventoryURL() {return inventoryURL;}
    public String getAriaLabel() {return ariaLabel;}
    public String getBackpackName() {return backpackName;}
    public String getErrorXpath() {return errorXpath;}
    public String getRemoveBackpackButton() {return removeButtonForBackpack;}
    public String getOneItemInCart() {return oneItemInCart;}
    public String getEmptyCart() {return emptyCart;}
    public String getCartURL() {return cartURL;}
    public String getInventoryNameField() {return inventoryNameField;}
    public String getBikeLightName() {return bikeLightName;}
    public String getBikeLightAddButton() {return bikeLightAddButton;}
    public String getTwoItemsInCart() {return twoItemsInCart;}
    public String getRemoveBikeLight() {return removeBikeLight;}

    public String getSortingAtoZ() {
        return sortingAtoZ;
    }

    public String getSortingZtoA() {
        return sortingZtoA;
    }

    public String getSortingLoHi() {
        return sortingLoHi;
    }

    public String getSortingHiLo() {
        return sortingHiLo;
    }

    public String getInventoryNameList() {
        return inventoryNameList;
    }

    public String getInventoryPriceList() {
        return inventoryPriceList;
    }

    public String getCartBadge() {
        return cartBadge;
    }

    public String getCartItem() {
        return cartItem;
    }

    public String getNumberOfItems() {
        return numberOfItems;
    }

    public Double getPriceOfBackpack() {
        return priceOfBackpack;
    }

    public String getShirtName() {
        return shirtName;
    }
}
