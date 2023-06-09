package ua.project.SafeSellSafeBuy.controllers;

//  https://stripe.com/docs/checkout/quickstart

import java.nio.file.Paths;

        import static spark.Spark.post;
        import static spark.Spark.port;
        import static spark.Spark.staticFiles;

        import com.stripe.Stripe;
        import com.stripe.model.checkout.Session;
        import com.stripe.param.checkout.SessionCreateParams;

public class StripeController {


    public static void main(String[] args) {
        port(8080);

        // This is your test secret API key.
        Stripe.apiKey = "sk_test_51MzlRYFqvRFvrqeTIGUTEjV3Z0LhMNF1ujwBW8GGKVMWirqKHrNzAGmwUY4FW3ee2QGZT3jdMX4Zua99rampRmrg005lMI2PeZ";

        staticFiles.externalLocation(
                Paths.get("public").toAbsolutePath().toString());

        post("/create-checkout-session", (request, response) -> {
            String YOUR_DOMAIN = "http://localhost:8080";
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(YOUR_DOMAIN + "/success.html")
                            .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                            .setPrice("{{PRICE_ID}}")
                                            .build())
                            .build();
            Session session = Session.create(params);

            response.redirect(session.getUrl(), 303);
            return "/product/main";
        });
    }
}