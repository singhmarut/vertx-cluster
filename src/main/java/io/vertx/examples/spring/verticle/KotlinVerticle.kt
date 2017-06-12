package io.vertx.examples.spring.verticle


/**
 * Created by marutsingh on 5/27/17.
 */

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.eventbus.Message
import io.vertx.examples.spring.service.ProductService


/**
 * Simple verticle to wrap a Spring service bean - note we wrap the service call
 * in executeBlocking, because we know it's going to be a JDBC call which blocks.
 * As a general principle with Spring beans, the default assumption should be that it will block unless you
 * know for sure otherwise (in other words use executeBlocking unless you know for sure your service call will be
 * extremely quick to respond)
 */
class SpringDemoVerticle1 : AbstractVerticle() {

    private val mapper = ObjectMapper()

    /*fun allProductsHandler(service: ProductService): Handler<Message<String>> {


        System.out.println("Hello World!")
        return { msg ->
            System.out.println("1 received message.body() = "
                    + message.body());
        }
        // throw new Exception("");

        *//*return msg -> vertx.<String>executeBlocking(future -> {
                    throw new Exception("");
                    try {
                        future.complete(mapper.writeValueAsString(service.getAllProducts()));
                    } catch (JsonProcessingException e) {
                        System.out.println("Failed to serialize result");
                        future.fail(e);
                    }
                },
                result -> {
                    if (result.succeeded()) {
                        msg.reply(result.result());
                    } else {
                        msg.reply(result.cause().toString());
                    }
                });*//*
    }
*/
    override fun start() {
        super.start()
        val productService = ProductService()

        vertx.eventBus().consumer(ALL_PRODUCTS_ADDRESS,  { message: Message<String> ->
            System.out.println("1 received message.body() = "
                    + message.body());
        })

        //Start consuming events
        /*vertx.eventBus().consumer<String>(ALL_PRODUCTS_ADDRESS,  {
            allProductsHandler(productService)
        })*/

    }

    companion object {

        public val ALL_PRODUCTS_ADDRESS: String = "example.all.products"
    }
}
