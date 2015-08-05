package org.opencloudengine.garuda.cloud;

import com.amazonaws.services.ec2.model.Instance;
import org.junit.Before;
import org.junit.Test;
import org.opencloudengine.garuda.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by swsong on 2015. 7. 22..
 */
public class IaaSTest {

    private static Logger logger = LoggerFactory.getLogger(IaaSTest.class);
    String accessKey;
    String secretKey;

    String endPoint = "ec2.ap-northeast-1.amazonaws.com";
    String instanceType = "t2.micro";
    String imageId = "ami-936d9d93";
    int volumeSize = 13;
    String group = "default";
    String keyPair = "aws-garuda";

    @Before
    public void setUp() throws IOException {
        Properties props = PropertiesUtil.loadProperties("/Users/swsong/Dropbox/System/auth/AwsCredentials.properties");
        accessKey = props.getProperty("accessKey");
        secretKey = props.getProperty("secretKey");
    }

    @Test
    public void testEC2Launch() {
        InstanceRequest request = new InstanceRequest(instanceType, imageId, volumeSize, group, keyPair);
        EC2IaaS iaas = new EC2IaaS(endPoint, accessKey, secretKey, null);
        List<CommonInstance> list = iaas.launchInstance(request, "sang", 2);
        for(CommonInstance i : list) {
            logger.debug("- {}", i.getInstanceId());
            logger.debug("-- {}", i.as(Instance.class));
        }

        logger.debug("Wait..");
        iaas.waitUntilInstancesReady(list);
        logger.debug("Done!!");
    }

    @Test
    public void testEC2LaunchByCustomAMI() {
        String imageId = "ami-ba1fa3ba";
        int volumeSize = 10;
        InstanceRequest request = new InstanceRequest(instanceType, imageId, volumeSize, group, keyPair);
        EC2IaaS iaas = new EC2IaaS(endPoint, accessKey, secretKey, null);
        List<CommonInstance> list = iaas.launchInstance(request, "slave", 1);
        for(CommonInstance i : list) {
            logger.debug("- {}", i.getInstanceId());
            logger.debug("-- {}", i.as(Instance.class));
        }

        logger.debug("Wait..");
        iaas.waitUntilInstancesReady(list);
        logger.debug("Done!!");
    }

    @Test
    public void testEC2LaunchDescribeTerminate() {
        InstanceRequest request = new InstanceRequest(instanceType, imageId, volumeSize, group, keyPair);
        EC2IaaS iaas = new EC2IaaS(endPoint, accessKey, secretKey, null);
        List<CommonInstance> list = iaas.launchInstance(request, "sang", 2);
        for(CommonInstance i : list) {
            logger.debug("- {}", i.getInstanceId());
            logger.debug("-- {}", i.as(Instance.class));
        }

        logger.debug("Wait..");
        iaas.waitUntilInstancesReady(list);
        logger.debug("Launch Done!!");



        logger.debug("Terminate!");
        iaas.terminateInstances(list);


    }

}