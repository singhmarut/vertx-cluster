package io.vertx.examples.spring;

import com.hazelcast.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.examples.spring.verticle.ServerVerticle;
import io.vertx.examples.spring.verticle.SpringDemoVerticle;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Runner for the vertx-spring sample
 *
 */
public class SpringExampleRunner {

    public static void main( String[] args ) {
        System.out.println("adfadf");
        Config hazelcastConfig = new Config();
        hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1").setEnabled(true);
        hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new SpringDemoVerticle());
                vertx.deployVerticle(new ServerVerticle(Integer.parseInt(args[0])));
            } else {
            }
        });
    }
}
