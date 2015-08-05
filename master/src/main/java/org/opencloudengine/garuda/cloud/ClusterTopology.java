package org.opencloudengine.garuda.cloud;

import org.opencloudengine.garuda.exception.InvalidRoleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
* 현재 생성되어 운영중인 클러스터의 형상을 담고있다.
* Created by swsong on 2015. 7. 20..
*/
public class ClusterTopology {


    public static final String IAAS_PROFILE_KEY = "iaasProfile";
    public static final String CLUSTER_ID_KEY = "clusterId";

    public static final String GARUDA_MASTER_ROLE = "garuda-master";
    public static final String PROXY_ROLE = "proxy";
    public static final String MESOS_MASTER_ROLE = "mesos-master";
    public static final String MESOS_SLAVE_ROLE = "mesos-slave";
    public static final String MANAGEMENT_DB_REGISTRY_ROLE = "management";
    public static final String SERVICE_NODES_ROLE = "service-db";

    private String clusterId;
    private String iaasProfile;

    private List<CommonInstance> garudaMasterList;
    private List<CommonInstance> proxyList;
    private List<CommonInstance> mesosMasterList;
    private List<CommonInstance> mesosSlaveList;
    private List<CommonInstance> managementList;
    private List<CommonInstance> serviceNodeList;
    private List<CommonInstance> noRoleNodeList;


    public ClusterTopology(String clusterId, String iaasProfile) {
        this.clusterId = clusterId;
        this.iaasProfile = iaasProfile;

        garudaMasterList = new ArrayList<>();
        proxyList = new ArrayList<>();
        mesosMasterList = new ArrayList<>();
        mesosSlaveList = new ArrayList<>();
        managementList = new ArrayList<>();
        serviceNodeList = new ArrayList<>();
        noRoleNodeList = new ArrayList<>();
    }

    public String getClusterId() {
        return clusterId;
    }

    public String getIaasProfile() {
        return iaasProfile;
    }

    public List<CommonInstance> getAllNodeList() {
        List<CommonInstance> list = new ArrayList<>();
        list.addAll(garudaMasterList);
        list.addAll(proxyList);
        list.addAll(mesosMasterList);
        list.addAll(mesosSlaveList);
        list.addAll(managementList);
        list.addAll(serviceNodeList);
        list.addAll(noRoleNodeList);
        return list;
    }

    public List<CommonInstance> getGarudaMasterList() {
        return garudaMasterList;
    }

    public List<CommonInstance> getProxyList() {
        return proxyList;
    }

    public List<CommonInstance> getMesosMasterList() {
        return mesosMasterList;
    }

    public List<CommonInstance> getMesosSlaveList() {
        return mesosSlaveList;
    }

    public List<CommonInstance> getManagementList() {
        return managementList;
    }

    public List<CommonInstance> getServiceNodeList() {
        return serviceNodeList;
    }

    public void addNode(String role, CommonInstance commonInstance) throws InvalidRoleException {
        switch (role) {
            case GARUDA_MASTER_ROLE:
                garudaMasterList.add(commonInstance);
                break;
            case PROXY_ROLE:
                proxyList.add(commonInstance);
                break;
            case MESOS_MASTER_ROLE:
                mesosMasterList.add(commonInstance);
                break;
            case MESOS_SLAVE_ROLE:
                mesosSlaveList.add(commonInstance);
                break;
            case MANAGEMENT_DB_REGISTRY_ROLE:
                managementList.add(commonInstance);
                break;
            case SERVICE_NODES_ROLE:
                serviceNodeList.add(commonInstance);
                break;
            default:
                noRoleNodeList.add(commonInstance);
                throw new InvalidRoleException("no such role : " + role);
        }
    }

    @Override
    public String toString() {
        return getProperties().toString();
    }

    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty(CLUSTER_ID_KEY, clusterId);
        props.setProperty(IAAS_PROFILE_KEY, iaasProfile);
        putProps(props, GARUDA_MASTER_ROLE, garudaMasterList);
        putProps(props, PROXY_ROLE, proxyList);
        putProps(props, MESOS_MASTER_ROLE, mesosMasterList);
        putProps(props, MESOS_SLAVE_ROLE, mesosSlaveList);
        putProps(props, MANAGEMENT_DB_REGISTRY_ROLE, managementList);
        putProps(props, SERVICE_NODES_ROLE, serviceNodeList);
        return props;
    }

    private void putProps(Properties props, String roleName, List<CommonInstance> nodeList) {
        StringBuffer addressList = new StringBuffer();
        for(CommonInstance d : nodeList) {
            String id = d.getInstanceId();
            if(addressList.length() > 0) {
                addressList.append(",");
            }
            addressList.append(id);
        }
        props.put(roleName, addressList.toString());
    }

}