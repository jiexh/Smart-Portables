/* This code is used to implement carousel feature in Website. Carousels are used to implement slide show feature. This  code is used to display
all the accessories related to a particular product. This java code is getting called from cart.java. The product which is added to cart, all the
accessories related to product will get displayed in the carousel.*/


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;


class Carousel {

    String carouselfeature(List<CartItem> cartItems) throws ServletException {
        StringBuilder sb = new StringBuilder();
        String myCarousel = "myCarousel";

        sb.append("<div id='content'><div class='post'><h2 class='title meta'>");
        sb.append("<a style='font-size: 24px;'>Customers Also Bought</a>");

        sb.append("</h2>");

        sb.append("<div class='container'>");
				/* Carousels require the use of an id (in this case id="myCarousel") for carousel controls to function properly.
				The .slide class adds a CSS transition and animation effect, which makes the items slide when showing a new item.
				Omit this class if you do not want this effect.
				The data-ride="carousel" attribute tells Bootstrap to begin animating the carousel immediately when the page loads.

				*/
        sb.append("<div class='carousel slide' id='")
                .append(myCarousel)
                .append("' data-ride='carousel'>");

				/*The slides are specified in a <div> with class .carousel-inner.
				The content of each slide is defined in a <div> with class .item. This can be text or images.
				The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible.
				*/
        sb.append("<div class='carousel-inner'>");

        HashSet<String> accessoryIdSet = new HashSet<>();
        for (CartItem oi : cartItems) {
            Set<String> accessoriesForItem = MySqlDataStoreUtilities.listProducts().get(oi.getItemId()).getAccessoryIds();

            if (accessoriesForItem.size() > 0) {
                int k = 0;
                for (String accessoryId : accessoriesForItem) {
                    Product accessory = MySqlDataStoreUtilities.listProducts().get(accessoryId);
                    if (accessory == null || accessoryIdSet.contains(accessory.getId())) {
                        continue;
                    } else {
                        accessoryIdSet.add(accessory.getId());
                    }

                    if (k == 0) {
                        sb.append("<div class='item active'><div class='col-md-6'>");
                    } else {
                        sb.append("<div class='item'><div class='col-md-6'>");
                    }
                    sb.append("<div id='shop_item'>");
                    sb.append("<h3>").append(accessory.getName()).append("</h3>");
                    sb.append("<strong>$").append(accessory.getPrice()).append("</strong><ul>");
                    sb.append("<li id='item'><img src='images/").append(accessory.getImage())
                            .append("' alt='' /></li>");
                    sb.append("<li><form method='post' action='Cart'>")
                            .append("<input type='hidden' name='id' value='")
                            .append(accessory.getId())
                            .append("'>")
                            .append("<input type='submit' class='btnbuy' value='Buy Now'></form></li>");

                    sb.append("<li><form method='get' action='ReviewList'>");
                    sb.append("<input type='hidden' name='id' value='");
                    sb.append(accessory.getId());
                    sb.append("'>");
                    sb.append("<input type='submit' name='ReviewAction' value='View Reviews' class='btnbuy'></form></li>")
                            .append("<li><form method='get' action='ReviewList'>")
                            .append("<input type='hidden' name='id' value='")
                            .append(accessory.getId())
                            .append("'>")
                            .append("<input type='submit' name='ReviewAction' value='Write Review' class='btnbuy'></form></li>");

                    sb.append("</ul></div></div>")
                            .append("</div>");

                    k++;
                }

            }
        }
        /*		The "Left and right controls" part:
					This code adds "left" and "right" buttons that allows the user to go back and forth between the slides manually.
				The data-slide attribute accepts the keywords "prev" or "next", which alters the slide position relative to its current position.
				*/
        sb.append("<a class='left carousel-control' href='#")
                .append(myCarousel)
                .append("' data-slide='prev' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>")
                .append("<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>")
                .append("<span class='sr-only'>Previous</span>")
                .append("</a>");
        sb.append("<a class='right carousel-control' href='#")
                .append(myCarousel)
                .append("' data-slide='next' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>")
                .append("<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>")
                .append("<span class='sr-only'>Next</span>")
                .append("</a>");


        sb.append("</div></div></div></div></div");

        // Don't show carousel if no accessories are found.
        if (accessoryIdSet.isEmpty()) {
            return "";
        }

        return sb.toString();
    }

    String recommendedProductsCarousel(List<Product> products) throws ServletException {
        StringBuilder sb = new StringBuilder();
        String myCarousel = "myCarousel";

        sb.append("<div id='content'><div class='post'><h2 class='title meta'>");
        sb.append("<a style='font-size: 24px;'>Recommended Products</a>");

        sb.append("</h2>");

        sb.append("<div class='container' style='height:380px'>");
        sb.append("<div class='carousel slide' id='")
                .append(myCarousel)
                .append("' data-ride='carousel'>");

        sb.append("<div class='carousel-inner'>");

        int k = 0;

        for (Product product : products) {
            if (k == 0) {
                sb.append("<div class='item active'><div class='col-md-6'>");
            } else {
                sb.append("<div class='item'><div class='col-md-6'>");
            }

            sb.append("<div id='shop_item'>");
            sb.append("<h3>").append(product.getName()).append("</h3>");
            sb.append("<strong>$").append(product.getPrice()).append("</strong>");
            sb.append("<ul><li id='item'><img src='images/").append(product.getImage())
                    .append("' alt='' /></li>");
            sb.append("<li><form method='post' action='Cart'>")
                    .append("<input type='hidden' name='id' value='")
                    .append(product.getId())
                    .append("'>")
                    .append("<input type='submit' class='btnbuy' value='Buy Now'></form></li>");

            sb.append("<li><form method='get' action='ReviewList'>");
            sb.append("<input type='hidden' name='id' value='");
            sb.append(product.getId());
            sb.append("'>");
            sb.append("<input type='submit' name='ReviewAction' value='View Reviews' class='btnbuy'></form></li>")
                    .append("<li><form method='get' action='ReviewList'>")
                    .append("<input type='hidden' name='id' value='")
                    .append(product.getId())
                    .append("'>")
                    .append("<input type='submit' name='ReviewAction' value='Write Review' class='btnbuy'></form></li>");

            sb.append("</ul>")
                    .append("</div></div></div>");

            k++;
        }

        sb.append("</div>"); // carousel-inner

        sb.append("<a class='left carousel-control' href='#")
                .append(myCarousel)
                .append("' data-slide='prev' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>")
                .append("<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>")
                .append("<span class='sr-only'>Previous</span>")
                .append("</a>");
        sb.append("<a class='right carousel-control' href='#")
                .append(myCarousel)
                .append("' data-slide='next' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>")
                .append("<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>")
                .append("<span class='sr-only'>Next</span>")
                .append("</a>");

        sb.append("</div></div></div></div>");

        if (products.isEmpty()) {
            return "";
        }

        return sb.toString();
    }
}
