package io.vertx.examples.spring;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.examples.spring.verticle.ServerVerticle;
import io.vertx.examples.spring.verticle.SpringDemoVerticle;
import io.vertx.examples.spring.verticle.SpringDemoVerticle1;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Runner for the vertx-spring sample
 *
 */
@SpringBootApplication
public class SpringExampleRunner {

    @Value("${server.port}")
    String someValue;

    public static void main( String[] args ) {
        //Config hazelcastConfig = new Config();
        //hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1").setEnabled(true);
        //hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        ConfigurableApplicationContext ctx = SpringApplication.run(SpringExampleRunner.class, args);

        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "127.0.0.1");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ZookeeperClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new SpringDemoVerticle());
                vertx.deployVerticle(new ServerVerticle(Integer.parseInt(ctx.getEnvironment().getProperty("server.port"))));
            } else {
            }
        });
    }
}
